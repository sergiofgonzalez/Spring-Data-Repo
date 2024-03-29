package org.joolzminer.examples.sdata.jpa.core;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (this.id == null || obj == null || (!this.getClass().equals(obj.getClass()))) {
			return false;
		}
		
		AbstractEntity that = (AbstractEntity) obj;
		
		return id.equals(that.getId());
	}	
}
