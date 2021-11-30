package com.inventmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventmart.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySupplierId(long supplierId);
    
	@Query("SELECT COUNT(u) FROM Product u")
	Long getTotalProduct();    
        
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Product SET initialStock=:stock where id =:id")
	void setProductQuantity(@Param("stock") double stock, @Param("id") long id);        
}
