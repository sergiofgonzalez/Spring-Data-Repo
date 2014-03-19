package org.joolzminer.examples.sdata.mongo.repository;


import java.util.List;

import org.joolzminer.examples.sdata.mongo.domain.core.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>, QueryDslPredicateExecutor<Product> {
	
	Page<Product> findByDescriptionContaining(String description, Pageable pageable);
	
	@Query("{ ?0 : ?1 }")
	List<Product> findByAttributes(String key, String value);
}
