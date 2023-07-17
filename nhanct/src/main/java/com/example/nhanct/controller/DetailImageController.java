//package com.example.nhanct.controller;
//
//import com.example.nhanct.consts.MenuConstant;
//import com.example.nhanct.entity.DetailImageEntity;
//import com.example.nhanct.service.DetailImageService;
//import com.example.nhanct.service.ImageService;
//import com.example.nhanct.utils.SecurityUtils;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("admin/product/detail-image")
//public class DetailImageController extends FunctionCommon {
//
//	@Autowired
//	private ImageService imageService;
//	@Autowired
//	private DetailImageService detailImageService;
//
//	@Value("${adress.406}")
//	private String page406;
//
//	@Value("${adress.404}")
//	private String adress404;
//
//	@Value("${file.upload-dir}")
//	private String uploadFolder;
//
//	@Value("${file.upload-result}")
//	private String uploadResult;
//
//	@Autowired
//	SecurityUtils myUser;
//
//	@GetMapping("")
//	public String index(ModelMap model, @RequestParam("id") int id) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//
//		List<DetailImageEntity> listDetailImage = detailImageService.getDetailImageByIdproduct(id);
//		model.addAttribute("listDetailImage", listDetailImage);
//		model.addAttribute("idProduct", id);
//		return "detailImage/index";
//	}
//
//	@GetMapping("add")
//	public String add(ModelMap model, @RequestParam("id") int id) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		DetailImageEntity detailImage = new DetailImageEntity();
//		detailImage.setProductId(id);
//		model.addAttribute("detailImage", detailImage);
//		model.addAttribute("idProduct", id);
//
//		return "detailImage/add";
//	}
//
//	@PostMapping("add")
//	public String add(@Valid @ModelAttribute("detailImage") DetailImageEntity detailImage, BindingResult errors,
//					  @RequestParam("fileUpload") MultipartFile file, ModelMap model) {
//		if(!file.isEmpty()) {
//			try {
//				detailImage.setImage(imageService.upload(file));
//			} catch (Exception e) {
//				errors.rejectValue("image", "detailImage", "Can't upload files");
//			}
//		}else errors.rejectValue("image", "detailImage", "You haven't uploaded the file yet!");
//
//		if(errors.hasErrors()) {
//			menuListRole(model);
//			return "detailImage/add";
//		}
//
//		detailImageService.add(detailImage);
//		return "redirect:/admin/product/detail-image?id=" + detailImage.getProductId();
//	}
//
//	@GetMapping("delete")
//	public String delete(@RequestParam("id") int id, @RequestParam("idSP") int idSP, ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//			imageService.delete(detailImageService.getById(id).getImage());
//			detailImageService.delete(id);
//			return "redirect:/admin/product/detail-image?id=" + idSP;
//		} catch (DataIntegrityViolationException e) {
//			model.addAttribute("message", "Unable to delete bound data!");
//			return index(model, idSP);
//		} catch(Exception e) {
//			model.addAttribute("message", "Error cannot delete data!");
//			return index(model, idSP);
//		}
//	}
//
//}
