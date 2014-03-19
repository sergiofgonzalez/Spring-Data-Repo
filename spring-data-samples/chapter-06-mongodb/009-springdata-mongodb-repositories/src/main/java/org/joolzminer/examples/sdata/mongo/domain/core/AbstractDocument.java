package org.joolzminer.examples.sdata.mongo.domain.core;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;

public class AbstractDocument {
	
	@Id
	private BigInteger id;

	public BigInteger getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (id == null || obj == null || !(getClass().equals(obj.getClass()))) {
			return false;
		}
		
		AbstractDocument that = (AbstractDocument) obj;
		return id.equals(that.getId());		
	}
	
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}
