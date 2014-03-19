package org.joolzminer.examples.sdata.mongo.repository;

import javax.inject.Inject;

import org.joolzminer.examples.sdata.mongo.ApplicationConfig;
import org.joolzminer.examples.sdata.mongo.domain.core.Address;
import org.joolzminer.examples.sdata.mongo.domain.core.Customer;
import org.joolzminer.examples.sdata.mongo.domain.core.EmailAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class CustomerRepositoryIntegrationTest {
	@Inject
	private CustomerRepository customerRepository;
	
	@Autowired
	Mongo mongo;
	
	@Before
	public void setUp() {

		DB database = mongo.getDB("e-store");
		
		// Customers		
		DBCollection customers = database.getCollection("customer");
		customers.remove(new BasicDBObject());
	}
	
	@Test
	public void testSaveCustomer() {
		EmailAddress emailAddress = new EmailAddress("alicia@keys.com");
		Customer customer = new Customer("Alicia", "Keys");
		customer.setEmailAddress(emailAddress);
		customer.add(new Address("27 Broadway", "New York", "United States"));
		
		Customer result = customerRepository.save(customer);
		assertThat(result.getId(), is(notNullValue()));
	}
	
	@Test
	public void testReadCustomerByEmail() {
		EmailAddress emailAddress = new EmailAddress("alicia@keys.com");
		Customer alicia = new Customer("Alicia", "Keys");
		alicia.setEmailAddress(emailAddress);
		customerRepository.save(alicia);
		
		Customer result = customerRepository.findByEmailAddress(emailAddress);
		assertThat(result, is(alicia));		
	}
	
	@Test(expected = DuplicateKeyException.class)
	public void testDuplicateEmail() {
		Customer sergio1 = new Customer("Sergio", "F. Gonzalez");
		sergio1.setEmailAddress(new EmailAddress("sergio.f.gonzalez@gmail.com"));
		customerRepository.save(sergio1);
		
		Customer sergio2 = new Customer("Sergio Francisco", "Gonzalez");
		sergio2.setEmailAddress(new EmailAddress("sergio.f.gonzalez@gmail.com"));
		customerRepository.save(sergio2);
	}
}
