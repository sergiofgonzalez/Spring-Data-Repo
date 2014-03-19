package org.joolzminer.examples.sdata.mongo.domain.order;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joolzminer.examples.sdata.mongo.domain.core.AbstractDocument;
import org.joolzminer.examples.sdata.mongo.domain.core.Address;
import org.joolzminer.examples.sdata.mongo.domain.core.Customer;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

@Document
public class Order extends AbstractDocument {
	@DBRef
	private Customer customer;
	private Address billingAddress;
	private Address shippingAddress;
	private Set<LineItem> lineItems = new HashSet<>();
	
	public Order(Customer customer, Address shippingAddress) {
		this(customer, shippingAddress, null);
	}
	
	@PersistenceConstructor
	public Order(Customer customer, Address shippingAddress, Address billingAddress) {
		Assert.notNull(customer);
		Assert.notNull(shippingAddress);
		
		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
	}
	
	
	public void add(LineItem lineItem) {
		lineItems.add(lineItem);
	}

	public Customer getCustomer() {
		return customer;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}
	
	public Address getBillingAddress() {
		return billingAddress != null ? billingAddress : shippingAddress;
	}

	public Set<LineItem> getLineItems() {
		return Collections.unmodifiableSet(lineItems);
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		for (LineItem item : lineItems) {
			total = total.add(item.getTotal());
		}
		
		return total;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", getId())
			.append("customer", customer)
			.append("shippingAddress", shippingAddress)
			.append("billingAddress", billingAddress)
			.append("lineItems", getLineItems())
			.toString();
	}
	
}
