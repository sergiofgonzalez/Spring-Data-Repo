package org.joolzminer.examples.sdata.repository;

import java.util.List;

import org.joolzminer.examples.sdata.domain.Customer;
import org.joolzminer.examples.sdata.domain.EmailAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


public interface CustomerRepository extends Repository<Customer, Long> {	
	Customer save(Customer customer);	
	Customer findById(Long id);
	Customer findByEmailAddress(EmailAddress emailAddress);
	List<Customer> findAll(); 
	
	// Custom Query
	@Query("select c from Customer c where c.emailAddress = ?1")
	Customer findByEmailAddressCustomQuery(EmailAddress emailAddress);
	
	// Query Derivation
	List<Customer> findByEmailAddressAndLastName(EmailAddress emailAddress, String lastName);
	
	// Property  expression Traversal
	List<Customer> findByAddressesCity(String city);
	
	// Pagination options
	Page<Customer> findByLastName(String lastName, Pageable pageable);
	
	// Sorting options
	List<Customer> findAll(Sort sort);
}
