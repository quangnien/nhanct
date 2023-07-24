package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.*;
import com.example.nhanct.enumdef.StatusOfInvoiceEnum;
import com.example.nhanct.repository.CustomerRepository;
import com.example.nhanct.repository.InvoiceRepository;
import com.example.nhanct.repository.IssueInvoiceRepository;
import com.example.nhanct.repository.WarehouseRepository;
import com.example.nhanct.service.CustomerService;
import com.example.nhanct.service.InvoiceService;
import com.example.nhanct.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	private InvoiceRepository invoiceRepository ;
	@Autowired
	private IssueInvoiceRepository issueInvoiceRepository ;
	@Autowired
	private CustomerRepository customerRepository ;
	@Autowired
	private WarehouseRepository warehouseRepository ;
	@Autowired
	SecurityUtils myContext;
	private Configuration configuration;

	@Override
	public Page<InvoiceEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		return invoiceRepository.findAll(pageable);
	}

	@Override
	public InvoiceEntity getById(int id) {
		InvoiceEntity invoiceEntity = invoiceRepository.findById(id).get();

		Optional<CustomerEntity> customer = customerRepository.findById(invoiceEntity.getCustomerId());
		Optional<WarehouseEntity> warehouseInput = warehouseRepository.findById(invoiceEntity.getInputWarehouseId());
		Optional<WarehouseEntity> warehouseOutput = warehouseRepository.findById(invoiceEntity.getInputWarehouseId());

		CustomerEntity customerEntity = new CustomerEntity();
		WarehouseEntity warehouseInputEntity = new WarehouseEntity();
		WarehouseEntity warehouseOutputEntity = new WarehouseEntity();
		if(invoiceEntity.getFlagInvoiceType().equals("VAT")){
			customerEntity = customer.get();
			invoiceEntity.setCustomerName(customerEntity.getCustomerName());
			invoiceEntity.setPhone(customerEntity.getPhone());
			invoiceEntity.setAddress(customerEntity.getAddress());
			invoiceEntity.setMst(customerEntity.getMst());
			invoiceEntity.setInvoiceType("VAT");
		}
		else if(invoiceEntity.getFlagInvoiceType().equals("WC")){
			warehouseInputEntity = warehouseInput.get();
			invoiceEntity.setInputWarehouseId(warehouseInputEntity.getId());

			warehouseOutputEntity = warehouseInput.get();
			invoiceEntity.setOutputWarehouseId(warehouseOutputEntity.getId());

			invoiceEntity.setInvoiceType("WC");

		}

		return invoiceEntity;
	}

	@Override
	public void add(InvoiceEntity invoice) throws Exception {
		invoice.setIssueDate(new Date());
		invoice.setIssuerId(myContext.getPrincipal().getId());
		invoice.setReleaserId(myContext.getPrincipal().getId());
		invoice.setStatus(StatusOfInvoiceEnum.DU_THAO.getText());

		IssueInvoiceEntity issueInvoice = new IssueInvoiceEntity();
		if(invoice.getInvoiceType().equals("VAT")){
			issueInvoice = issueInvoiceRepository.findAllByInvoiceTypeOrderByIdDesc("VAT").get(0);
		}
		else if(invoice.getInvoiceType().equals("WC")){
			issueInvoice = issueInvoiceRepository.findAllByInvoiceTypeOrderByIdDesc("WC").get(0);
		};

		invoice.setIssueInvoiceId(issueInvoice.getId());

		Optional<IssueInvoiceEntity> issueInvoiceEntityOptional = issueInvoiceRepository.findById(invoice.getIssueInvoiceId());
		if(issueInvoiceEntityOptional.isPresent()){
			IssueInvoiceEntity issueInvoiceEntity = issueInvoiceEntityOptional.get();
			if(issueInvoiceEntity.getCurrentInvoiceNumber() + 1 > issueInvoiceEntity.getQuantity()){
				throw new Exception("Quantity of Invoice > quantity was registered with Issue-Invoice.");
			}
			else {
				issueInvoiceEntity.setCurrentInvoiceNumber(issueInvoiceEntity.getCurrentInvoiceNumber() + 1);
				issueInvoiceRepository.save(issueInvoiceEntity);
			}
		}

		/* IF INVOICE_TYPE = VAT */
		if(invoice.getInvoiceType().equals("VAT")){
			CustomerEntity customerEntityFromDB = customerRepository.findByMst(invoice.getMst());
			if(null != customerEntityFromDB){
				customerEntityFromDB.setCustomerName(invoice.getCustomerName());
				customerEntityFromDB.setPhone(invoice.getPhone());
				customerEntityFromDB.setAddress(invoice.getAddress());
				customerRepository.save(customerEntityFromDB);

				invoice.setCustomerId(customerEntityFromDB.getId());
			}
			else {
				CustomerEntity customerEntity = new CustomerEntity();
				customerEntity.setCustomerName(invoice.getCustomerName());
				customerEntity.setMst(invoice.getMst());
				customerEntity.setPhone(invoice.getPhone());
				customerEntity.setAddress(invoice.getAddress());
				customerRepository.save(customerEntity);

				invoice.setCustomerId(customerEntity.getId());
			}
		}

		if(invoice.getInvoiceType().equals("VAT")){
			invoice.setFlagInvoiceType("VAT");
		}
		else if(invoice.getInvoiceType().equals("WC")){
			invoice.setFlagInvoiceType("WC");
		}

		invoiceRepository.save(invoice);
	}

	@Override
	public void update(InvoiceEntity invoiceEntity) {
		Optional<InvoiceEntity> entityOptional = invoiceRepository.findById(invoiceEntity.getId());
		if(entityOptional.isPresent()){
			InvoiceEntity entity = entityOptional.get();
			entity.setBusinessId(invoiceEntity.getBusinessId());
			entity.setCustomerId(invoiceEntity.getCustomerId());
			entity.setInputWarehouseId(invoiceEntity.getInputWarehouseId());
			entity.setOutputWarehouseId(invoiceEntity.getOutputWarehouseId());

			/* IF INVOICE_TYPE = VAT */
			if(invoiceEntity.getInvoiceType().equals("VAT")){
				CustomerEntity customerEntityFromDB = customerRepository.findByMst(invoiceEntity.getMst());
				if(null != customerEntityFromDB){
//					customerEntityFromDB.setCustomerName(invoiceEntity.getCustomerName());
//					customerEntityFromDB.setPhone(invoiceEntity.getPhone());
//					customerEntityFromDB.setAddress(invoiceEntity.getAddress());
//					customerRepository.save(customerEntityFromDB);

					entity.setCustomerId(customerEntityFromDB.getId());
				}
				else {
					CustomerEntity customerEntity = new CustomerEntity();
					customerEntity.setCustomerName(invoiceEntity.getCustomerName());
					customerEntity.setMst(invoiceEntity.getMst());
					customerEntity.setPhone(invoiceEntity.getPhone());
					customerEntity.setAddress(invoiceEntity.getAddress());
					customerRepository.save(customerEntity);

					entity.setCustomerId(customerEntity.getId());
				}
			}
			else if(invoiceEntity.getInvoiceType().equals("WC")){
				entity.setInputWarehouseId(invoiceEntity.getInputWarehouseId());
				entity.setOutputWarehouseId(invoiceEntity.getOutputWarehouseId());

				/* MUST BE SET, BECAUSE -> ERROR (RELASIONSHIP OF JPA) */
				CustomerEntity customerEntityFromDB = customerRepository.findByMst(invoiceEntity.getMst());
				entity.setCustomerId(customerEntityFromDB.getId());
			}


			if(invoiceEntity.getInvoiceType().equals("VAT")){
				entity.setFlagInvoiceType("VAT");
			}
			else if(invoiceEntity.getInvoiceType().equals("WC")){
				entity.setFlagInvoiceType("WC");
			}

			invoiceRepository.save(entity);
		}
	}

	@Override
	public boolean delete(int id) {
		invoiceRepository.deleteById(id);
		return true;
	}

	@Override
	public List<InvoiceEntity> findAllByKeyword(String keyword) {
		return invoiceRepository.findAllByKeyword(keyword);
	}

	@Override
	public void changeStatusInvoice(int id, String status, String reason) throws Exception {
		if(null != status){
			Optional<InvoiceEntity> invoiceEntityOptional = invoiceRepository.findById(id);
			if(invoiceEntityOptional.isPresent()){
				InvoiceEntity invoice = invoiceEntityOptional.get();
				if(status.equals("approve")){
					invoice.setReleaseDate(new Date());
					invoice.setReleaserId(myContext.getPrincipal().getId());
					invoice.setStatus(StatusOfInvoiceEnum.DA_DUYET.getText());

					Optional<IssueInvoiceEntity> issueInvoiceEntityOptional = issueInvoiceRepository.findById(invoice.getIssueInvoiceId());
					if(issueInvoiceEntityOptional.isPresent()){
						IssueInvoiceEntity issueInvoiceEntity = issueInvoiceEntityOptional.get();
						int curentInvoiceNumber = issueInvoiceEntity.getCurrentInvoiceNumber();
						if(curentInvoiceNumber + 1 > issueInvoiceEntity.getQuantity()){
							throw new Exception("Quantity of Invoice > quantity was registered with Issue-Invoice.");
						}
						else {
							issueInvoiceEntity.setCurrentInvoiceNumber(curentInvoiceNumber + 1);
							invoice.setNumberOfInvoice(curentInvoiceNumber + 1);
							issueInvoiceRepository.save(issueInvoiceEntity);
						}
					}
					invoiceRepository.save(invoice);
				}
				else if(status.equals("cancel")){
					invoice.setReasonForCancellation(reason);
					invoice.setStatus(StatusOfInvoiceEnum.DA_HUY.getText());
					invoiceRepository.save(invoice);
				}
				else if(status.equals("request")){
					invoice.setStatus(StatusOfInvoiceEnum.CHO_DUYET.getText());
					invoiceRepository.save(invoice);
				}
			}

		}
	}

	@Override
	public void pdfForCustomer(int id) throws Exception {

	}

	@Override
	public List<InvoiceEntity> findAll() {
		return invoiceRepository.findAll();
	}

	public String getPdfContent(Map<String, String> inParams, String template)
			throws IOException, TemplateException {
		Template t = configuration.getTemplate(template);
		return FreeMarkerTemplateUtils.processTemplateIntoString(t, inParams);
	}

//	@Override
//	public List<InvoiceEntity> finByCategoryId(int id) {
//		return invoiceRepository.findByCategoryId(id);
//	}

}
