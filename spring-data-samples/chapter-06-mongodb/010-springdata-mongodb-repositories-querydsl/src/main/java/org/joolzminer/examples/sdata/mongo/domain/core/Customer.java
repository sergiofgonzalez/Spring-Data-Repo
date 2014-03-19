package org.joolzminer.examples.sdata.mongo.domain.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.Assert;

@Document
public class Customer extends AbstractDocument {
	private String firstName, lastName;
	
	@Field("email")
	@Indexed(unique = true)
	private EmailAddress emailAddress;
	
	private Set<Address> addresses = new HashSet<>();

	protected Customer() {		
	}
	
	public Customer(String firstName, String lastName) {
		Assert.hasText(firstName, "FirstName must not be null or empty!");
		Assert.hasText(lastName, "LastName must not be null or empty!");
		
		this.firstName = firstName;
		this.lastName = lastName;
	}
	

	public void add(Address address) {
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", getId())
			.append("firstName", firstName)
			.append("lastName", lastName)
			.append("emailAddress", emailAddress)
			.append("addresses", getAddresses())
			.toString();			
	}	
}
