package org.joolzminer.examples.sdata.jpa.matcher;

import org.hamcrest.Matcher;
import org.joolzminer.examples.sdata.jpa.core.Product;
import org.joolzminer.examples.sdata.jpa.order.LineItem;
import org.joolzminer.examples.sdata.jpa.order.Order;

import static org.hamcrest.Matchers.*;


public class OrderMatchers {

	public static <T> Matcher<Iterable<? super T>> containsOrder(Matcher<? super T> matcher) {
		return hasItem(matcher);
	}
	
	public static Matcher<Order> LineItem(Matcher<LineItem> matcher) {
		return hasProperty("lineItems", hasItem(matcher));
	}
	
	public static Matcher<LineItem> Product(Matcher<Product> matcher) {
		return hasProperty("product", matcher);
	}
}
