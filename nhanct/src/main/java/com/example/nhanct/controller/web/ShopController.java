package com.example.nhanct.controller.web;

import com.example.nhanct.entity.*;
import com.example.nhanct.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private ImageService imageService;
//	@Autowired
//	private ReviewService reviewService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DetailImageService detailImageService;
	@Autowired
	private ImageToSearchService imageToSearchService;

	@GetMapping("")
	public String index(ModelMap model, HttpSession httpSession) {

		List<BrandEntity> listBrand = brandService.findAll();
		model.addAttribute("listBrand", listBrand);

		try {
			if (httpSession.getAttribute("userName") != null) {
				String userName = (String) httpSession.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}

		List<ProductEntity> top3acer = productService.findTop3ByBrand(listBrand.get(0));
		List<ProductEntity> top3dell = productService.findTop3ByBrand(listBrand.get(1));
		List<ProductEntity> top3hp = productService.findTop3ByBrand(listBrand.get(2));
		List<ProductEntity> top3apple = productService.findTop3ByBrand(listBrand.get(3));
		List<ProductEntity> top3lenovo = productService.findTop3ByBrand(listBrand.get(4));
		//find...by...(ENTITY)
		model.addAttribute("top3acer", top3acer);
		model.addAttribute("top3dell", top3dell);
		model.addAttribute("top3hp", top3hp);
		model.addAttribute("top3apple", top3apple);
		model.addAttribute("top3lenovo", top3lenovo);

		Page<ProductEntity> page1 = productService.findTop4ByBrandOrderBySoluongDesc(listBrand.get(0).getId(),1);
		Page<ProductEntity> page2 = productService.findTop4ByBrandOrderBySoluongDesc(listBrand.get(1).getId(),1);
		Page<ProductEntity> page3 = productService.findTop4ByBrandOrderBySoluongDesc(listBrand.get(2).getId(),1);
		Page<ProductEntity> page4 = productService.findTop4ByBrandOrderBySoluongDesc(listBrand.get(3).getId(),1);
		Page<ProductEntity> page5 = productService.findTop4ByBrandOrderBySoluongDesc(listBrand.get(4).getId(),1);
		List<ProductEntity> top4acertobesoldout = page1.getContent();
		List<ProductEntity> top4delltobesoldout = page2.getContent();
		List<ProductEntity> top4hptobesoldout = page3.getContent();
		List<ProductEntity> top4appletobesoldout = page4.getContent();
		List<ProductEntity> top4lenovotobesoldout = page5.getContent();
		model.addAttribute("top4acertobesoldout", top4acertobesoldout);
		model.addAttribute("top4delltobesoldout", top4delltobesoldout);
		model.addAttribute("top4hptobesoldout", top4hptobesoldout);
		model.addAttribute("top4appletobesoldout", top4appletobesoldout);
		model.addAttribute("top4lenovotobesoldout", top4lenovotobesoldout);

		return "web/shop/index";
	}

	@GetMapping("{id}/{categoryName}")
	public String category(@PathVariable int id, @PathVariable String categoryName, ModelMap model,
			HttpSession httpSession) {

		List<CategoryEntity> listCategory = categoryService.findAll();
		model.addAttribute("listCategory", listCategory);

		try {
			if (httpSession.getAttribute("userName") != null) {
				String userName = (String) httpSession.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}

		List<ProductEntity> listProduct = productService.finByCategoryId(id);
		model.addAttribute("listProduct", listProduct);

		model.addAttribute("categoryName", categoryName);

		return "web/shop/shop";
	}

	@GetMapping("search")
	public String index(ModelMap model, @RequestParam("keyword") String keyword, HttpSession httpSession) {

		List<CategoryEntity> listCategory = categoryService.findAll();
		model.addAttribute("listCategory", listCategory);

		try {
			if (httpSession.getAttribute("userName") != null) {
				String userName = (String) httpSession.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}

		List<ProductEntity> listProduct = productService.findAllByKeyword(keyword);
		model.addAttribute("listProduct", listProduct);

		model.addAttribute("keyword", keyword);
		return "web/shop/search";
	}

//	@RequestMapping(value = "detail/{pageNumber}", method = RequestMethod.GET)
//	public String detail(@RequestParam("id") int id, ModelMap model, HttpSession httpSession,
//			@PathVariable("pageNumber") int currentPage) {
//		defaultChitiet(id, model, httpSession, currentPage);
////		model.addAttribute("review", new ReviewEntity());
//		return "web/shop/detail";
//	}

	@GetMapping("detail")
	public String detail(@RequestParam("id") int id, ModelMap model, HttpSession httpSession) {
		defaultChitiet(id, model, httpSession);
//		model.addAttribute("review", new ReviewEntity());
		return "web/shop/detail";
	}

//	@PostMapping("detail/{pageNumber}")
//	public String detail(@RequestParam("id") int id, HttpSession httpSession,
//						  @Valid @ModelAttribute("review") ReviewEntity review, BindingResult errors,
//						  @RequestParam("fileUpload") MultipartFile file, ModelMap model, @PathVariable("pageNumber") int currentPage) {
    @PostMapping("detail")
    public String detail(@RequestParam("id") int id, HttpSession httpSession, BindingResult errors
            , ModelMap model) {

		if (httpSession.getAttribute("userName") == null)
			return "redirect:/login";

//		if(!file.isEmpty()) {
//			try {
//				review.setImage(imageService.upload(file));
//			} catch (Exception e) {
//				errors.rejectValue("image", "review", "Can't upload files");
//			}
//		}else errors.rejectValue("image", "review", "You haven't uploaded the file yet!");

		if(errors.hasErrors()) {
			defaultChitiet(id, model, httpSession);
			return "web/shop/detail";
		}

//		reviewService.add(review);

		defaultChitiet(id, model, httpSession);

//		model.addAttribute("mess_review",
//				"Bạn vừa đánh giá đơn hàng, mỗi lượt đánh giá của bạn là động lực để chúng tôi hoàn thiện Product hơn!");

		return "web/shop/detail";
	}

//	public void defaultChitiet(int id, ModelMap model, HttpSession httpSession,int currentPage) {
//
//		List<CategoryEntity> listCategory = categoryService.findAll();
//		List<ProductEntity> listProduct = productService.findAll();
//		model.addAttribute("listProduct", listProduct);
//		model.addAttribute("listCategory", listCategory);
//
//		try {
//			if (httpSession.getAttribute("userName") != null) {
//				String userName = (String) httpSession.getAttribute("userName");
//				String firstOfName = userName.substring(0, 1);
//				model.addAttribute("firstOfName", firstOfName);
//			}
//		} catch (Exception e) {
//		}
//
//		model.addAttribute("idProduct", id);
//
//		ProductEntity product = productService.getById(id);
//		model.addAttribute("product", product);
//
//		List<DetailImageEntity> detailImage = detailImageService.getDetailImageByIdproduct(id);
//		model.addAttribute("images", detailImage);
//
//		//đoạn xử lý lấy tên category của Product đang xem
//		CategoryEntity categoryon = null; // category của Product đang xem
//		String categoryName = null;
//		for (CategoryEntity item : listCategory) { // duyệt 1 list danhmus, trùng với category đang xem thì GET
//			if (product.getCategoryId() == item.getId()) {
//				categoryon = item;
//				categoryName = item.getCategoryName();
//				break;
//			}
//		}
//		model.addAttribute("categoryName", categoryName);
//
//		List<ProductEntity> top4category = productService.findTop4byCategory(categoryon);
//		model.addAttribute("top4category", top4category);
//
//		// kiểm tra đã login chưa - phần đánh giá
//		if (httpSession.getAttribute("userName") != null) {
//			Optional<CustomerEntity> opt = customerService.findByUserName(httpSession.getAttribute("userName").toString());
//			model.addAttribute("customer", opt.get());
//		}
//
//	}

	public void defaultChitiet(int id, ModelMap model, HttpSession httpSession) {

		List<CategoryEntity> listCategory = categoryService.findAll();
		List<ProductEntity> listProduct = productService.findAll();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("listCategory", listCategory);

		try {
			if (httpSession.getAttribute("userName") != null) {
				String userName = (String) httpSession.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}

		model.addAttribute("idProduct", id);

		ProductEntity product = productService.getById(id);
		model.addAttribute("product", product);

		List<DetailImageEntity> detailImage = detailImageService.getDetailImageByIdproduct(id);
		model.addAttribute("images", detailImage);

		//đoạn xử lý lấy tên category của Product đang xem
		CategoryEntity categoryon = null; // category của Product đang xem
		String categoryName = null;
		for (CategoryEntity item : listCategory) { // duyệt 1 list danhmus, trùng với category đang xem thì GET
			if (product.getCategoryId() == item.getId()) {
				categoryon = item;
				categoryName = item.getCategoryName();
				break;
			}
		}
		model.addAttribute("categoryName", categoryName);

		List<ProductEntity> top4category = productService.findTop4byCategory(categoryon);
		model.addAttribute("top4category", top4category);

		// kiểm tra đã login chưa - phần đánh giá
		if (httpSession.getAttribute("userName") != null) {
			Optional<CustomerEntity> opt = customerService.findByUserName(httpSession.getAttribute("userName").toString());
			model.addAttribute("customer", opt.get());
		}

	}

//	/* SEARCH PRODUCT BY IMAGE */
//	@RequestMapping(value = "api/search-by-image", method = RequestMethod.POST)
//	//*@Valid @ModelAttribute("image") ProductEntity product, BindingResult errors,*//*
////	@ResponseBody
//	public String saveImgTimKiem (
//			@Valid @ModelAttribute("hinhanhsearch") ImageToSearch hinhanhsearch, BindingResult errors,
//			@RequestParam("fileUpload") MultipartFile file, ModelMap model) {
//		if(!file.isEmpty()) {
//			try {
//				hinhanhsearch.setImage(imageService.upload(file));
//			} catch (Exception e) {
//				String message = "uploadFileIsFalse";
//				model.addAttribute("message", message);
//				return "web/shop/search-img";
////				return "redirect:/shop/api/search-by-image?message="+message;
////				errors.rejectValue("image", "hinhanhsearch", "Can't upload files");
//			}
//		}else {
//			String message = "uploadFileIsFalse";
//			model.addAttribute("message", message);
//			return "web/shop/search-img";
////			return "redirect:/shop/api/search-by-image?message="+message;
//		}
//
//		if(errors.hasErrors()) {
//			return "product/add";
//		}
//
//		/* add imageToSearch to db */
//		String pathImage = hinhanhsearch.getImage();
//		pathImage = pathImage.replaceAll("\\/", "\\\\");
//		imageToSearchService.add(hinhanhsearch);
//
//
//		String url = "http://127.0.0.1:8090/submit?path=F:\\java\\PTIT\\cuahangdienmaySourceTree\\src\\main\\resources\\static"+pathImage;
//		RestTemplate restTemplate = new RestTemplate();
//		String kq = restTemplate.getForObject(url, String.class);
//
////		String kq2 = kq.replaceAll("[^a-zA-Z0-9]+","");
//		String kq2 = kq.replaceAll("\\{", "");
//		kq2 = kq2.replaceAll("ids", "");
//		kq2 = kq2.replaceAll(":", "");
//		kq2 = kq2.replaceAll("\\[", "");
//		kq2 = kq2.replaceAll("\\]", "");
//		kq2 = kq2.replaceAll("\\}", "");
//		kq2 = kq2.replaceAll("\"", "");
////		kq2=kq2.substring(2);
//
//		String firstString = kq2.substring(0,2);
//		if(firstString.equals("nc") || firstString.equals("mh")){
//			firstString = kq2.substring(0,5);
//		}else {
//			firstString = kq2.substring(0,4);
//		}
//
//		int index = -1;
//
//		List<String> myList = new ArrayList<String>(Arrays.asList(kq2.split(",")));
//
//		List<ProductEntity> listProduct = productService.findByListMaDinhDanh(myList);
//
//		ProductEntity firstAI = new ProductEntity();
//		if(listProduct.size()>0){
//			for (int i = 0; i< listProduct.size(); i++) {
//				if(listProduct.get(i).getMaDinhDanh().equals(firstString)){
//					index = i;
//					firstAI = listProduct.get(i);
//					listProduct.remove(i);
//					break;
//				}
//			}
//		}
//
//		model.addAttribute("firstAI", firstAI);
//		model.addAttribute("listProduct", listProduct);
//		model.addAttribute("hinhanhsearch", hinhanhsearch.getImage());
//
//		return "web/shop/search-img";
//	}

}
