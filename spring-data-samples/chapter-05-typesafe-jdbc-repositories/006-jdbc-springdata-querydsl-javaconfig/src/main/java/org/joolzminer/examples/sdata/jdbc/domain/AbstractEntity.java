package org.joolzminer.examples.sdata.jdbc.domain;

public class AbstractEntity {

	private Long id;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
