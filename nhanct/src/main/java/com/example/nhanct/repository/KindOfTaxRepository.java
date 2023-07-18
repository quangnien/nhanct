package com.example.nhanct.repository;

import com.example.nhanct.entity.KindOfTaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KindOfTaxRepository extends JpaRepository<KindOfTaxEntity, Integer>{
}
