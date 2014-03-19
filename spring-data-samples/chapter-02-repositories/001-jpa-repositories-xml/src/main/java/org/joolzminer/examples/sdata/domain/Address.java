package org.joolzminer.examples.sdata.domain;

import javax.persistence.Entity;
import org.springframework.util.Assert;

@Entity
public class Address extends AbstractEntity {
	private String street;
	private String city;
	private String country;
	
	protected Address() {		
	}
	
	public Address(String street, String city, String country) {
		Assert.hasText(street);
		Assert.hasText(city);
		Assert.hasText(country);
		
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
}
