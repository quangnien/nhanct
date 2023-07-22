package com.example.nhanct.controller;

import com.example.nhanct.entity.RoleEntity;
import com.example.nhanct.entity.UserEntity;
import com.example.nhanct.service.ImageService;
import com.example.nhanct.service.RoleService;
import com.example.nhanct.service.UserService;
import com.example.nhanct.utils.SecurityUtils;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin/role-for-user")
public class RoleUserController extends FunctionCommon {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ImageService imageService;
	@Value("${adress.406}")
	private String page406;
	@Value("${adress.404}")
	private String adress404;
	@Value("${file.upload-dir}")
	private String uploadFolder;
	@Value("${file.upload-result}")
	private String uploadResult;
	
	@Autowired
	SecurityUtils myUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap model) {
		return listPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
		
		Page<UserEntity> page = userService.findAll(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<UserEntity> listUser = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("user", listUser);
		return "user/index";
	}
	
	//tìm kiếm theo tentaikhoan
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String index(ModelMap model, @Param("keyword") String keyword) {
		List<UserEntity> listUser = userService.findAll(keyword);
		model.addAttribute("listUser", listUser);
		model.addAttribute("keyword", keyword);
		return "user/search";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(ModelMap model, @RequestParam(value = "message", required = false) String message) {
		
		//vai trò phải có trước User, nếu chưa có => báo lỗi phải thêm role trước
		List<RoleEntity> roles = roleService.findAll();
		if (roles.isEmpty())
			return "redirect:" + page406 + "?id=role";
		
		model.addAttribute("roles", roles);
		model.addAttribute("message", message);
		model.addAttribute("user", new UserEntity());
		return "user/add"; //VIEW
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("user") UserEntity user, BindingResult errors,
					  @RequestParam("fileUpload") MultipartFile file, @RequestParam String confirm,
					  ModelMap model) {
		String message = "";
		if (!file.isEmpty()) {
			try {
				user.setImage(imageService.upload(file));
			} catch (Exception e) {
				errors.rejectValue("image", "user", "Can't upload files");
			}
		} else
			errors.rejectValue("image", "user", "You haven't uploaded the file yet!");

		if (errors.hasErrors() || !passWordMatch(user.getPassword(), confirm, errors)
				|| duplicateEmail(user, errors)
				|| duplicateTenDangNhap(user, errors)) {
			menuListRole(model);
			model.addAttribute("roles", roleService.findAll());
			return "user/add";
		}
		if(userService.isException(user)) {
			message = "F";
			return "redirect:/admin/user/add?message="+message;
		}
		
		if(userService.isExceptionEmail(user)) {
			errors.rejectValue("email", "user", "Enter the correct format email");
			return "user/add";
		}
	
		/*__________ KIỂM TRA ĐỦ 18 CHƯA ? __________*/
		LocalDate start = LocalDate.now();
		
		Date input =  user.getDob();
		LocalDate end = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		long years = Math.abs(ChronoUnit.YEARS.between(start, end));

		if (years < 18) {
			errors.rejectValue("dob", "user", "Age not enough 18!");
			return "user/add";
		}
		/*__________ END KIỂM TRA ĐỦ 18 CHƯA ? __________*/

		userService.add(user);
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") int id, ModelMap model) {
		try {
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("user", userService.getById(id));
			return "user/edit";
		} catch (Exception e) {
			return "redirect:" + adress404;
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute("user") UserEntity user, BindingResult errors,
			@RequestParam("fileUpload") MultipartFile file, Model model) {
		if (!file.isEmpty()) {
			try {
				imageService.delete(user.getImage());
				user.setImage(imageService.upload(file));
			} catch (Exception e) {
				errors.rejectValue("image", "user", "Can't upload files");
			}
		} else
			errors.rejectValue("image", "user", "You haven't uploaded the file yet!");

		if (errors.hasErrors() || duplicateEmail(user, errors)
				|| duplicateTenDangNhap(user, errors)) {
			model.addAttribute("roles", roleService.findAll());
			return "user/edit";
		}
		if(userService.isExceptionEmail(user)) {
			errors.rejectValue("email", "user", "Enter the correct format email");
			return "user/edit";
		}
		
		/*__________ KIỂM TRA ĐỦ 18 CHƯA ? __________*/
		LocalDate start = LocalDate.now();
		
		Date input =  user.getDob();
		LocalDate end = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		long years = Math.abs(ChronoUnit.YEARS.between(start, end));

		if (years < 18) {
			errors.rejectValue("dob", "user", "Age not enough 18!");
			return "user/add";
		}
		/*__________ END KIỂM TRA ĐỦ 18 CHƯA ? __________*/
		
		userService.update(user);
		return "redirect:/admin/user";
	}

	
	@GetMapping("confirm-delete")
	public String delete(@RequestParam("id") int id, ModelMap model,
			@RequestParam(value = "message", required = false) String message) {
		try {
			UserEntity user = userService.getById(id);
			model.addAttribute("user", user);
			model.addAttribute("message", message);
			return "user/confirm-delete";
		} catch (Exception e) {
			return "redirect:"+adress404;
		}
	}
	
	@GetMapping("confirm-delete-post")
	public String delete1(@RequestParam("id") int id, ModelMap model) {
		String message = "";
		try {
			if(id != myUser.getPrincipal().getId() && userService.delete(id) ) {
				imageService.delete(userService.getById(id).getImage());
				userService.delete(id);
				return "redirect:/admin/user";
				
			}else {
				message = "F";
				return "redirect:/admin/user/confirm-delete?id=" + id + "&&message="+message;
			}
			
		} catch (DataIntegrityViolationException e) {
			message = "F";
			System.out.println("/admin/user/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/user/confirm-delete?id=" + id + "&&message="+message;
		} catch(Exception e) {
			message = "F";
			System.out.println("/admin/user/confirm-delete?id=" + id + " &&message= "+message);
			return "redirect:/admin/user/confirm-delete?id=" + id + "&&message="+message;
		}
	}

	@RequestMapping(value = "change-password", method = RequestMethod.GET)
	public String password(@RequestParam int id, ModelMap model) {
		model.addAttribute("user", userService.getById(id));
		return "user/change-password";
	}

	@RequestMapping(value = "change-password", method = RequestMethod.POST)
	public String password(@Valid @ModelAttribute("user") UserEntity user, BindingResult errors,
			@RequestParam String confirm) {
		if (passWordMatch(user.getPassword(), confirm, errors) && !errors.hasFieldErrors("password")) {
			userService.updatePassword(user);
			return "redirect:/admin/user";
		}
		// userService.changePassword(user);
		return "user/change-password";
	}

	/*------------------------------PRIVATE METHOD------------------------------------------*/
	private boolean passWordMatch(String password, String passConfirm, BindingResult errors) {
		if (password.equals(passConfirm))
			return true;
		errors.rejectValue("password", "user", "Confirmation password does not match!");
		return false;
	}

	private boolean duplicateEmail(UserEntity user, BindingResult errors) {
		UserEntity entity = userService.findByEmail(user.getEmail());
		if (entity == null || entity.getId() == user.getId())
			return false;
		else {
			errors.rejectValue("email", "user", "This email is already registered!");
			return true;
		}
	}
	
	public boolean duplicateTenDangNhap(UserEntity user, BindingResult errors) {
		UserEntity entity = userService.findByTenTaiKhoanUser(user.getUserName().trim());
		if (entity == null || entity.getId() == user.getId())
			return false;
		else {
			errors.rejectValue("userName", "user", "This Username already exists!");
			return true;
		}
	}

}
