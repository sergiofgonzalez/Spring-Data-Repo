package org.joolzminer.examples.sdata.mongo.repository;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.joolzminer.examples.sdata.mongo.ApplicationConfig;
import org.joolzminer.examples.sdata.mongo.domain.core.Product;
import org.joolzminer.examples.sdata.mongo.domain.core.QProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mysema.query.types.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class QueryDslProductRepositoryIntegrationTest {
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
	

	private static QProduct product = QProduct.product;
	

	
	@Test
	public void testFindProductsByQueryDslPredicate() {
		Product iPad = productRepository.findOne(product.name.eq("iPad"));
		Predicate tablets = product.description.contains("tablet");
		
		Iterable<Product> result = productRepository.findAll(tablets);
		assertThat(result, is(Matchers.<Product> iterableWithSize(1)));
		assertThat(result, hasItem(iPad));
	}
	
}
