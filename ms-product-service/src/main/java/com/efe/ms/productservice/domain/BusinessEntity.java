package com.efe.ms.productservice.domain;

import java.io.Serializable;

import javax.persistence.Transient;

@SuppressWarnings("serial")
public class BusinessEntity implements Serializable,Cloneable {

	@Transient
	protected String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
