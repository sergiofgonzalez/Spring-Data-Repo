package org.joolzminer.examples.sdata.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.util.Assert;

@Entity
public class Customer extends AbstractEntity {
	private String firstName;
	private String lastName;
	
	@Column(unique = true)
	private EmailAddress emailAddress;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "customer_id")
	private Set<Address> addresses = new HashSet<Address>();
	
	protected Customer() {
	}

	public Customer(String firstName, String lastName) {
		Assert.hasText(firstName);
		Assert.hasText(lastName);
		
		this.firstName = firstName;
		this.lastName = lastName;
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
	
	public void add(Address address ) {
		Assert.notNull(address);
		addresses.add(address);
	}

	@Column(unique = true)
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "customer_id")
	public Set<Address> getAddresses() {
		return addresses;
	}		
}
