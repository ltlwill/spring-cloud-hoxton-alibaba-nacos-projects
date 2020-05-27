package com.efe.ms.common.domain;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BusinessEntity implements Serializable,Cloneable {

	protected String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
