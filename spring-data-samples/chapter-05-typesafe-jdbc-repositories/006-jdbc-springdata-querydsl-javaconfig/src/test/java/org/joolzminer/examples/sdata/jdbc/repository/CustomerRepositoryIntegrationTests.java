package org.joolzminer.examples.sdata.jdbc.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.joolzminer.examples.sdata.jdbc.ApplicationConfig;
import org.joolzminer.examples.sdata.jdbc.domain.Address;
import org.joolzminer.examples.sdata.jdbc.domain.Customer;
import org.joolzminer.examples.sdata.jdbc.domain.EmailAddress;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@DirtiesContext
public class CustomerRepositoryIntegrationTests {

	@Inject private DataSource dataSource;
	
	@Inject private CustomerRepository customerRepository;
	
	@Before
	public void populateDatabase() throws SQLException {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("schema.sql"));
		populator.addScript(new ClassPathResource("test-data.sql"));
		Connection connection = null;
		
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			populator.populate(connection);
			
		} finally {
			if (connection != null) {
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
		}
	}	
	
	@Test
	public void testFindById() {
		Customer savedCustomer = customerRepository.findById(1L);
		
		assertThat(savedCustomer, is(notNullValue()));
		assertThat(savedCustomer.getFirstName(), is("John"));
	}
	
	@Test
	public void testFindAll() {
		List<Customer> customers = customerRepository.findAll();
		
		assertThat(customers, is(notNullValue()));
		assertThat(customers, hasSize(3));
		assertThat(customers.get(0), notNullValue());
		assertThat(customers.get(1), notNullValue());
		assertThat(customers.get(2), notNullValue());		
	}
	
	@Test
	public void testFindByEmail() {
		Customer customer = customerRepository.findByEmailAddress(new EmailAddress("bob@doe.com"));
		
		assertThat(customer, is(notNullValue()));
		assertThat(customer.getFirstName(), is("Bob"));
	}
	
	@Test
	public void testSaveNewCustomer() {
		Customer customer = new Customer();
		customer.setFirstName("Sven");
		customer.setLastName("Svensson");
		customer.setEmailAddress(new EmailAddress("sven@svensson.com"));
		Address address = new Address("Storgaten 6", "Trosa", "Sweden");
		customer.addAddress(address);
		customerRepository.save(customer);
		
		Customer savedCustomer = customerRepository.findById(customer.getId());
		
		assertThat(savedCustomer, is(notNullValue()));
		assertThat(savedCustomer.getFirstName(), is("Sven"));
		assertThat(savedCustomer.getEmailAddress().toString(), is(notNullValue()));
	}
	
	@Test
	public void testSaveNewCustomerWithoutEmail() {
		Customer customer = new Customer();
		customer.setFirstName("Sven");
		customer.setLastName("Svensson");
		Address address = new Address("Storgaten 6", "Trosa", "Sweden");
		customer.addAddress(address);
		customerRepository.save(customer);
		
		Customer savedCustomer = customerRepository.findById(customer.getId());
		
		assertThat(savedCustomer, is(notNullValue()));
		assertThat(savedCustomer.getFirstName(), is("Sven"));
		assertThat(savedCustomer.getEmailAddress(), is(nullValue()));
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void testSaveNewCustomerWithDuplicateEmail() {
		Customer customer = new Customer();
		customer.setFirstName("Bob");
		customer.setLastName("Doe");
		customer.setEmailAddress(new EmailAddress("bob@doe.com"));
		customerRepository.save(customer);
	}
	
	@Test
	public void testDeleteCustomer() {
		Customer customer = customerRepository.findById(1L);
		customerRepository.delete(customer);
		
		Customer result = customerRepository.findById(1L);
		assertThat(result, is(nullValue()));
	}
}
