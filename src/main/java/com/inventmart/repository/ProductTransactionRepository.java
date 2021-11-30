package com.inventmart.repository;

import com.inventmart.model.ProductTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("productTransactionRepository")
public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long> {

}
