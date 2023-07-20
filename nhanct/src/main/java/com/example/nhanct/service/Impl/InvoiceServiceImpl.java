package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.IssueInvoiceEntity;
import com.example.nhanct.enumdef.StatusOfInvoiceEnum;
import com.example.nhanct.repository.InvoiceRepository;
import com.example.nhanct.repository.IssueInvoiceRepository;
import com.example.nhanct.service.InvoiceService;
import com.example.nhanct.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	private InvoiceRepository invoiceRepository ;

	@Autowired
	private IssueInvoiceRepository issueInvoiceRepository ;

	@Autowired
	SecurityUtils myContext;

	@Override
	public Page<InvoiceEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return invoiceRepository.findAll(pageable);
	}

	@Override
	public InvoiceEntity getById(int id) {
		return invoiceRepository.findById(id).get();
	}

	@Override
	public void add(InvoiceEntity invoice) throws Exception {
		invoice.setIssueDate(new Date());
		invoice.setIssuerId(myContext.getPrincipal().getId());
		invoice.setReleaserId(myContext.getPrincipal().getId());
		invoice.setStatus(StatusOfInvoiceEnum.DU_THAO.getText());

		IssueInvoiceEntity issueInvoice = issueInvoiceRepository.findAllOrderByIdDesc().get(0);
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
			}

		}
	}

	@Override
	public List<InvoiceEntity> findAll() {
		return invoiceRepository.findAll();
	}

//	@Override
//	public List<InvoiceEntity> finByCategoryId(int id) {
//		return invoiceRepository.findByCategoryId(id);
//	}

}
