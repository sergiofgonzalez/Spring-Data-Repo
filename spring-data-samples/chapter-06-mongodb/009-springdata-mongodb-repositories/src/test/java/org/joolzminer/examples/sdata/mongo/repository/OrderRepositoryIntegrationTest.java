package org.joolzminer.examples.sdata.mongo.repository;

import java.util.List;

import javax.inject.Inject;
import org.hamcrest.Matcher;
import org.joolzminer.examples.sdata.mongo.ApplicationConfig;
import org.joolzminer.examples.sdata.mongo.domain.core.Customer;
import org.joolzminer.examples.sdata.mongo.domain.core.EmailAddress;
import org.joolzminer.examples.sdata.mongo.domain.core.Product;
import org.joolzminer.examples.sdata.mongo.domain.order.LineItem;
import org.joolzminer.examples.sdata.mongo.domain.order.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.Mongo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.joolzminer.examples.sdata.mongo.utils.SugarMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class OrderRepositoryIntegrationTest {
	
	@Inject
	private OrderRepository orderRepository;
	
	@Inject
	private CustomerRepository customerRepository;
	
	@Inject
	private ProductRepository productRepository;
	
	@Autowired
	private Mongo mongo;
	
	@Before
	public void setUp() {

		DB database = mongo.getDB("e-store");
		
		// Customers
		DBCollection customers = database.getCollection("customer");
		customers.drop();
		
		BasicDBObject address = new BasicDBObject();
		address.put("city", "New York");
		address.put("street", "Broadway");
		address.put("country", "United States");
		
		BasicDBList addresses = new BasicDBList();
		addresses.add(address);
		
		DBObject dave = new BasicDBObject("firstName", "Dave");
		dave.put("lastName", "Matthews");
		dave.put("email", "dave@dmband.com");
		dave.put("addresses", addresses);
		
		customers.insert(dave);
		
		// Products		
		DBCollection products = database.getCollection("product");
		products.drop();
		
		DBObject iPad = new BasicDBObject("name", "iPad");
		iPad.put("description", "Apple tablet device");
		iPad.put("price", 499.0);
		iPad.put("attributes", new BasicDBObject("connector", "plug"));
		
		DBObject macbook = new BasicDBObject("name", "Macbook Pro");
		macbook.put("description", "Apple notebook");
		macbook.put("price", 1299.0);

		DBObject dock = new BasicDBObject("name", "Dock");
		dock.put("description", "Dock for iPhone/iPad");
		dock.put("price", 49.0);
		dock.put("attributes", new BasicDBObject("connector", "plug"));
		
		products.insert(iPad, macbook, dock);
		
		// Orders		
		DBCollection orders = database.getCollection("order");
		orders.drop();
		
		// Line items
		DBObject iPadLineItem = new BasicDBObject("product", iPad);
		iPadLineItem.put("amount", 2);
		
		DBObject macbookLineItem = new BasicDBObject("product", macbook);
		macbookLineItem.put("amount", 1);
		
		BasicDBList lineItems = new BasicDBList();
		lineItems.add(iPadLineItem);
		lineItems.add(macbookLineItem);
		
		DBObject order = new BasicDBObject("customer", new DBRef(database, "customer", dave.get("_id")));
		order.put("lineItems", lineItems);
		order.put("shippingAddress", address);
		
		orders.insert(order);
	}
	
	@Test
	public void testCreateOrder() {
		Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@dmband.com"));
		Product iPad = productRepository.findAll().iterator().next();
		
		Order order = new Order(dave, dave.getAddresses().iterator().next());
		order.add(new LineItem(iPad));
		
		order = orderRepository.save(order);
		assertThat(order.getId(), is(notNullValue()));
	}
	
	
	@Test
	public void testReadOrder() {
		Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@dmband.com"));
		List<Order> orders = orderRepository.findByCustomer(dave);
		Matcher<Iterable<? super Order>> hasOrderForiPad = containsOrder(with(LineItem(with(Product(named("iPad"))))));
		
		assertThat(orders, hasSize(1));
		assertThat(orders, hasOrderForiPad);
	}
	
}
