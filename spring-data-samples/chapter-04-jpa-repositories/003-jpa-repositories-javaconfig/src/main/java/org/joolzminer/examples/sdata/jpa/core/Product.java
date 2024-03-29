package org.joolzminer.examples.sdata.jpa.core;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.springframework.util.Assert;

@Entity
public class Product extends AbstractEntity {

	@Column(nullable = false)
	private String name;
	
	private String description;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@ElementCollection
	private Map<String, String> attributes = new HashMap<String, String>();
	
	protected Product() {		
	}
	
	public Product(String name, BigDecimal price) {
		this(name, price, null);
	}
	
	public Product(String name, BigDecimal price, String description) {
		Assert.hasText(name, "Name must not be null or empty");
		Assert.isTrue(BigDecimal.ZERO.compareTo(price) < 0, "Price must be greater than zero");
		
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	public void setAttribute(String name, String value) {
		Assert.hasText(name);
		
		if (value == null) {
			this.attributes.remove(value);
		} else {
			this.attributes.put(name, value);
		}
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}	
}
