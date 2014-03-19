package org.joolzminer.examples.sdata.mongo.domain.core;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

public class Address {
	
	private final String street, city, country;
	
	public Address(String street, String city, String country) {
		Assert.hasText(street, "Street must not be null or empty!");
		Assert.hasText(city, "City must not be null or empty!");
		Assert.hasText(country, "Country must not be null or empty!");
		
		this.street = street;
		this.city = city;
		this.country = country;
	}
	
	public Address getCopy() {
		return new Address(street, city, country);
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("street", street)
			.append("city", city)
			.append("country", country)
			.toString();
	}
}
