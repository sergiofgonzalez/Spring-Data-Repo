package org.joolzminer.examples.sdata.jpa.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.joolzminer.examples.sdata.jpa.core.AbstractEntity;
import org.joolzminer.examples.sdata.jpa.core.Product;
import org.springframework.util.Assert;

@Entity
public class LineItem extends AbstractEntity {

	@ManyToOne
	private Product product;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	private int amount;
	
	public LineItem() {		
	}
	
	public LineItem(Product product) {
		this(product, 1);
	}
	
	public LineItem(Product product, int amount) {
		Assert.notNull(product, "The given product must not be null");
		Assert.notNull(amount > 0, "The amount of Products to be bought must be greater than zero");
		
		this.product = product;
		this.amount = amount;
		this.price = product.getPrice();
	}

	public Product getProduct() {
		return product;
	}

	public BigDecimal getUnitPrice() {
		return price;
	}
	
	public int getAmount() {
		return amount;
	}

	public BigDecimal getTotal() {
		return price.multiply(BigDecimal.valueOf(amount));
	}	
}
