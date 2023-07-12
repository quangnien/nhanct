package com.example.nhanct.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nhanct.entity.ContactEntity;
import com.example.nhanct.repository.ContactRepository;
import com.example.nhanct.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public List<ContactEntity> findAll() {
		return contactRepository.findAll();
	}

	@Override
	public void delete(int id) {
		contactRepository.deleteById(id);
	}

	@Override
	public void add(ContactEntity lienhe) {
		contactRepository.save(lienhe);
	}

	@Override
	public ContactEntity getById(int id) {
		return contactRepository.findById(id).get();
	}

	@Override
	public boolean isExceptionEmail(ContactEntity lienhe) {
		/*KIỂM TRA ĐỊNH DẠNG EMAIL*/
		String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	            + "gmail.com";
	    String email1 = lienhe.getYourEmail();
	    Boolean b = email1.matches(EMAIL_REGEX);
	    if(b == true) return false;
	    else return true;
	}
}
