package com.example.nhanct.service.Impl;

import com.example.nhanct.entity.BrandEntity;
import com.example.nhanct.entity.CategoryEntity;
import com.example.nhanct.entity.ProductEntity;
import com.example.nhanct.repository.ProductRepository;
import com.example.nhanct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository ;
	
//	@Autowired
//	private SaleRepository saleRepositorye ;

	@Override
	public Page<ProductEntity> findAll(int pageNumber) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 5);
		return productRepository.findAll(pageable);
	}

	@Override
	public ProductEntity getById(int id) {
		return productRepository.findById(id).get();
	}

	@Override
	public void add(ProductEntity product) {
//		SaleEntity km = saleRepositorye.findById(product.getSaleId()).get();
//		if (LocalDate.now().isAfter(km.getStartDate())
//				&& LocalDate.now().isBefore(km.getEndDate())) {
//			product.setPriceActually(product.getPrice() - (km.getGiamGia()*product.getPrice())/100);
//		}
//		else product.setPriceActually(product.getPrice());
		product.setPriceActually(product.getPrice());

		product.setLastUpdate(LocalDateTime.now());
		productRepository.save(product);
	}

	@Override
	public void update(ProductEntity product) {
		ProductEntity entity = productRepository.findById(product.getId()).get();
		if(entity != null) {
//			SaleEntity km = saleRepositorye.findById(product.getSaleId()).get();
			entity.setProductName(product.getProductName());
			entity.setPrice(product.getPrice());
			entity.setImage(product.getImage());
			entity.setQuantity(product.getQuantity());
			entity.setCategoryId(product.getCategoryId());
			entity.setBrandId(product.getBrandId());
			entity.setStatus(product.getStatus());
			entity.setLastUpdate(LocalDateTime.now());
			entity.setDes(product.getDes());
			
//			if (LocalDate.now().isAfter(km.getStartDate())
//					&& LocalDate.now().isBefore(km.getEndDate())) {
//				entity.setPriceActually(entity.getPrice() - (km.getGiamGia()*product.getPrice())/100);
//			}
//			else entity.setPriceActually(entity.getPrice());
			entity.setPriceActually(entity.getPrice());

			productRepository.save(entity);
		}
	}

	@Override
	public boolean delete(int id) {
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ProductEntity> findAllByKeyword(String keyword) {
		return productRepository.findAllByKeyword(keyword);
	}

	@Override
	public List<ProductEntity> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<ProductEntity> finByCategoryId(int id) {
		return productRepository.findByCategoryId(id);
	}

	@Override
	public List<ProductEntity> findTop3byCategory(CategoryEntity category) {
		return productRepository.findTop3ByCategoryOrderByIdDesc(category);

	}

	@Override
	public List<ProductEntity> findTop3ByBrand(BrandEntity brand) {
		return productRepository.findTop3ByBrandOrderByIdDesc(brand);
	}

	@Override
	public List<ProductEntity> findTop4byCategory(CategoryEntity category) {
		return productRepository.findTop4ByCategoryOrderByIdDesc(category);
	}
	
	@Override
	public Page<ProductEntity> findTop4ByCategoryOrderBySoluongDesc(int idCategory, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber-1, 4);
		return (Page<ProductEntity>) productRepository.findTop4ByCategoryOrderByQuantityDesc(idCategory, pageable);
	}

	@Override
	public Page<ProductEntity> findTop4ByBrandOrderBySoluongDesc(int idBrand, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber-1, 4);
		return (Page<ProductEntity>) productRepository.findTop4ByBrandOrderByQuantityDesc(idBrand, pageable);
	}

//	@Override
//	public List<ProductEntity> findByListMaDinhDanh(List<String> maDinhDanhList) {
//		return productRepository.findByListMaDinhDanh(maDinhDanhList);
//	}


	@Override
	public Page<ProductEntity> getAllProductByCategory(int id, int pageNumber) {
		 Pageable pageable = PageRequest.of(pageNumber-1, 6);
		 return (Page<ProductEntity>) productRepository.findAllProductByCategory(id, pageable);
	}

}
