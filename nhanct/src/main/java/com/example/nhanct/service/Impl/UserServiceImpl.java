package com.example.nhanct.service.Impl;

import com.example.nhanct.dto.UserDto;
import com.example.nhanct.entity.UserEntity;
import com.example.nhanct.repository.UserRepository;
import com.example.nhanct.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<UserEntity> findAll(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 4);
		return userRepository.findAll(pageable);
	}

	@Override
	public UserEntity getById(int id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void add(UserEntity user) {
		// kiem tra email ton tai chua
		UserEntity entity = userRepository.findByEmail(user.getEmail());
		if(entity == null) {
			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			user.setPassword(hashed);
		}
		userRepository.save(user);
	}

	@Override
	public void update(UserEntity user) {
		UserEntity entity = userRepository.findById(user.getId()).get();
		if(entity != null) {
			entity.setUserName(user.getUserName());		
			entity.setEmail(user.getEmail());
			entity.setPhone(user.getPhone());
			entity.setAddress(user.getAddress());
			entity.setTaiKhoanUser(user.getTaiKhoanUser());
			entity.setImage(user.getImage());
			entity.setSex(user.getSex());
			entity.setDob(user.getDob());
			userRepository.save(entity);
		}
		
	}

	@Override
	public boolean delete(int id) {
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public List<UserDto> getAllDto() {
//		return userRepository.findAll();
		//TODO : temp
		return null;
	}

	@Override
	public void changePassword(UserEntity user) {
		UserEntity entity = userRepository.findByEmail(user.getEmail());
		if(entity != null) {
			// ma hoa mat khau0
			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			// Update lại mật khẩu
			entity.setPassword(hashed);
		}
		
	}

	@Override
	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserEntity> findAll(String keyword) {
		if(keyword != null) {
			return userRepository.findAll(keyword);
		}
		return userRepository.findAll();
	}

	@Override
	public Page<UserEntity> find(String name, Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<UserEntity> findAll(int pageNumber, String sortField, String sortDir) {
		return null;
	}

	@Override
	public void updatePassword(UserEntity user) {
		hashPassword(user);
		userRepository.updatePassword(user.getPassword(), user.getId());
	}

	//___________PRIVATE METHOD_______________//
	private void hashPassword(UserEntity user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
	}

	@Override
	public boolean isException(UserEntity user) {
		boolean ex = false;
		//Date input =  user.getDob();
		//LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate d = LocalDate.now();
		Date date = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
		// check time input
		if ( user.getDob()==null
			|| (user.getDob().after(date))) {
			ex = true;
			System.out.println("có lỗi có lỗi ");
		}
			
		return ex;
	}

	@Override
	public boolean isExceptionEmail(UserEntity user) {
		/*KIỂM TRA ĐỊNH DẠNG EMAIL*/
		String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	            + "gmail.com";
	    String email1 = user.getEmail();
	    Boolean b = email1.matches(EMAIL_REGEX);
	    if(b == true) return false;
	    else return true;
	}

	@Override
	public UserEntity findByTenTaiKhoanUser(String userName) {
		return userRepository.findByUserName(userName);
	}
}
