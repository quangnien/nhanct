package com.example.nhanct.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nhanct.entity.ConfirmationTokenEntity;
import com.example.nhanct.repository.ConfirmationTokenRepository;
import com.example.nhanct.service.Tokenservice;


@Service
public class TokenServiceimpl implements Tokenservice{
	@Autowired
	private ConfirmationTokenRepository confirmtoken;

	@Override
	public ConfirmationTokenEntity findByConfirmationTokenAndFlag(String confirmationToken, int flagStatus) {
		return confirmtoken.findByConfirmationTokenAndFlag(confirmationToken, flagStatus);
	}

	@Override
	public ConfirmationTokenEntity  save(ConfirmationTokenEntity entity) {
		return confirmtoken.save(entity);
	}

	@Override
	public  List<ConfirmationTokenEntity>  saveAll(List<ConfirmationTokenEntity> entities) {
		return (List<ConfirmationTokenEntity>) confirmtoken.saveAll(entities);
	}

	@Override
	public Optional<ConfirmationTokenEntity> findById(String id) {
		return confirmtoken.findById(id);
	}

	@Override
	public boolean existsById(String id) {
		return confirmtoken.existsById(id);
	}

	@Override
	public List<ConfirmationTokenEntity> findAll() {
		return (List<ConfirmationTokenEntity>) confirmtoken.findAll();
	}

	@Override
	public List<ConfirmationTokenEntity> findAllById(List<String> ids) {
		return (List<ConfirmationTokenEntity>) confirmtoken.findAllById(ids);
	}

	@Override
	public long count() {
		return confirmtoken.count();
	}

	@Override
	public void deleteById(String id) {
		confirmtoken.deleteById(id);
	}

	@Override
	public void delete(ConfirmationTokenEntity entity) {
		confirmtoken.delete(entity);
	}

	@Override
	public void deleteAll(List<ConfirmationTokenEntity> entities) {
		confirmtoken.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		confirmtoken.deleteAll();
	}

	@Override
	public List<ConfirmationTokenEntity> getAllListFlag0() {
		return confirmtoken.getAllListFlag0();
	}
	
}
