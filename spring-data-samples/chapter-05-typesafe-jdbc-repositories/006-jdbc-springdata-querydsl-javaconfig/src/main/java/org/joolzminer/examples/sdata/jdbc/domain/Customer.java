package org.joolzminer.examples.sdata.jdbc.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

public class Customer extends AbstractEntity {

	private String firstName;
	private String lastName;
	
	private EmailAddress emailAddress;
	
	private Set<Address> addresses = new HashSet<Address>();
	
	public Customer() {		
	}
	
	public Customer(String firstName, String lastName) {
		Assert.hasText(firstName);
		Assert.hasText(lastName);
		
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void addAddress(Address address) {
		Assert.notNull(address);
		addresses.add(address);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<Address> getAddresses() {
		return Collections.unmodifiableSet(addresses);
	}	
	
	public void clearAddresses() {
		addresses.clear();
	}
}
