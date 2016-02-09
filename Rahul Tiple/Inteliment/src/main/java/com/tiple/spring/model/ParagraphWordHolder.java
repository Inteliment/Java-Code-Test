package com.tiple.spring.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ParagraphWordHolder implements Serializable{

	
	private static final long serialVersionUID = -7788619177798333712L;
	private List<Map> counts;
	public List<Map> getCounts() {
		return counts;
	}
	public void setCounts(List<Map> counts) {
		this.counts = counts;
	}

	
	

}
