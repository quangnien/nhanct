package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.dto.MenuForRoleDto;
import com.example.nhanct.dto.RoleForAccountDto;
import com.example.nhanct.dto.WrapMenuForRoleDto;
import com.example.nhanct.dto.WrapRoleForAccountDto;
import com.example.nhanct.entity.RoleEntity;
import com.example.nhanct.entity.RoleEntity;
import com.example.nhanct.entity.RoleMenuEntity;
import com.example.nhanct.entity.RoleUserEntity;
import com.example.nhanct.service.MenuService;
import com.example.nhanct.service.RoleMenuService;
import com.example.nhanct.service.RoleService;
import com.example.nhanct.service.RoleUserService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/role")
public class RoleController extends FunctionCommon {

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleMenuService roleMenuService;
	@Value("${adress.404}")
	private String adress404;
	@Autowired
	SecurityUtils myRole;
	@Value("${adress.406}")
	private String page406;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		Page<RoleEntity> page = roleService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<RoleEntity> listRole = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("role", listRole);
		return "role/index";
	}

	@GetMapping("add")
	public String add(ModelMap model, @RequestParam(value = "message", required = false) String message) {

		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		model.addAttribute("message", message);
		model.addAttribute("role", new RoleEntity());
		return "role/add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("role") RoleEntity role, BindingResult errors, ModelMap model) {

		String message = "";

		if (errors.hasErrors()) {
			menuListRole(model);
			model.addAttribute("roles", roleService.findAll());
			return "role/add";
		}
		if(roleService.isExceptionRoleCode(role)) {
			menuListRole(model);
			errors.rejectValue("roleCode", "role", "RoleCode is exist!");
			return "role/add";
		}
		if(!roleService.isExceptionFormatRoleCode(role)) {
			menuListRole(model);
			errors.rejectValue("roleCode", "role", "RoleCode must be begin with 'ROLE_'!");
			return "role/add";
		}
//		if(roleService.isException(role)) {
//			message = "F";
//			return "redirect:/admin/role/add?message="+message;
//		}

		roleService.add(role);
		return "redirect:/admin/role";
	}

	@GetMapping("edit")
	public String edit(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("role", roleService.getById(id));
			return "role/edit";
		} catch (Exception e) {
			return "redirect:" + adress404;
		}
	}

	@PostMapping("edit")
	public String edit(@Valid @ModelAttribute("role") RoleEntity role, BindingResult errors, ModelMap model) {
		if (errors.hasErrors() || duplicateRoleCode(role, errors)) {
			menuListRole(model);
			model.addAttribute("roles", roleService.findAll());
			return "role/edit";
		}

		roleService.update(role);
		return "redirect:/admin/role";
	}

	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, ModelMap model,
						 @RequestParam(value = "message", required = false) String message) {
		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		try {
			RoleEntity role = roleService.getById(id);
			model.addAttribute("role", role);
			model.addAttribute("message", message);
			return "role/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}

	@GetMapping("confirm-delete-post")
	public String delete1(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
			if(id != myRole.getPrincipal().getId() && roleService.delete(id) ) {
				return "redirect:/admin/role";
			}else {
				message = "F";
				return "redirect:/admin/role/confirm-delete?id=" + id + "&&message="+message;
			}

		} catch (DataIntegrityViolationException e) {
			message = "F";
			System.out.println("/admin/role/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/role/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			System.out.println("/admin/role/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/role/confirm-delete?id=" + id + "&&message="+message;
		}
	}

	@GetMapping("search")
	public String index(ModelMap model, @Param("keyword") String keyword) {

		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		List<RoleEntity> listRole = roleService.findAll(keyword);
		model.addAttribute("listRole", listRole);
		model.addAttribute("keyword", keyword);
		return "role/search";
	}


	/*------------------------------ begin ROLE FOR USER ------------------------------------------*/

	@GetMapping("menu-for-role/edit")
	public String menuForRoleGet(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.ROLE) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			//sai do ko phải lấy list của người login mà phải lấy list theo id của account mình click vào
//			Object[] tg = myRole.getPrincipal().getAuthorities().toArray();
//			List<String> menuNows = Stream.of(tg).map(Object::toString).collect(Collectors.toList());
			//fix
			List<String> menuNows = menuService.getListMenuRoleAtPresent(id);

			List<MenuForRoleDto> menuAll = menuService.getListMenuForUpdate();
			for (MenuForRoleDto menuForRoleDto : menuAll) {
				if (menuNows.contains(menuForRoleDto.getMenuCode())) {
					menuForRoleDto.setStatus(true);
				}
			}
			WrapMenuForRoleDto wMenuAll = new WrapMenuForRoleDto();
			wMenuAll.setMenuForRoleDto(menuAll);
			model.addAttribute("wMenuAll", wMenuAll);
			model.addAttribute("role", roleService.getById(id));
			return "role/menu-for-role-edit";
		} catch (Exception e) {
			return "redirect:" + adress404;
		}
	}

	@PostMapping("menu-for-role/edit")
	public String menuForRolePost(@ModelAttribute("wMenuAll") WrapMenuForRoleDto listMenu, @RequestParam("id") int id,
								  ModelMap model) {

		List<MenuForRoleDto> menuAll = listMenu.getMenuForRoleDto();         // listAll (cos true, false)

		List<String> menuBefore = menuService.getListMenuRoleAtPresent(id); // listMenuBefore

		List<String> menuAfter = new ArrayList<>(); // list vaiTroAfter
		for (MenuForRoleDto item : menuAll) {
			if(item.getMenuCode().equals("ROLE_ADMIN")){
				continue;
			}
			if(item.getStatus() == true) {
				menuAfter.add(item.getMenuCode());
			}
		}

		List<Integer> listIdMenuToAddNew = new ArrayList<Integer>();
		List<Integer> listIdMenuToDelete = new ArrayList<Integer>();

		for (MenuForRoleDto item : menuAll) {
			if(item.getMenuCode().equals("ROLE_ADMIN")
					|| item.getStatus() == false
					|| menuBefore.contains(item.getMenuCode())) {
				continue;
			}
			listIdMenuToAddNew.add(item.getId());
		}

		for (MenuForRoleDto item : menuAll) {
			if(item.getMenuCode().equals("ROLE_ADMIN")
					|| item.getStatus() == true
					|| !menuBefore.contains(item.getMenuCode())) {
				continue;
			}
			listIdMenuToDelete.add(item.getId());
		}

		for (Integer item : listIdMenuToAddNew) {
			RoleMenuEntity menuRole = new RoleMenuEntity();
			menuRole.setRoleId(id);
			menuRole.setMenuId(item);
			roleMenuService.save(menuRole);
		}

		for (Integer item : listIdMenuToDelete) {
			RoleMenuEntity menuRole = new RoleMenuEntity();
			menuRole.setRoleId(id);
			menuRole.setMenuId(item);
			roleMenuService.delete(menuRole);
		}

		return "redirect:/admin/role";
	}
	/*------------------------------ end ROLE FOR USER ------------------------------------------*/


	/*------------------------------PRIVATE METHOD------------------------------------------*/
	private boolean duplicateRoleCode(RoleEntity roleEntity, BindingResult errors) {
		RoleEntity entity = roleService.findByRoleCode(roleEntity.getRoleCode());
		if (entity == null || entity.getId() == roleEntity.getId())
			return false;
		else {
			errors.rejectValue("roleCode", "role", "This roleCode is already exist!");
			return true;
		}
	}

}
