//package com.example.nhanct.controller;
//
//import com.example.nhanct.entity.DetailInvoiceEntity;
//import com.example.nhanct.service.InvoiceService;
//import com.example.nhanct.utils.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("admin/invoice/detailInvoice")
//public class DetailBillController extends FunctionCommon {
//
//	@Autowired
//	private InvoiceService invoiceService;
//
//	@Autowired
//	SecurityUtils myUser;
//
//	@GetMapping("")
//	public String index(ModelMap model, @RequestParam("id") int id) {
//		if(hasRoleAuthor() == false) {
//			return "deny/deny";
//		}
//		menuListRole(model);
//
//		List<DetailInvoiceEntity> listdetailInvoice = invoiceService.getById(id).getDetailInvoice();
//		int dongia = 0;
//		for (DetailInvoiceEntity item : listdetailInvoice) {
//			dongia += item.getSumMoney();
//		}
//		model.addAttribute("listdetailInvoice", listdetailInvoice);
//		model.addAttribute("dongia", dongia);
//		model.addAttribute("id", id);
//		return "invoice/donhangdetail";
//	}
//
//	/*---------------- begin AUTHOR ------------*/
//	private boolean hasRoleAuthor () {
//		Object[] tg = SecurityUtils.getPrincipal().getAuthorities().toArray();
//		for (Object object : tg) {
//			if (object.toString().equals("ROLE_HOADONCHITIET")) {
//				return true;
//			}
//		}
//		return false;
//	}
//	/*---------------- end AUTHOR ------------*/
//
//}
