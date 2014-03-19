package org.joolzminer.examples.sdata.jpa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hamcrest.Matcher;
import org.joolzminer.examples.sdata.jpa.core.Customer;
import org.joolzminer.examples.sdata.jpa.core.CustomerRepository;
import org.joolzminer.examples.sdata.jpa.core.EmailAddress;
import org.joolzminer.examples.sdata.jpa.core.Product;
import org.joolzminer.examples.sdata.jpa.core.ProductRepository;
import org.joolzminer.examples.sdata.jpa.order.LineItem;
import org.joolzminer.examples.sdata.jpa.order.Order;
import org.joolzminer.examples.sdata.jpa.order.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.joolzminer.examples.sdata.jpa.matcher.CoreMatchers.*;
import static org.joolzminer.examples.sdata.jpa.matcher.OrderMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
public class OrderRepositoryIntegrationTests {
	
	@Inject private DataSource dataSource;
	
	@Inject private OrderRepository orderRepository;
	@Inject private CustomerRepository customerRepository;
	@Inject private ProductRepository productRepository;
	
	@Before
	public void populateDatabase() throws SQLException {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
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
	public void testCreateOrder() {
		Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@dmband.com"));
		Product iPad = productRepository.findOne(1L);
		
		Order order = new Order(dave, dave.getAddresses().iterator().next());
		order.add(new LineItem(iPad));
		
		order = orderRepository.save(order);
		assertThat(order.getId(), is(notNullValue()));		
	}
	
	@Test
	public void testReadOrder() {
		Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@dmband.com"));
		List<Order> orders = orderRepository.findByCustomer(dave);
		
		Matcher<Iterable<? super Order>> hasOrderForIPad = containsOrder(with(LineItem(with(Product(named("iPad"))))));
		
		assertThat(orders, hasSize(1));
		assertThat(orders, hasOrderForIPad);
	}
}
