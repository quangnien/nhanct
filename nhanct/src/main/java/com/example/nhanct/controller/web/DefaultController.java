package com.example.nhanct.controller.web;

import com.example.nhanct.entity.*;
import com.example.nhanct.repository.ConfirmationTokenRepository;
import com.example.nhanct.repository.CustomerRepository;
import com.example.nhanct.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@Controller
public class DefaultController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ConfirmationTokenRepository confirmationtokenrepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private Tokenservice tokenService;
	
	@GetMapping("fogot")
	public String fogot(ModelMap model, CustomerEntity kh) {
		List<CategoryEntity> listCategory = categoryService.findAll();
		List<ProductEntity> listProduct = productService.findAll();
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("listCategory", listCategory);
		model.addAttribute("kh", kh);
		
		return "web/shop/page-recoverpw";
	}
	
	@PostMapping("fogot")
	public String fogor(ModelMap model, @Valid @ModelAttribute("kh") CustomerEntity kh, BindingResult errors) {

		if(customerService.isExceptionEmail(kh)) {
			errors.rejectValue("email", "kh", "Enter the correct format email");
			return "web/Shop/page-recoverpw";
		}

		CustomerEntity checkkh = customerService.findbyemail(kh.getEmail());
		try {
			if(checkkh != null) {
				
				ConfirmationTokenEntity confirmationTokenEntity = new ConfirmationTokenEntity(checkkh);
				confirmationtokenrepository.save(confirmationTokenEntity);
				
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(kh.getEmail());
				message.setSubject("Fogot password");
				message.setText("Xin chào, để xác nhận và đổi mật khẩu hãy truy cập vào link : " + "http://localhost:7070/confirm-reset?token=" + confirmationTokenEntity.getConfirmationToken());
				this.emailSender.send(message);
			}else {
				model.addAttribute("message", "Không tìm thấy User email đã đăng ký");
				return "web/Shop/page-recoverpw";
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("kh", kh);
		return "web/shop/page-confirm-mail";
	}
	

	@GetMapping("confirm-reset")
	public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		
		ConfirmationTokenEntity token = confirmationtokenrepository.findByConfirmationTokenAndFlag(confirmationToken, 0); // 0-> valid
		
		if(token != null) {
			CustomerEntity kh = customerService.findbyemail(token.getCustomer().getEmail());
			modelAndView.addObject("customer", kh);
			modelAndView.addObject("id", kh.getId());
			modelAndView.setViewName("web/shop/resetPassword");
			
		}
		else {
			modelAndView.addObject("message", "The token was timeout 120s!");
			modelAndView.setViewName("web/shop/timeout");
		}
		return modelAndView;
	}
	
	@PostMapping("confirm-reset")
	public String resetUserPassword(ModelAndView modelAndView,@ModelAttribute("customer") CustomerEntity kh, @RequestParam("id") int id) {
		customerRepository.updatePassword(kh.getPassword(), id);
		return "redirect:/login";
	}
	
	@GetMapping("contact")
	public String contactGet(ModelMap model, HttpSession httpSession) {
		
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
		
		model.addAttribute("contact", new ContactEntity());
		
		return "web/shop/contact";
	}
	
	@PostMapping("contact")
	public String contactPost(ModelMap model,
			@Valid @ModelAttribute("contact") ContactEntity contact, BindingResult errors) {
		if(contactService.isExceptionEmail(contact)) {
			errors.rejectValue("yourEmail", "contact", "Enter the correct format email");
			return "web/shop/contact";
		}
		contactService.add(contact);
		return "redirect:/shop";
	}
}
