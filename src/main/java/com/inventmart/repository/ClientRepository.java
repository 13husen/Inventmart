package com.inventmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventmart.model.Client;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query("SELECT COUNT(c) FROM Client c")
	Long getTotalClients();
}
