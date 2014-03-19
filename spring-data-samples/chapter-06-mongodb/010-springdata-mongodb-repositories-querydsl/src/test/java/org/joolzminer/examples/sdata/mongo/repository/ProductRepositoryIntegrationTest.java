package org.joolzminer.examples.sdata.mongo.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.joolzminer.examples.sdata.mongo.ApplicationConfig;
import org.joolzminer.examples.sdata.mongo.domain.core.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ProductRepositoryIntegrationTest {

	@Inject
	private ProductRepository productRepository;
	
	@Inject
	private Mongo mongo;
	
	@Before
	public void setUp() {

		DB database = mongo.getDB("e-store");
		
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
	}
	
	@Test
	public void createProduct() {
		Product product = new Product("Camera bag", new BigDecimal(49.99));
		product = productRepository.save(product);
		
		assertThat(product.getId(), is(notNullValue()));
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void testLookupProductsByDescription() {
		Pageable pageable = new PageRequest(0, 1, Direction.DESC, "name");
		Page<Product> page = productRepository.findByDescriptionContaining("Apple", pageable);
		
		assertThat(page.getContent(), hasSize(1));
		assertThat(page, Matchers.<Product> hasItems(hasProperty("name", is("iPad"))));
		assertThat(page.isFirstPage(), is(true));
		assertThat(page.isLastPage(), is(false));
		assertThat(page.hasNextPage(), is(true));		
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void testFindProductsByAttributes() {
		List<Product> products = productRepository.findByAttributes("attributes.connector", "plug");
		
		assertThat(products, hasSize(2));
		assertThat(products, Matchers.<Product> hasItems(hasProperty("name", is("Dock"))));
		assertThat(products, Matchers.<Product> hasItems(hasProperty("name", is("iPad"))));
	}
}
