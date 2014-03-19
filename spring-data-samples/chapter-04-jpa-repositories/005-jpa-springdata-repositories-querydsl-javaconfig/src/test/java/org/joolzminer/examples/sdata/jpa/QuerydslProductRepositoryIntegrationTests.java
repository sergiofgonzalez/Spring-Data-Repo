package org.joolzminer.examples.sdata.jpa;

import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.joolzminer.examples.sdata.jpa.core.Product;
import org.joolzminer.examples.sdata.jpa.core.ProductRepository;
import org.joolzminer.examples.sdata.jpa.core.QProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.Predicate;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfig.class)
public class QuerydslProductRepositoryIntegrationTests {
	
	@Inject private DataSource dataSource;
	
	@Inject private ProductRepository productRepository;

	static final QProduct product = QProduct.product;
	
	
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
	public void findProductsByQuerydslPredicate() {
		Product iPad = productRepository.findOne(product.name.eq("iPad"));
		Predicate tablets = product.description.contains("tablet");
		
		Iterable<Product> result = productRepository.findAll(tablets);
		
		assertThat(result, is(Matchers.<Product> iterableWithSize(1)));
		assertThat(result, hasItem(iPad));
	}
	

}
