package com.tiple.spring.model;

import java.io.Serializable;

public class ParagraphHolder implements Serializable{

	
	private static final long serialVersionUID = -7788619177798333712L;
	private String[] searchText;


	public String[] getSearchText() {
		return searchText;
	}
	public void setSearchText(String[] searchText) {
		this.searchText = searchText;
	}
	
}
