package org.joolzminer.examples.sdata.mongo.domain.core;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document
public class Product extends AbstractDocument {
	private String name, description;
	private BigDecimal price;
	private Map<String, String> attributes = new HashMap<>();
	
	public Product(String name, BigDecimal price) {
		this(name, price, null);
	}
	
	@PersistenceConstructor
	public Product(String name, BigDecimal price, String description) {
		Assert.hasText(name, "Name must not be null or empty!");
		Assert.isTrue(BigDecimal.ZERO.compareTo(price) < 0, "Price must be greater than zero!");
		
		this.name = name;
		this.price = price;
		this.description = description;
	}
	
	public void setAttribute(String name, String value) {
		Assert.hasText(name, "The attribute's name must not be null or empty!");
		
		if (value == null) {
			attributes.remove(value);
		} else {
			attributes.put(name, value);
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", getId())
			.append("name", name)
			.append("description", description)
			.append("price", price)
			.append("attributes", getAttributes())
			.toString();
	}	
}
