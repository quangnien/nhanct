//package com.example.nhanct.repository;
//
//import com.example.nhanct.entity.CustomerEntity;
//import javax.transaction.Transactional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
//
//@Repository
//public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>{
//
////	Optional<CustomerEntity> findByEmail(String email);
//	Optional<CustomerEntity> findByResetToken(String resetToken);
//
//	CustomerEntity findByEmail(String email);
//
////	Optional<CustomerEntity> findByUserName(String userName);
//
//	CustomerEntity findByUserNameAndPassword(String userName, String password);
//
//	CustomerEntity findByUserName(String userName);
//
//	@Transactional
//	@Modifying
//	@Query("UPDATE CustomerEntity u SET u.password = ?1 where u.id = ?2")
//	public void updatePassword(String password, Integer id);
//
//	//seaching
//	@Query("SELECT s FROM CustomerEntity s WHERE s.customerName LIKE %?1%")
//	public List<CustomerEntity> findAll(String keyword);
//
//	@Query("SELECT s FROM CustomerEntity s WHERE s.customerName = ?1")
//	public CustomerEntity findByUserNameKhiDangKyHoacSua(String TenTaiKhoan);
//
//}
