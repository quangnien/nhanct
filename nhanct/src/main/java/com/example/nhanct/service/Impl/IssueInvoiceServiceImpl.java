package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.IssueInvoiceEntity;
import com.example.nhanct.repository.IssueInvoiceRepository;
import com.example.nhanct.service.IssueInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueInvoiceServiceImpl implements IssueInvoiceService{

	@Autowired
	private IssueInvoiceRepository issueInvoiceRepository ;

//	@Autowired
//	private SaleRepository saleRepositorye ;

	@Override
	public Page<IssueInvoiceEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return issueInvoiceRepository.findAll(pageable);
	}

	@Override
	public IssueInvoiceEntity getById(int id) {
		return issueInvoiceRepository.findById(id).get();
	}

	@Override
	public void add(IssueInvoiceEntity issueInvoice) {
		issueInvoice.setCurrentInvoiceNumber(0);
		issueInvoiceRepository.save(issueInvoice);
	}

	@Override
	public void update(IssueInvoiceEntity issueInvoiceEntity) {
		Optional<IssueInvoiceEntity> entityOptional = issueInvoiceRepository.findById(issueInvoiceEntity.getId());
		if(entityOptional.isPresent()){
			IssueInvoiceEntity entity = entityOptional.get();
			entity.setCurrentInvoiceNumber(issueInvoiceEntity.getCurrentInvoiceNumber());
			entity.setDateOfRegistration(issueInvoiceEntity.getDateOfRegistration());
			entity.setFromNumber(issueInvoiceEntity.getFromNumber());
			entity.setToNumber(issueInvoiceEntity.getToNumber());
			entity.setInvoiceTypeId(issueInvoiceEntity.getInvoiceTypeId());
			entity.setMst(issueInvoiceEntity.getMst());
			entity.setQuantity(issueInvoiceEntity.getQuantity());
			entity.setSymbol(issueInvoiceEntity.getSymbol());
			entity.setToNumber(issueInvoiceEntity.getToNumber());

			issueInvoiceRepository.save(entity);
		}
	}

	@Override
	public boolean delete(int id) {
		issueInvoiceRepository.deleteById(id);
		return true;
	}

	@Override
	public List<IssueInvoiceEntity> findAllByKeyword(String keyword) {
		return issueInvoiceRepository.findAllByKeyword(keyword);
	}

	@Override
	public List<IssueInvoiceEntity> findAll() {
		return issueInvoiceRepository.findAll();
	}

	@Override
	public List<IssueInvoiceEntity> findAllOrderByDate() {
		return issueInvoiceRepository.findAllOrderByDate();
	}

//	@Override
//	public List<IssueInvoiceEntity> finByCategoryId(int id) {
//		return issueInvoiceRepository.findByCategoryId(id);
//	}

}
