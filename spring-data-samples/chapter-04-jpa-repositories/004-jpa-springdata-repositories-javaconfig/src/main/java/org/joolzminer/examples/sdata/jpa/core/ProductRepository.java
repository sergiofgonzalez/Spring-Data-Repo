package org.joolzminer.examples.sdata.jpa.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface ProductRepository extends CrudRepository<Product, Long> {
	Page<Product> findByDescriptionContaining(String description, Pageable pageable);
	
	@Query("select p from Product p where attributes[?1] = ?2")
	List<Product> findByAttributeAndValue(String attribute, String value);
}
