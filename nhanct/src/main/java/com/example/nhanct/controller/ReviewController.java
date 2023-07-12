//package com.example.nhanct.controller;
//
//import com.example.nhanct.entity.ReviewEntity;
//import com.example.nhanct.service.ImageService;
//import com.example.nhanct.service.ReviewService;
//import com.example.nhanct.utils.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.Page;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/review")
//public class ReviewController extends FunctionCommon {
//
//	@Autowired
//	private ReviewService reviewService;
//
//	@Value("${adress.404}")
//	private String adress404;
//
//	@Autowired
//	private ImageService imageService;
//
//	@Autowired
//	SecurityUtils myUser;
//
//	@GetMapping("")
//	public String index(ModelMap model) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		return listPage(model,1);
//	}
//
//	@GetMapping("/page/{pageNumber}")
//	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		Page<ReviewEntity> page = reviewService.findAll(currentPage);
//		long totalItems = page.getTotalElements();
//		long totalPages = page.getTotalPages();
//
//		List<ReviewEntity> listReview = page.getContent();
//
//		model.addAttribute("currentPage", currentPage);
//		model.addAttribute("totalItems", totalItems);
//		model.addAttribute("totalPages", totalPages);
//
//		model.addAttribute("reviews",listReview);
//
//		return "review/index";
//	}
//
//	@GetMapping("confirm-delete")
//	public String delete(@RequestParam("id") int id, ModelMap model,
//			@RequestParam(value = "message", required = false) String message) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//
//			ReviewEntity review = reviewService.getById(id);
//			model.addAttribute("review", review);
//			model.addAttribute("message", message);
//			return "review/confirm-delete";
//		} catch (Exception e) {
//			return "redirect:"+adress404;
//		}
//	}
//
//	@GetMapping("confirm-delete-post")
//	public String delete1(@RequestParam("id") int id, ModelMap model) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		String message = "";
//		String img_avt = (reviewService.getById(id).getImage());
//		try {
//			if(reviewService.delete(id) == true) {
//				imageService.delete(img_avt);
//			}
//			return "redirect:/admin/review";
//		} catch (DataIntegrityViolationException e) {
//			message = "F";
//			return "redirect:/admin/review/confirm-delete?id=" + id + "&&message="+message;
//		} catch(Exception e) {
//			message = "F";
//			return "redirect:/admin/review/confirm-delete?id=" + id + "&&message="+message;
//		}
//	}
//
//	@GetMapping("search")
//	public String seach(ModelMap model, @Param("keyword") int keyword) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		List<ReviewEntity> listReviews = reviewService.findAllByKeyWord(keyword);
//		model.addAttribute("listreview", listReviews);
//		model.addAttribute("keyword", keyword);
//		return "review/search";
//	}
//
//	/*---------------- begin AUTHOR ------------*/
//	private boolean hasRoleAuthor () {
//		Object[] tg = SecurityUtils.getPrincipal().getAuthorities().toArray();
//		for (Object object : tg) {
//			if (object.toString().equals("ROLE_DANHGIA")) {
//				return true;
//			}
//		}
//		return false;
//	}
//	/*---------------- end AUTHOR ------------*/
//
//}
