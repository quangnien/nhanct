package com.example.nhanct.repository;

import com.example.nhanct.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>{
    CustomerEntity findByMst(String mst);
}
