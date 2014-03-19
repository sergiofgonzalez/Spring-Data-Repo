package org.joolzminer.examples.sip.persistence.test;

import java.util.Arrays;
import java.util.List;

import org.joolzminer.examples.sdata.domain.Product;
import org.joolzminer.examples.sdata.domain.QProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// Static imports: 
// + CollQueryFactory.* : for QueryDsl
// + Matchers.*: for junit Assert parameters
// + Assert.* : for AssertThat() and other params
import static com.mysema.query.collections.CollQueryFactory.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/application-context.xml"})
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductUnitTest {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductUnitTest.class);
	
	
	private static final QProduct $ = QProduct.product;
	
	Product macBook, iPad, iPod, turntable;
	
	List<Product> products;
	
	
	@Before
	public void setUp() {
		macBook = new Product("MacBook Pro", "Apple laptop");
		iPad = new Product("iPad", "Apple tablet");
		iPod = new Product("iPod", "Apple MP3 player");
		turntable = new Product("Turntable", "Vynil player");
		
		products = Arrays.asList(macBook, iPad, iPod, turntable);
	}
	
	
	@Test
	public void testFindAllAppleProducts() {
		List<Product> results = from($, products).where($.description.contains("Apple")).list($); 
		
		assertThat(results, hasSize(3));
		assertThat(results, hasItems(macBook, iPad, iPod));
	}	
	
	@Test
	public void testFindAllAppleProductName() {
		List<String> results = from($, products).where($.description.contains("Apple")).list($.name);
		
		assertThat(results, hasSize(3));
		assertThat(results, hasItems(macBook.getName(), iPad.getName(), iPod.getName()));
	}
	
	@Test
	public void testFindPlayers() {
		List<Product> results = from($, products).where($.description.contains("player")).list($);
		
		assertThat(results, hasSize(2));
		assertThat(results, hasItems(iPod, turntable));
	}
}
