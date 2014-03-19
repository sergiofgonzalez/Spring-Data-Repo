package org.joolzminer.examples.sdata.jpa;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.joolzminer.examples.sdata.jpa.core.Product;
import org.joolzminer.examples.sdata.jpa.core.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


// Static Imports
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
public class ProductRepositoryIntegrationTests {
	
	@Inject private DataSource dataSource;
	
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
	public void testCreateProduct() {
		Product product = new Product("Camera bag", new BigDecimal(49.99));
		product = productRepository.save(product);

		assertThat(product.getId(), is(notNullValue()));
		assertThat(productRepository.findOne(product.getId()), is(product));
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void testLookupProductsByDescription() {
		Pageable pageable = new PageRequest(0, 1, Direction.DESC, "name");
		Page<Product> page = productRepository.findByDescriptionContaining("Apple", pageable);
		
		assertThat(page.getContent(), hasSize(1));
		assertThat(page, Matchers.<Product> hasItems(hasProperty("name", is("iPad"))));
		assertThat(page.getTotalElements(), is(2L));
		assertThat(page.isFirstPage(), is(true));
		assertThat(page.isLastPage(), is(false));
		assertThat(page.hasNextPage(), is(true));		
	}
	
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void findProductsByAttributes() {
		List<Product> products = productRepository.findByAttributeAndValue("connector", "plug");
		
		assertThat(products, Matchers.<Product> hasItems(hasProperty("name", is("Dock"))));
	}
}
