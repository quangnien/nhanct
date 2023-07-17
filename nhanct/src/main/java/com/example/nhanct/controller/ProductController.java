//package com.example.nhanct.controller;
//
//import com.example.nhanct.consts.MenuConstant;
//import com.example.nhanct.entity.*;
//import com.example.nhanct.service.*;
//import com.example.nhanct.utils.SecurityUtils;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.data.domain.Page;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("admin/product")
//public class ProductController extends FunctionCommon {
//
//	@Autowired
//	private ProductService productService;
//	@Autowired
//	private CategoryService categoryService;
//	@Autowired
//	private BrandService brandService;
////	@Autowired
////	private SaleService saleService;
//	@Autowired
//	private ImageService imageService;
//	@Autowired
//	private DetailImageService detailImageService;
//	@Autowired
//	private SecurityUtils myUser;
//	@Value("${adress.406}")
//	private String page406;
//	@Value("${adress.404}")
//	private String adress404;
//
//	@Value("${file.upload-dir}")
//	private String uploadFolder;
//
//	@Value("${file.upload-result}")
//	private String uploadResult;
//
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public String index(ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		return listPage(model, 1);
//	}
//
//	@GetMapping("/page/{pageNumber}")
//	public String listPage(ModelMap model, @PathVariable("pageNumber") int currentPage) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		Page<ProductEntity> page = productService.findAll(currentPage);
//		long totalItems = page.getTotalElements();
//		int totalPages = page.getTotalPages();
//
//		List<ProductEntity> listProduct = page.getContent();
//
//		model.addAttribute("currentPage", currentPage);
//		model.addAttribute("totalItems", totalItems);
//		model.addAttribute("totalPages", totalPages);
//
//		model.addAttribute("listProduct", listProduct);
//		return "product/index";
//	}
//
//	@GetMapping("search")
//	public String index(ModelMap model, @Param("keyword") String keyword) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		List<ProductEntity> listProduct = productService.findAllByKeyword(keyword);
//		model.addAttribute("listProduct", listProduct);
//		model.addAttribute("keyword", keyword);
//		return "product/search";
//	}
//
//	@GetMapping("add")
//	public String add(ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		List<CategoryEntity> listCategory = categoryService.findAll();
//		if(listCategory.isEmpty()) return "redirect:"+page406 + "?id=category";
//
//		List<BrandEntity> listBrand = brandService.findAll();
//		if(listBrand.isEmpty()) return "redirect:"+page406 + "?id=brand";
//
////		List<SaleEntity> listSale = saleService.findAll();
////		if(listSale.isEmpty()) return "redirect:"+page406 + "?id=sale";
//
//		model.addAttribute("listCategory", listCategory);
//		model.addAttribute("listBrand", listBrand);
////		model.addAttribute("listSale", listSale);
//		model.addAttribute("product", new ProductEntity());
//		return "product/add";
//	}
//
//	@PostMapping("add")
//	public String add(@Valid @ModelAttribute("product") ProductEntity product, BindingResult errors,
//					  @RequestParam("fileUpload") MultipartFile file, ModelMap model) {
//		if(!file.isEmpty()) {
//			try {
//				product.setImage(imageService.upload(file));
//			} catch (Exception e) {
//				errors.rejectValue("image", "product", "Can't upload files");
//			}
//		}else errors.rejectValue("image", "product", "You haven't uploaded the file yet!");
//
//		if(errors.hasErrors()) {
//			model.addAttribute("listCategory", categoryService.findAll());
//			model.addAttribute("listBrand", brandService.findAll());
////			model.addAttribute("listSale", saleService.findAll());
//			menuListRole(model);
//			return "product/add";
//		}
//
//		productService.add(product);
//		return "redirect:/admin/product";
//	}
//
//	@GetMapping("edit")
//	public String edit(@RequestParam("id") int id, ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//			model.addAttribute("listCategory", categoryService.findAll());
//			model.addAttribute("listBrand", brandService.findAll());
////			model.addAttribute("listSale", saleService.findAll());
//			model.addAttribute("product", productService.getById(id));
//			return "product/edit";
//		} catch (Exception e) {
//			return "redirect:"+adress404;
//		}
//
//	}
//
//	@PostMapping("edit")
//	public String edit(@Valid @ModelAttribute("product") ProductEntity product, BindingResult errors,
//			@RequestParam("fileUpload") MultipartFile file, ModelMap model) {
//		if(!file.isEmpty()) {
//			try {
//				imageService.delete(product.getImage());
//				product.setImage(imageService.upload(file));
//			} catch (Exception e) {
//				errors.rejectValue("image", "product", "Can't upload files");
//			}
//		}else errors.rejectValue("image", "product", "You haven't uploaded the file yet!");
//
//		if(errors.hasErrors()) {
//			menuListRole(model);
//			model.addAttribute("listCategory", categoryService.findAll());
//			model.addAttribute("listBrand", brandService.findAll());
////			model.addAttribute("listSale", saleService.findAll());
//			return "product/edit";
//		}
//		productService.update(product);
//		return "redirect:/admin/product";
//	}
//
//	@GetMapping("confirm-delete")
//	public String delete(@RequestParam("id") int id, ModelMap model,
//			@RequestParam(value = "message", required = false) String message) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		try {
//			ProductEntity product = productService.getById(id);
//			model.addAttribute("product", product);
//			model.addAttribute("message", message);
//			return "product/confirm-delete";
//		} catch (Exception e) {
//			return "redirect:"+adress404;
//		}
//	}
//
//	@GetMapping("confirm-delete-post")
//	public String confirmDeletePost(@RequestParam("id") int id, ModelMap model) {
//		if(hasRoleAuthor(MenuConstant.PRODUCT) == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//		String message = "";
//		String img_avt = (productService.getById(id).getImage());
//		try {
//			if(productService.delete(id) == true) {
//				List<DetailImageEntity> listDetailImage = detailImageService.getDetailImageByIdproduct(id);
//				for (DetailImageEntity detailImage : listDetailImage) {
//					imageService.delete(detailImage.getImage());
//					detailImageService.delete(detailImage.getId());
//				}
//				imageService.delete(img_avt);
//			}
//			return "redirect:/admin/product";
//
//		} catch (DataIntegrityViolationException e) {
//			message = "F";
//			return "redirect:/admin/product/confirm-delete?id=" + id + "&&message="+message;
//		} catch(Exception e) {
//			message = "F";
//			return "redirect:/admin/product/confirm-delete?id=" + id + "&&message="+message;
//		}
//	}
//}
