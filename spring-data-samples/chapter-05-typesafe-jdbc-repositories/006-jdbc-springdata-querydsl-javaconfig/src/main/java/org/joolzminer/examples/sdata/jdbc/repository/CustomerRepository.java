package org.joolzminer.examples.sdata.jdbc.repository;

import java.util.List;

import org.joolzminer.examples.sdata.jdbc.domain.Customer;
import org.joolzminer.examples.sdata.jdbc.domain.EmailAddress;

public interface CustomerRepository {
	Customer findById(Long id);
	List<Customer> findAll();
	void save(Customer customer);
	void delete(Customer customer);
	Customer findByEmailAddress(EmailAddress emailAddress);

}
