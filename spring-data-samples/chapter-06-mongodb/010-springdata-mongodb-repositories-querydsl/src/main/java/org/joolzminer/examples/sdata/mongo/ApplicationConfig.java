package org.joolzminer.examples.sdata.mongo;



import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

@Configuration
@ComponentScan(basePackageClasses = ApplicationConfig.class)
@EnableMongoRepositories
public class ApplicationConfig extends AbstractMongoConfiguration {

	@Inject
	private List<Converter<?, ?>> converters;
	
	@Override
	protected String getDatabaseName() {
		return "e-store";
	}

	@Override
	public @Bean Mongo mongo() throws Exception {
		Mongo mongo = new Mongo();
		mongo.setWriteConcern(WriteConcern.SAFE);
		
		return mongo;
	}
	
	@Override
	protected String getMappingBasePackage() {
		return "org.joolzminer.examples.sdata.mongo.domain";
	}
	
	@Override
	public CustomConversions customConversions() {
		return new CustomConversions(converters);
	}
}
