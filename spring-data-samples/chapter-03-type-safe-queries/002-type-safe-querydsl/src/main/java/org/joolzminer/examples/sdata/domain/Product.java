package org.joolzminer.examples.sdata.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.mysema.query.annotations.QueryEntity;

@QueryEntity
public class Product extends AbstractEntity {
	private String name;
	private String description;
	private BigDecimal price;
	private Map<String, String> attributes = new HashMap<String,String>();

	public Product() {		
	}
	
	public Product(String name) {
		this(name, null);
	}
	
	public Product(String name, String description) {
		this.name = name;
		this.description = description;
	}
	

	public void setAttributes(String key, String value) {
		attributes.put(key, value);
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
}
