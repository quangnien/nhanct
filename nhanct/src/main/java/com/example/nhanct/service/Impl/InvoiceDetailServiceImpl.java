package com.example.nhanct.service.Impl;

import com.example.nhanct.dto.InvoiceDetailDto;
import com.example.nhanct.entity.InvoiceDetailEntity;
import com.example.nhanct.entity.InvoiceEntity;
import com.example.nhanct.entity.KindOfTaxEntity;
import com.example.nhanct.repository.InvoiceDetailRepository;
import com.example.nhanct.repository.InvoiceRepository;
import com.example.nhanct.repository.KindOfTaxRepository;
import com.example.nhanct.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService{

	@Autowired
	private InvoiceDetailRepository invoiceDetailRepository ;
	@Autowired
	private KindOfTaxRepository kindOfTaxRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Override
	public Page<InvoiceDetailEntity> findAll(int pageNumber, int invoiceId) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 10);
		Page<InvoiceDetailEntity> invoiceDetailEntityList = invoiceDetailRepository.findAllByInvoiceId(invoiceId, pageable);
		return invoiceDetailEntityList;
	}

	@Override
	public List<InvoiceDetailDto> findAllByInvoiceIdGroupByKindOfTaxId(int id) {
		return invoiceDetailRepository.findAllByInvoiceIdGroupByKindOfTaxId(id);
	}

	@Override
	public InvoiceDetailEntity getById(int id) {
		return invoiceDetailRepository.findById(id).get();
	}

	@Override
	public void add(InvoiceDetailEntity invoiceDetail) {
		updatePriceTax(invoiceDetail);
		invoiceDetailRepository.save(invoiceDetail);
	}

	@Override
	public void update(InvoiceDetailEntity invoiceDetailEntity) {
		Optional<InvoiceDetailEntity> entityOptional = invoiceDetailRepository.findById(invoiceDetailEntity.getId());
		if(entityOptional.isPresent()){
			InvoiceDetailEntity entity = entityOptional.get();

			entity.setItemName(invoiceDetailEntity.getItemName());
			entity.setQuantity(invoiceDetailEntity.getQuantity());
			entity.setDvt(invoiceDetailEntity.getDvt());
			entity.setPrice(invoiceDetailEntity.getPrice());
			updatePriceTax(entity);

			invoiceDetailRepository.save(entity);
		}
	}

	@Override
	public boolean deleteById(int id) {
		invoiceDetailRepository.deleteByIdCustom(id);
		return true;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		invoiceDetailRepository.deleteById(id);
		return true;
	}

	@Override
	public List<InvoiceDetailEntity> findAllByKeyword(String keyword) {
		return invoiceDetailRepository.findAllByKeyword(keyword);
	}

	@Override
	public List<InvoiceDetailEntity> findAllByInvoiceId(int invoiceId) {
		return invoiceDetailRepository.findAllByInvoiceId(invoiceId);
	}

	@Override
	public List<InvoiceDetailEntity> findAllByKindOfTaxId(int invoiceId) {
		return invoiceDetailRepository.findAllByKindOfTaxId(invoiceId);
	}

	@Override
	public List<InvoiceDetailEntity> findAll() {
		return invoiceDetailRepository.findAll();
	}

//	@Override
//	public List<InvoiceDetailEntity> finByCategoryId(int id) {
//		return invoiceDetailRepository.findByCategoryId(id);
//	}
	/*____________________________________________________*/
	private static boolean isUnpaged(Pageable pageable) {
		return pageable.isUnpaged();
	}


	private void updatePriceTax(InvoiceDetailEntity invoiceDetail){

		String typeOfInvoice = "";
		Optional<InvoiceEntity> invoiceEntity = invoiceRepository.findById(invoiceDetail.getInvoiceId());
		if(invoiceEntity != null){
			typeOfInvoice = invoiceEntity.get().getFlagInvoiceType();
		}

		if(typeOfInvoice.equals("VAT")){
			// tiền trước thuế = đơn giá * số lượng
			BigDecimal priceBeforeTax = BigDecimal.valueOf(invoiceDetail.getQuantity()).multiply(invoiceDetail.getPrice());

			Optional<KindOfTaxEntity> kindOfTaxEntityOptional = kindOfTaxRepository.findById(invoiceDetail.getKindOfTaxId());
			if(kindOfTaxEntityOptional.isPresent()){
				KindOfTaxEntity kindOfTax = kindOfTaxEntityOptional.get();

				// tiền thuế = tiền trước thuế * tỉ lệ thuế
				BigDecimal priceOfTax = BigDecimal.valueOf(kindOfTax.getRatio()).multiply(priceBeforeTax).divide(BigDecimal.valueOf(100));

				// tiền sau thuế = tiền trước thuế + tiền thuế
//			BigDecimal priceAfterTax = priceBeforeTax.multiply(priceOfTax);
				BigDecimal priceAfterTax = priceBeforeTax.add(priceOfTax);
				BigDecimal sumOfAfterTax = priceAfterTax;

				invoiceDetail.setPriceBeforeTax(priceBeforeTax);
				invoiceDetail.setPriceAfterTax(priceAfterTax);
				invoiceDetail.setPriceOfTax(priceOfTax);
				invoiceDetail.setSumMoneyAfterTax(sumOfAfterTax);
			}
		}
		else {

		}
	}
}
