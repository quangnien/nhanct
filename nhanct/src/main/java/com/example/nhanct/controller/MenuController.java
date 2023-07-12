package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.MenuEntity;
import com.example.nhanct.service.MenuService;
import com.example.nhanct.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/menu")
public class MenuController extends FunctionCommon {

	@Autowired
	private MenuService menuService;
	@Value("${adress.404}")
	private String adress404;
	@Autowired
	SecurityUtils myMenu;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.MENU) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.MENU) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<MenuEntity> page = menuService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<MenuEntity> listMenu = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("menu", listMenu);
		return "menu/index";
	}

	@GetMapping("add")
	public String add(ModelMap model, @RequestParam(value = "message", required = false) String message) {

		if(hasRoleAuthor(MenuConstant.MENU) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		model.addAttribute("message", message);
		model.addAttribute("menu", new MenuEntity());
		return "menu/add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("menu") MenuEntity menu, BindingResult errors, ModelMap model) {

		String message = "";

		if (errors.hasErrors()) {
			menuListRole(model);
			model.addAttribute("menus", menuService.findAll());
			return "menu/add";
		}
		if(menuService.isExceptionMenuCode(menu)) {
			menuListRole(model);
			errors.rejectValue("menuName", "menu", "MenuCode is exist!");
			return "menu/add";
		}
//		if(menuService.isException(menu)) {
//			message = "F";
//			return "redirect:/admin/menu/add?message="+message;
//		}

		menuService.add(menu);
		return "redirect:/admin/menu";
	}

	@GetMapping("edit")
	public String edit(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.MENU) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			model.addAttribute("menus", menuService.findAll());
			model.addAttribute("menu", menuService.getById(id));
			return "menu/edit";
		} catch (Exception e) {
			return "redirect:" + adress404;
		}
	}

	@PostMapping("edit")
	public String edit(@Valid @ModelAttribute("menu") MenuEntity menu, BindingResult errors, ModelMap model) {
		if (errors.hasErrors() || duplicateMenuCode(menu, errors)) {
			menuListRole(model);
			model.addAttribute("menus", menuService.findAll());
			return "menu/edit";
		}

		menuService.update(menu);
		return "redirect:/admin/menu";
	}

	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, ModelMap model,
						 @RequestParam(value = "message", required = false) String message) {
		if(hasRoleAuthor(MenuConstant.MENU) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		try {
			MenuEntity menu = menuService.getById(id);
			model.addAttribute("menu", menu);
			model.addAttribute("message", message);
			return "menu/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}

	@GetMapping("confirm-delete-post")
	public String delete1(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.MENU) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
			if(id != myMenu.getPrincipal().getId() && menuService.delete(id) ) {
				return "redirect:/admin/menu";
			}else {
				message = "F";
				return "redirect:/admin/menu/confirm-delete?id=" + id + "&&message="+message;
			}

		} catch (DataIntegrityViolationException e) {
			message = "F";
			System.out.println("/admin/menu/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/menu/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			System.out.println("/admin/menu/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/menu/confirm-delete?id=" + id + "&&message="+message;
		}
	}

	@GetMapping("search")
	public String index(ModelMap model, @Param("keyword") String keyword) {

		if(hasRoleAuthor(MenuConstant.MENU) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		List<MenuEntity> listMenu = menuService.findAll(keyword);
		model.addAttribute("listMenu", listMenu);
		model.addAttribute("keyword", keyword);
		return "menu/search";
	}

	/*------------------------------PRIVATE METHOD------------------------------------------*/
	private boolean duplicateMenuCode(MenuEntity menuEntity, BindingResult errors) {
		MenuEntity entity = menuService.findByMenuCode(menuEntity.getMenuCode());
		if (entity == null || entity.getId() == menuEntity.getId())
			return false;
		else {
			errors.rejectValue("menuCode", "menu", "This menu-code is already exist!");
			return true;
		}
	}

}
