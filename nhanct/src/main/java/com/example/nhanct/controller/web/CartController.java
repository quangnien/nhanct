package com.example.nhanct.controller.web;

import com.example.nhanct.dto.ItemDto;
import com.example.nhanct.entity.CategoryEntity;
import com.example.nhanct.entity.DetailInvoiceEntity;
import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.ProductEntity;
import com.example.nhanct.service.*;
import com.example.nhanct.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private DetailInvoiceService detailInvoiceService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	SecurityUtils myUser;
	
	@GetMapping("")
	public String index(ModelMap model, HttpSession session,
						@RequestParam(value = "message", required = false) String message) {
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

		model.addAttribute("message", message);
		model.put("total", total(session));
		return "web/cart/index";
	}
	
	@GetMapping("buy/{id}")
	public String buy(@PathVariable("id") int id, ModelMap model, HttpSession session) {
		
		try {
			if (session.getAttribute("userName") != null) {
				String userName = (String) session.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}
		
		if(session.getAttribute("cart") == null) {
			List<ItemDto> cart = new ArrayList<>();
			cart.add(new ItemDto(productService.getById(id), 1));
			session.setAttribute("cart", cart);
		}else {
			List<ItemDto> cart = (List<ItemDto>) session.getAttribute("cart");
			int index = isExits(id, cart);
			if(index == -1) cart.add(new ItemDto(productService.getById(id), 1));
			else {
				int quantity = cart.get(index).getQuantity()+1;
				cart.get(index).setQuantity(quantity);
			}
			session.setAttribute("cart", cart);
		}
		return "redirect:../../cart";
	}
	
	@GetMapping("remove/{id}")
	public String remove(@PathVariable("id") int id, ModelMap model, HttpSession session) {
		
		try {
			if (session.getAttribute("userName") != null) {
				String userName = (String) session.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
			
		}
		
		List<ItemDto> cart = (List<ItemDto>) session.getAttribute("cart");
		int index = isExits(id, cart);
		cart.remove(index);
		session.setAttribute("cart", cart);
		return "redirect:../../cart";
	}
	
	@PostMapping("update")
	public String update(HttpServletRequest request, HttpSession session, ModelMap model) {
		
		try {
			if (session.getAttribute("userName") != null) {
				String userName = (String) session.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
			
		}
		
		try {
			String[] quantities = request.getParameterValues("quantity");
			List<ItemDto> cart = (List<ItemDto>) session.getAttribute("cart");
			for(int i =0;i<cart.size();i++) {
				cart.get(i).setQuantity(Integer.parseInt(quantities[i]));
			}
			session.setAttribute("cart", cart);
			return "redirect:../cart";
		} catch (Exception e) {
			return "redirect:../cart";
		}
		
 	}
	
	@GetMapping("/checkout")
	public String checkout(HttpSession session, ModelMap model) {
		try {
			if (session.getAttribute("userName") != null) {
				String userName = (String) session.getAttribute("userName");
				String firstOfName = userName.substring(0, 1);
				model.addAttribute("firstOfName", firstOfName);
			}
		} catch (Exception e) {
		}
		
		if(session.getAttribute("userName") == null) return "redirect:/login";
		else {
			
			InvoiceEntity invoice = new InvoiceEntity();
			invoice.setCustomerId(Integer.parseInt(session.getAttribute("id").toString()));
			invoice.setCustomer(customerService.find(session.getAttribute("userName").toString()));
			invoice.setInvoiceTime(new Date());
			invoice.setStatus("Chưa xác nhận");
			Collection<? extends GrantedAuthority> authorities;
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String isAnonymousUser = auth.getPrincipal().toString();
			if(isAnonymousUser.equals("anonymousUser")) {
//				return "redirect:/login/admin";
				invoice.setUserId(12);
			}
			else {
				invoice.setUserId(myUser.getPrincipal().getId());
			}
			

			 
			List<ItemDto> cart = (List<ItemDto>) session.getAttribute("cart");
			if(cart != null) {
				for(ItemDto item : cart) {
					DetailInvoiceEntity detailInvoice = new DetailInvoiceEntity();
					
					/*-------- BEGIN minus product quantity ----------*/
					ProductEntity productMua = item.getSanpham();
					int soluongproductmua = item.getQuantity();
					ProductEntity productTrongKho = productService.getById(item.getSanpham().getId());
					if((productTrongKho.getQuantity() - soluongproductmua) < 0 ){
						String message = "";
						message = "overQuantity";
						return "redirect:/cart?message="+message;
					}
					invoiceService.add(invoice);
					if(productTrongKho.getQuantity() - soluongproductmua == 0){
						productTrongKho.setStatus("Tạm thời hết hàng");
					}
					productTrongKho.setQuantity(productTrongKho.getQuantity() - soluongproductmua);
					productService.update(productTrongKho);
					
					/*-------- END minus product quantity ----------*/
					
					List<InvoiceEntity> listinvoiceDon = invoiceService.findAll();
					int idInvoice = 0;
					for (InvoiceEntity hoaDon : listinvoiceDon) {
						idInvoice=hoaDon.getId();
					}
					InvoiceEntity invoicetg = invoiceService.getById(idInvoice);
					
					detailInvoice.setInvoiceId(invoicetg.getId());
					detailInvoice.setProductId(item.getSanpham().getId());
					detailInvoice.setQuantity(item.getQuantity());
					int tongTienProduct = (item.getSanpham().getPrice() * item.getQuantity());
					detailInvoice.setSumMoney(tongTienProduct);
					
					detailInvoiceService.add(detailInvoice);
				}
				session.removeAttribute("cart");
				return "web/shop/thanks";
			}
			return "redirect:/cart";
		}
	}
	
	
	/*__________ PRIVATE METHOD ___________*/
	
	private long total(HttpSession session){
		List<ItemDto> cart = (List<ItemDto>) session.getAttribute("cart");
		long s = 0;
		if(cart != null) {
			for(ItemDto item : cart) {
				s += item.getQuantity() * item.getSanpham().getPrice();
			}
		}
		return s;
	}
	
	public int isExits(int id, List<ItemDto> cart) {
		for(int i =0;i<cart.size();i++) {
			 if(cart.get(i).getSanpham().getId() == id) return i;
		}
		return -1;
	}
	
}
