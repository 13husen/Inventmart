package com.inventmart.repository;

import com.inventmart.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("invoiceRepository")
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	@Query("SELECT COUNT(u) FROM Invoice u")
	Long getTotalInvoice();
}
