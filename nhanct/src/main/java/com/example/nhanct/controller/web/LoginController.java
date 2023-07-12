package com.example.nhanct.controller.web;

import com.example.nhanct.entity.*;
import com.example.nhanct.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DetailImageService detailImageService;

	@GetMapping("signup")
	public String add(ModelMap model) {
		List<CategoryEntity> listCategory = categoryService.findAll();
		List<ProductEntity> listProduct = productService.findAll();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("listCategory", listCategory);
		
		model.addAttribute("customer", new CustomerEntity());
		return "web/shop/signup";
	}

	@PostMapping("signup")
	public String add(@Valid @ModelAttribute("customer") CustomerEntity customer,
			BindingResult result,  ModelMap model) {
		if (result.hasErrors() || duplicateTenTaiKhoan(customer, result)
				|| duplicatemail(customer, result)) {
			return "web/shop/signup";
		}
		if(customerService.isExceptionEmail(customer)) {
			result.rejectValue("email", "customer", "Enter the correct format email");
			return "web/shop/signup";
		}
		

		customerService.add(customer);
		return "redirect:/login";
	}
	
	@GetMapping("login")
	public String index(ModelMap model) {
		
		List<CategoryEntity> listCategory = categoryService.findAll();
		List<ProductEntity> listProduct = productService.findAll();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("listCategory", listCategory);
		
		model.addAttribute("customer", new CustomerEntity());
		return "web/shop/login";
	}
	
	@PostMapping("login")
	public String login(@ModelAttribute("customer") CustomerEntity customer,
						HttpSession session, HttpServletRequest re, ModelMap model) {
		try {
			
			CustomerEntity kh = customerService.findByUserNameAndPasswordKH(customer.getUserName(),customer.getPassword());
			if (kh != null) {
				session.setAttribute("id", kh.getId());
				session.setAttribute("address", kh.getAddress());
				session.setAttribute("email", kh.getEmail());
				session.setAttribute("customerName", kh.getCustomerName());
				session.setAttribute("phone", kh.getPhone());
				session.setAttribute("userName", customer.getUserName());
				return "redirect:/shop";
			}
			else {
				model.addAttribute("messagelogin", "Login thất bại!");
				model.addAttribute("Khachhang", new CustomerEntity());
				return "web/shop/login";
			}
		} catch (Exception e) {
			model.addAttribute("messagelogin", "Login thất bại!");
			model.addAttribute("Khachhang", new CustomerEntity());
			return "web/shop/login";
		}
	}

	@GetMapping("my-account")
	public String myaccount(HttpSession session, ModelMap model) {
		
		List<CategoryEntity> listCategory = categoryService.findAll();
		List<ProductEntity> listProduct = productService.findAll();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("listCategory", listCategory);
		
		try {
			if (session.getAttribute("userName") != null) {
				String userName = (String) session.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}
		
		try {
			Optional<CustomerEntity> opt = customerService.findByUserName(session.getAttribute("userName").toString());
			if (opt.isPresent()) {
				model.addAttribute("customer", opt.get());
			} else {
				return "redirect:/login";
			}
			List<InvoiceEntity> list = invoiceService.findTop5byKhachhang(opt.get());
			model.addAttribute("invoicekh", list);

			return "web/shop/my-account";
		} catch (Exception e) {
			return "redirect:/login";
		}
	}

	@PostMapping("my-account")
	public String saveorupdate(ModelMap model, @Valid @ModelAttribute("customer") CustomerEntity Khachhang, BindingResult result, HttpSession httpSession) {
		
		try {
			if (httpSession.getAttribute("userName") != null) {
				String userName = (String) httpSession.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}
		
		if(customerService.isExceptionEmail(Khachhang)) {
			result.rejectValue("email", "customer", "Enter the correct format email");
			return "web/shop/my-account";
		}
		
		if(duplicateTenTaiKhoan(Khachhang, result)) {
			return "web/shop/my-account";
		}
		
		
		model.addAttribute("message", "Update Thông Tin!!!");

		customerService.update(Khachhang);
		
		model.addAttribute("customer", Khachhang);
		return "redirect:/my-account";
	}

	@GetMapping("detail/invoice/edit")
	public String edit(@RequestParam("id") int id, ModelMap model, HttpSession httpSession) {
		
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
		
		List<DetailInvoiceEntity> listdetailInvoice = invoiceService.getById(id).getDetailInvoice();

		int dongia = 0;
		for (DetailInvoiceEntity item : listdetailInvoice) {
			dongia += item.getSumMoney();
		}
		model.addAttribute("dongia", dongia);
		
		model.addAttribute("listdetailInvoice", listdetailInvoice);
		
		model.addAttribute("idInvoice", id);
		if (listdetailInvoice.size() > 0) {
			model.addAttribute("status", listdetailInvoice.get(0).getInvoice().getStatus());
		}
		else {
			model.addAttribute("status", null);
		}
		return "web/shop/thongtininvoice";
	}

	@GetMapping("detail/invoice/review")
	public String review(@RequestParam("id") int id,@RequestParam("idinvoice") int idinvoice,  ModelMap model, HttpSession httpSession) {

//		/* __ BEGIN lay san pham id __*/
//		DetailInvoiceEntity hdct = invoiceService.getById(idInvoice);
//		int id = hd.get

		List<CategoryEntity> listCategory = categoryService.findAll();
		List<ProductEntity> listProduct = productService.findAll();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("listCategory", listCategory);

		/* ____________________________ start ____________________________*/
		/* begin get sp đánh giá*/
		ProductEntity product = productService.getById(id);
		model.addAttribute("product", product);
//		model.addAttribute("review", new ReviewEntity());
		/* end get sp đánh giá*/

		model.addAttribute("idProduct", id);

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
		model.addAttribute("idinvoice", idinvoice);

		/* ____________________________ end ____________________________ */

		try {
			if (httpSession.getAttribute("userName") != null) {
				String userName = (String) httpSession.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}

//		List<DetailInvoiceEntity> listdetailInvoice = invoiceService.getById(id).getDetailInvoice();
//
//		int dongia = 0;
//		for (DetailInvoiceEntity item : listdetailInvoice) {
//			dongia += item.getSumMoney();
//		}
//		model.addAttribute("dongia", dongia);
//
//		model.addAttribute("listdetailInvoice", listdetailInvoice);
//
//		model.addAttribute("idInvoice", id);
//		if (listdetailInvoice.size() > 0) {
//			model.addAttribute("status", listdetailInvoice.get(0).getHoadon().getStatusInvoice());
//		}
//		else {
//			model.addAttribute("status", "null");
//		}
		return "web/shop/reviewproduct";
	}
	
	@GetMapping("detail/invoice/confirm-cancel")
	public String confirmCancel(@RequestParam("id") int id, ModelMap model, HttpSession httpSession) {
		
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
		
		List<DetailInvoiceEntity> listdetailInvoice = invoiceService.getById(id).getDetailInvoice();
		
		int dongia = 0;
		for (DetailInvoiceEntity item : listdetailInvoice) {
			dongia += item.getSumMoney();
		}
		model.addAttribute("dongia", dongia);
		
		model.addAttribute("listdetailInvoice", listdetailInvoice);
		
		model.addAttribute("idInvoice", id);
		if (listdetailInvoice.size() > 0) {
			model.addAttribute("status", listdetailInvoice.get(0).getInvoice().getStatus());
		}
		else {
			model.addAttribute("status", "null");
		}
		
		return "web/shop/confirmCancelInvoice";
	}
	
	@GetMapping("detail/invoice/confirm-cancel-post")
	public String confirmCancelPost(@RequestParam("id") int id, ModelMap model, HttpSession httpSession) {
		
		List<DetailInvoiceEntity> listdetailInvoice = invoiceService.getById(id).getDetailInvoice();
		
		/*--------- confirm-delete -> udpate product quantity ---------*/
		for (DetailInvoiceEntity item : listdetailInvoice) {
			ProductEntity sp = productService.getById(item.getProductId());
			
			/*update product quantity*/
			sp.setQuantity(sp.getQuantity() + item.getQuantity());

			/*update tình trạng*/
			sp.setStatus("Out of stock coming soon");
			productService.update(sp);
		}
		
		InvoiceEntity hoaDon= invoiceService.getById(id);
		hoaDon.setStatus("Khách hàng yêu câù hủy đơn hàng");
		invoiceService.update(hoaDon);
		return "redirect:/detail/invoice/edit?id="+id;
	}

	@RequestMapping(value = "logout-account", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		
		session.removeAttribute("id");
		session.removeAttribute("userName");
		session.removeAttribute("customerName");
		session.removeAttribute("email");
		session.removeAttribute("address");
		session.removeAttribute("phone");	

		return "redirect:/shop";

	}
	
	/*------------------------------PRIVATE METHOD------------------------------------------*/
	public boolean duplicateTenTaiKhoan(CustomerEntity customer, BindingResult errors) {
		CustomerEntity entity = customerService.findByUserNameKhiDangKyHoacSua(customer.getUserName().trim());
		if (entity == null || entity.getId() == customer.getId())
			return false;
		else {
			errors.rejectValue("userName", "customer", "This Username already exists!");
			return true;
		}
	}
	
	public boolean duplicatemail(CustomerEntity customer, BindingResult errors) {
		CustomerEntity entity = customerService.findbyemail(customer.getEmail().trim());
		if (entity == null || entity.getId() == customer.getId())
			return false;
		else {
			errors.rejectValue("email", "customer", "Email này đã tồn tại!");
			return true;
		}
	}
}
	
