package org.joolzminer.examples.sdata.jpa.core;

import org.springframework.data.repository.Repository;

public interface CustomerRepository extends Repository<Customer, Long> {
	Customer findOne(Long id);
	Customer save(Customer customer);
	Customer findByEmailAddress(EmailAddress emailAddress);
}
