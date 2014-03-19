package org.joolzminer.examples.sdata.mongo.repository;

import org.joolzminer.examples.sdata.mongo.domain.core.Customer;
import org.joolzminer.examples.sdata.mongo.domain.core.EmailAddress;
import org.springframework.data.repository.Repository;

public interface CustomerRepository extends Repository<Customer, Long>{
	Customer findOne(Long id);
	Customer save(Customer customer);
	Customer findByEmailAddress(EmailAddress emailAddress);
}
