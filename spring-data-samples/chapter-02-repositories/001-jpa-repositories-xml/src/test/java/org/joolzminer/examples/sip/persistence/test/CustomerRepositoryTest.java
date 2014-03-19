package org.joolzminer.examples.sip.persistence.test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import junit.framework.Assert;

import org.joolzminer.examples.sdata.domain.Address;
import org.joolzminer.examples.sdata.domain.Customer;
import org.joolzminer.examples.sdata.domain.EmailAddress;
import org.joolzminer.examples.sdata.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/application-context.xml"})
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerRepositoryTest {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepositoryTest.class);
	
	@Inject private CustomerRepository customerRepository;
	
	@Test
	@Transactional(readOnly = false)
	public void testSaveCustomer() {
		Customer customer = new Customer("first-name", "last-name");
		customer.setEmailAddress(new EmailAddress("firstname@example.com"));
		customer.add(new Address("street", "city", "country"));
		Customer savedCustomer = customerRepository.save(customer);
		
		Assert.assertEquals(savedCustomer, customerRepository.findById(savedCustomer.getId()));
	}
	
	@Test
	@Transactional(readOnly = false)
	public void testFindByEmailAddress() {
		Customer customer = new Customer("first-name", "last-name");
		customer.setEmailAddress(new EmailAddress("firstname@example.com"));
		customer.add(new Address("street", "city", "country"));
		customer = customerRepository.save(customer);
		
		Customer retrievedCustomer = customerRepository.findByEmailAddress(new EmailAddress("firstname@example.com"));
		
		Assert.assertEquals(customer, retrievedCustomer);
	}
	
	
	@Test
	@Transactional(readOnly = false)
	public void testFindByEmailAddressCustomQuery() {
		Customer customer = new Customer("first-name", "last-name");
		customer.setEmailAddress(new EmailAddress("firstname@example.com"));
		customer.add(new Address("street", "city", "country"));
		customer = customerRepository.save(customer);
		
		Customer retrievedCustomer = customerRepository
										.findByEmailAddressCustomQuery(new EmailAddress("firstname@example.com"));
		
		Assert.assertEquals(customer, retrievedCustomer);
	}
	
	@Test
	@Transactional(readOnly = false)
	public void testFindAll() {
		List<Customer> savedCustomers = new ArrayList<Customer>();
		for (int i = 0; i < 10; i++) {
			Customer customer = new Customer("first-name" + i, "last-name" + i);
			customer.setEmailAddress(new EmailAddress("firstname" + i + "@example.com"));
			customer.add(new Address("street" + i, "city" + i, "country" + i));
			savedCustomers.add(customerRepository.save(customer));
		}


		List<Customer> retrievedCustomers = customerRepository.findAll();
		Assert.assertEquals(10, retrievedCustomers.size());
		for (Customer savedCustomer : savedCustomers) {
			Assert.assertTrue(retrievedCustomers.contains(savedCustomer));
		}		
	}
	
	@Test
	@Transactional(readOnly = false)
	public void testFindByEmailAndLastName() {
		List<Customer> savedCustomers = new ArrayList<Customer>();
		for (int i = 0; i < 10; i++) {
			Customer customer = new Customer("first-name" + i, "last-name" + i);
			customer.setEmailAddress(new EmailAddress("firstname" + i + "@example.com"));
			customer.add(new Address("street" + i, "city" + i, "country" + i));
			savedCustomers.add(customerRepository.save(customer));
		}


		List<Customer> retrievedCustomers = customerRepository
												.findByEmailAddressAndLastName(new EmailAddress("firstname5@example.com"), "last-name5");
		Assert.assertEquals(1, retrievedCustomers.size());
		Assert.assertEquals(savedCustomers.get(5) , retrievedCustomers.get(0));
	}
	
	@Test
	@Transactional(readOnly = false)
	public void testFindByAddressesCity() {
		List<Customer> savedCustomers = new ArrayList<Customer>();
		for (int i = 0; i < 10; i++) {
			Customer customer = new Customer("first-name" + i, "last-name" + i);
			customer.setEmailAddress(new EmailAddress("firstname" + i + "@example.com"));
			customer.add(new Address("street" + i, "city" + (i % 2), "country" + i));
			savedCustomers.add(customerRepository.save(customer));
		}


		List<Customer> retrievedCustomers = customerRepository
												.findByAddressesCity("city0");
		Assert.assertEquals(5, retrievedCustomers.size());
		for (Customer customer : retrievedCustomers) {
			for (Address address : customer.getAddresses()) {
				Assert.assertEquals("city0", address.getCity());
			}
		}
		
	}
	
	
	@Test
	@Transactional(readOnly = false)
	public void testFindByLastNamePagination() {
		List<Customer> savedCustomers = new ArrayList<Customer>();
		for (int i = 0; i < 100; i++) {
			Customer customer = new Customer("first-name" + i, "last-name" + (i % 2));
			customer.setEmailAddress(new EmailAddress("firstname" + i + "@example.com"));
			customer.add(new Address("street" + i, "city" + (i % 2), "country" + i));
			savedCustomers.add(customerRepository.save(customer));
		}


		Pageable pageable = new PageRequest(2, 10, Direction.ASC, "lastName", "firstName");
		Page<Customer> retrievedCustomersPage = customerRepository
												.findByLastName("last-name0", pageable);

		Assert.assertEquals(50, retrievedCustomersPage.getTotalElements());
		Assert.assertEquals(10, retrievedCustomersPage.getNumberOfElements());
		Assert.assertEquals(5, retrievedCustomersPage.getTotalPages());
		for (Customer customer : retrievedCustomersPage.getContent()) {
			Assert.assertEquals("last-name0", customer.getLastName());
		}		
	}
	
	@Test
	@Transactional(readOnly = false)
	public void testFindAllSort() {
		List<Customer> savedCustomers = new ArrayList<Customer>();
		for (int i = 0; i < 100; i++) {
			Customer customer = new Customer("first-name" + i, "last-name" + i);
			customer.setEmailAddress(new EmailAddress("firstname" + i + "@example.com"));
			customer.add(new Address("street" + i, "city" + (i % 2), "country" + i));
			savedCustomers.add(customerRepository.save(customer));
		}


		Sort sort = new Sort(Direction.DESC, "lastName");
		List<Customer> retrievedCustomers = customerRepository
												.findAll(sort);

		Assert.assertEquals(100, retrievedCustomers.size());
		Assert.assertEquals(savedCustomers.get(99), retrievedCustomers.get(0));
		Assert.assertEquals(savedCustomers.get(0), retrievedCustomers.get(99));
	}	
}
