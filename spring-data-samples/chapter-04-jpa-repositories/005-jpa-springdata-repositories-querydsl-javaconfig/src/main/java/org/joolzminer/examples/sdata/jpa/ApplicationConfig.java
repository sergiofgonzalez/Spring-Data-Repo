package org.joolzminer.examples.sdata.jpa;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class ApplicationConfig {

	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	
	private static final String DATASOURCE_URL = "jdbc:mysql://localhost:3306/spdata04?autoReconnect=true";

	private static final String DATASOURCE_USERNAME = "root";
	
	private static final String DATASOURCE_PASSWORD = "Accenture1";
	
	@Bean(destroyMethod = "close")
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(DRIVER_CLASS_NAME);
		dataSource.setUrl(DATASOURCE_URL);
		dataSource.setUsername(DATASOURCE_USERNAME);
		dataSource.setPassword(DATASOURCE_PASSWORD);
		
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(true);
		
		Map<String, Object> jpaPropertiesMap = new HashMap<String, Object>();
		jpaPropertiesMap.put(AvailableSettings.DIALECT, MySQL5InnoDBDialect.class.getName());
		jpaPropertiesMap.put(AvailableSettings.SHOW_SQL, false);
		jpaPropertiesMap.put(AvailableSettings.HBM2DDL_AUTO, "create" );
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setPackagesToScan(getClass().getPackage().getName());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setJpaPropertyMap(jpaPropertiesMap);
		
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		
		return transactionManager;
	}
}
