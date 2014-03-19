package org.joolzminer.examples.sdata.jdbc.domain;

import java.util.regex.Pattern;

import org.springframework.util.Assert;


public class EmailAddress {
	
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
	
	private String value;
	
	protected EmailAddress() {		
	}
	
	public EmailAddress(String emailAddress) {
		Assert.isTrue(isValid(emailAddress), "Invalid email address");
		value = emailAddress;
	}
	
	public static boolean isValid(String candidate) {
		return candidate == null ? false : PATTERN.matcher(candidate).matches();
	}

	@Override
	public String toString() {
		return value;
	}	
}
