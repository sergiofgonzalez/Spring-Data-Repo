package org.joolzminer.examples.sdata.mongo.domain.order;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joolzminer.examples.sdata.mongo.domain.core.AbstractDocument;
import org.joolzminer.examples.sdata.mongo.domain.core.Product;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

public class LineItem extends AbstractDocument {
	@DBRef
	private Product product;
	private BigDecimal price;
	private int amount;
	
	public LineItem(Product product) {
		this(product, 1);
	}
	
	public LineItem(Product product, int amount) {
		Assert.notNull(product, "The given Product must not be null");
		Assert.isTrue(amount > 0, "The amount of Products to be bought must be greater than zero!");
		
		this.product = product;
		this.amount = amount;
		this.price = product.getPrice();
	}
	
	public LineItem() {		
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", getId())
			.append("product", product)
			.append("price", price)
			.append("amount", amount)
			.toString();
	}	
}
