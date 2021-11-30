package com.inventmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventmart.model.PurchaseOrder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository("purchaseOrderRepository")
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
	PurchaseOrder findByCode(String code);
        
	@Query("SELECT COUNT(u) FROM PurchaseOrder u")
	Long getTotalPo();
        
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE PurchaseOrder SET status =:status where code=:code")
	void setPurchaseOrderStatus(@Param("status") String status, @Param("code") String code);        
        
}
