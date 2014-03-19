package org.joolzminer.examples.sdata.jpa.core;

public interface CustomerRepository {
	Customer findOne(Long id);
	Customer save(Customer customer);
	Customer findByEmailAddress(EmailAddress emailAddress);
}
