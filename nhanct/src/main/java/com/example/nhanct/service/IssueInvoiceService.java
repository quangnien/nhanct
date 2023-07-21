package com.example.nhanct.service;

import com.example.nhanct.entity.IssueInvoiceEntity;
import org.springframework.data.domain.Page;

import java.util.List;


public interface IssueInvoiceService {

	List<IssueInvoiceEntity> findAll();
	List<IssueInvoiceEntity> findAllOrderByDate();
	Page<IssueInvoiceEntity> findAll(int pageNumber);
	IssueInvoiceEntity getById(int id);
	void add(IssueInvoiceEntity product);
	void update(IssueInvoiceEntity product);
	boolean delete(int id);

	List<IssueInvoiceEntity> findAllByKeyword(String keyword);
}