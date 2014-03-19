package org.joolzminer.examples.sdata.mongo.utils;

import org.hamcrest.Matcher;
import org.joolzminer.examples.sdata.mongo.domain.core.Product;
import org.joolzminer.examples.sdata.mongo.domain.order.LineItem;
import org.joolzminer.examples.sdata.mongo.domain.order.Order;

import static org.hamcrest.Matchers.*;

public class SugarMatchers {
	public static <T> Matcher<Iterable<? super T>> containsOrder(Matcher<? super T> matcher) {
		return hasItem(matcher);
	}
	
	public static Matcher<Product> named(String name) {
		return hasProperty("name", is(name));
	}
	
	public static Matcher<LineItem> Product(Matcher<Product> matcher) {
		return hasProperty("product", matcher);
	}
	
	public static <T> Matcher<T> with(Matcher<T> matcher) {
		return matcher;
	}
	
	public static Matcher<Order> LineItem(Matcher<LineItem> matcher) {
		return hasProperty("lineItems", hasItem(matcher));
	}
}
