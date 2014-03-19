package org.joolzminer.examples.sdata.jpa.matcher;

import org.hamcrest.Matcher;
import org.joolzminer.examples.sdata.jpa.core.Product;

import static org.hamcrest.Matchers.*;

public class CoreMatchers {

	public static <T> Matcher<T> with(Matcher<T> matcher) {
		return matcher;
	}
	
	public static Matcher<Product> named(String name) {
		return hasProperty("name", is(name));
	}
}
