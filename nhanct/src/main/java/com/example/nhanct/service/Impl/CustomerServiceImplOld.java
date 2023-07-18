package com.example.nhanct.service.Impl;//package com.example.nhanct.service.Impl;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import com.example.nhanct.entity.CustomerEntity;
//import com.example.nhanct.repository.CustomerRepository;
//import com.example.nhanct.service.CustomerService;
//
//@Service
//public class CustomerServiceImpl implements CustomerService{
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Override
//	public List<CustomerEntity> findAll() {
//		return customerRepository.findAll();
//	}
//
//	@Override
//	public CustomerEntity getById(int id) {
//		return customerRepository.findById(id).get();
//	}
//
//	@Override
//	public void add(CustomerEntity customer) {
//		customerRepository.save(customer);
//
//	}
//
//	@Override
//	public void update(CustomerEntity customer) {
//		CustomerEntity entity = customerRepository.findById(customer.getId()).get();
//		if(entity != null) {
//			entity.setCustomerName(customer.getCustomerName());
//			entity.setAddress(customer.getAddress());
//			entity.setEmail(customer.getEmail());
//			entity.setPassword(customer.getPassword());
//			entity.setPhone(customer.getPhone());
//			entity.setUserName(customer.getUserName());
//			customerRepository.save(entity);
//		}
//
//	}
//
//	@Override
//	public void delete(int id) {
//		customerRepository.deleteById(id);
//
//	}
//
//	@Override
//	public CustomerEntity login(String userName, String password) {
////		CustomerEntity customer = this.findByUserName(userName);
////		if(customer != null) {
////			if(BCrypt.checkpw(password, customer.getPasswordKH()));
////			return customer;
////		}
//		return null;
//	}
//
//	@Override
//	public Optional<CustomerEntity> findByUserName(String userName) {
//		return Optional.ofNullable(customerRepository.findByUserName(userName));
//	}
//
////	@Override
////	public Optional<CustomerEntity> findByKhachHangemail(String email) {
////		return customerRepository.findByEmail(email);
////	}
//
//	@Override
//	public Optional<CustomerEntity> findKhachHangByResetToken(String resetToken) {
//		return customerRepository.findByResetToken(resetToken);
//
//	}
//
//	@Override
//	public CustomerEntity findByUserNameAndPasswordKH(String userName, String password) {
//		return customerRepository.findByUserNameAndPassword(userName, password);
//	}
//
//	@Override
//	public Optional<CustomerEntity> findByEmail(String email) {
//		return Optional.ofNullable(customerRepository.findByEmail(email));
//	}
//
//	@Override
//	public Optional<CustomerEntity> findByKhachHangemail(String email) {
//		return null;
//	}
//
//	@Override
//	public CustomerEntity findbyemail(String email) {
//		return customerRepository.findByEmail(email);
//	}
//
//	@Override
//	public CustomerEntity find(String userName) {
//		return customerRepository.findByUserName(userName);
//	}
//
//	@Override
//	public void updatePassword(CustomerEntity customer) {
//		customerRepository.updatePassword(customer.getPassword(), customer.getId());
//	}
//
//	@Override
//	public Page<CustomerEntity> findAll(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber-1, 5);
//		return customerRepository.findAll(pageable);
//	}
//
//	@Override
//	public List<CustomerEntity> findAll(String keyword) {
//		return customerRepository.findAll(keyword);
//	}
//
//	@Override
//	public boolean isExceptionEmail(CustomerEntity customer) {
//		/*KIỂM TRA ĐỊNH DẠNG EMAIL*/
//		String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//	            + "gmail.com";
//	    String email1 = customer.getEmail();
//	    Boolean b = email1.matches(EMAIL_REGEX);
//	    if(b == true) return false;
//	    else return true;
//	}
//
//	@Override
//	public CustomerEntity findByUserNameKhiDangKyHoacSua(String userName) {
//		return customerRepository.findByUserNameKhiDangKyHoacSua(userName);
//	}
//
//}
