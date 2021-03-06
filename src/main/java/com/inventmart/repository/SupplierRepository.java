package com.inventmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventmart.model.Supplier;

@Repository("supplierRepository")
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	@Query("SELECT COUNT(s) FROM Supplier s")
	Long getTotalSuppliers();
}
