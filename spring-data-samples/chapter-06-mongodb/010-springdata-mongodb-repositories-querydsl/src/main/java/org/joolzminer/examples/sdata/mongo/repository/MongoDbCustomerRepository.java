package org.joolzminer.examples.sdata.mongo.repository;


import javax.inject.Inject;

import org.joolzminer.examples.sdata.mongo.domain.core.Customer;
import org.joolzminer.examples.sdata.mongo.domain.core.EmailAddress;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

@Repository
public class MongoDbCustomerRepository implements CustomerRepository {

	private final MongoOperations operations;
	
	@Inject
	public MongoDbCustomerRepository(MongoOperations operations) {
		Assert.notNull(operations);
		this.operations = operations;
	}
	
	@Override
	public Customer findOne(Long id) {
		Query query = query(where("id").is(id));
		return operations.findOne(query, Customer.class);
	}

	@Override
	public Customer save(Customer customer) {
		operations.save(customer);
		return customer;
	}

	@Override
	public Customer findByEmailAddress(EmailAddress emailAddress) {
		Query query = query(where("emailAddress").is(emailAddress));
		return operations.findOne(query, Customer.class);
	}

}
