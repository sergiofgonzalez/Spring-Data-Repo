package org.joolzminer.examples.sdata.mongo;

import junit.framework.Assert;

import org.joolzminer.examples.sdata.mongo.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class ApplicationConfigTest {

	@Test
	public void testBootstrapFromJavaConfig() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		Assert.assertNotNull(context);
	}
}
