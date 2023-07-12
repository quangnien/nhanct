package com.example.nhanct.controller;

import com.example.nhanct.consts.MenuConstant;
import com.example.nhanct.entity.BrandEntity;
import com.example.nhanct.entity.CategoryEntity;
import com.example.nhanct.service.CategoryService;
import com.example.nhanct.utils.MessageUtil;
import com.example.nhanct.utils.SecurityUtils;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/category")
public class CategoryController extends FunctionCommon {

	@Autowired
	private CategoryService categoryService;
	@Value("${adress.404}")
	private String adress404;

	@GetMapping("")
	public String index(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.CATEGORY) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		return listPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		if(hasRoleAuthor(MenuConstant.CATEGORY) == false) {
			return "deny/deny";
		}
		menuListRole(model);

		Page<CategoryEntity> page = categoryService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<CategoryEntity> listCategory = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("listCategory", listCategory);
		return "category/index";
	}

	@GetMapping("search")
	public String index(ModelMap model, @RequestParam("keyword") String keyword) {
		menuListRole(model);
		List<CategoryEntity> listCategory = categoryService.findAll(keyword);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("keyword", keyword);
		return "category/search";
	}

	@GetMapping("add")
	public String add(ModelMap model) {
		if(hasRoleAuthor(MenuConstant.CATEGORY) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		model.addAttribute("category", new CategoryEntity());
		return "category/add";
	}

	@PostMapping("add")
	public String add(@Valid @ModelAttribute("category") CategoryEntity category, BindingResult errors,
					  ModelMap model) {
		if(errors.hasErrors() || duplicateCategory(category, errors)) {
			menuListRole(model);
			return "category/add";
		}

		categoryService.add(category);
		return "redirect:/admin/category";
	}

	@GetMapping("edit")
	public String edit(@RequestParam("id") int id , ModelMap model) {
		if(hasRoleAuthor(MenuConstant.CATEGORY) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			CategoryEntity category = categoryService.getById(id);
			model.addAttribute("category", category);
			return "category/edit";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}

	@PostMapping("edit")
	public String edit(@Valid @ModelAttribute("category") CategoryEntity category, BindingResult errors, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.CATEGORY) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		if(errors.hasErrors() || duplicateCategory(category, errors)) {
			menuListRole(model);
			return "category/edit";
		}
		categoryService.update(category);
		return "redirect:/admin/category";
	}
	
	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, ModelMap model,
			@RequestParam(value = "message", required = false) String message) {
		if(hasRoleAuthor(MenuConstant.CATEGORY) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		try {
			CategoryEntity category = categoryService.getById(id);
			model.addAttribute("category", category);
			model.addAttribute("message", message);
			return "category/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}
	
	@GetMapping("confirm-delete-post")
	public String delete1(@RequestParam("id") int id, ModelMap model) {
		if(hasRoleAuthor(MenuConstant.CATEGORY) == false) {
			return "deny/deny";
		}
		menuListRole(model);
		String message = "";
		try {
			categoryService.delete(id);
			return "redirect:/admin/category";
		} catch (DataIntegrityViolationException e) {
			message = "F";
			System.out.println("/admin/category/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/category/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			System.out.println("/admin/category/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/category/confirm-delete?id=" + id + "&&message="+message;
		}
	}
	
	/*------------------------------PRIVATE METHOD------------------------------------------*/
	public boolean duplicateCategory(CategoryEntity category, BindingResult errors) {
		CategoryEntity entity = categoryService.findByCategory(category.getCategoryName().trim());
		if(entity == null || entity.getId() == category.getId()) return false;
		else {
			errors.rejectValue("categoryName", "category", "This category name is exists!");
			return true;
		}
	}

}
