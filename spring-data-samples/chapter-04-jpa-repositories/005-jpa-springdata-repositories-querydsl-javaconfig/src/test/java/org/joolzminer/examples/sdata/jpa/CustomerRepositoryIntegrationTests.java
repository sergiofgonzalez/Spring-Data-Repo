package org.joolzminer.examples.sdata.jpa;

import javax.inject.Inject;

import org.joolzminer.examples.sdata.jpa.core.Customer;
import org.joolzminer.examples.sdata.jpa.core.CustomerRepository;
import org.joolzminer.examples.sdata.jpa.core.EmailAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class CustomerRepositoryIntegrationTests {

	@Inject private CustomerRepository customerRepository;
	
	@Test
	@Transactional(readOnly = false)
	@Rollback(false)
	public void testInsertNewCustomerCorrectly() {
		Customer dave = new Customer("Dave", "Matthews");
		dave.setEmailAddress(new EmailAddress("dave@dmband.com"));

		Customer result = customerRepository.save(dave);
		assertThat(result.getId(), is(notNullValue()));
	}
	
	@Test
	@Transactional(readOnly = false)
	@Rollback(false)
	public void testUpdatesCustomerCorrectly() {
		Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@dmband.com"));
		assertThat(dave, is(notNullValue()));
		
		dave.setLastName("Miller");
		dave = customerRepository.save(dave);
		
		Customer reference = customerRepository.findByEmailAddress(dave.getEmailAddress());
		assertThat(reference.getLastName(), is(dave.getLastName()));
	}
}
