package com.example.nhanct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.nhanct.entity.ConfirmationTokenEntity;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, String>{
	
	ConfirmationTokenEntity findByConfirmationTokenAndFlag(String confirmationToken, int flagStatus);
	  
	@Query("SELECT s FROM ConfirmationTokenEntity s WHERE s.flag = 0")
	List<ConfirmationTokenEntity> getAllListFlag0();
}
