package com.efe.ms.serviceconsumer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Combo extends BusinessEntity {
	
	@Id
	private String Id;
	private String mainId;
	private String subId;
	private String subCount;
	@Column(name="order")
	private String order;
	@Transient
	private Integer subQty;
	@Transient
	private String subEname;
	@Transient
	private String subQuantity;
	@Transient
	private String subStartPrice;
	@Transient
	private String subImage;
	@Transient
	private String defaultSize;
	@Transient
	private String defaultColor;
	@Transient
	private String weight;
	
	public Combo(){
		
	}
	
	public Combo(String subId){
		this.subId = subId;
	}
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getSubCount() {
		return subCount;
	}
	public void setSubCount(String subCount) {
		this.subCount = subCount;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String oorder) {
		this.order = oorder;
	}
	public Integer getSubQty() {
		return subQty;
	}
	public void setSubQty(Integer subQty) {
		this.subQty = subQty;
	}
	
	public String getSubEname() {
		return subEname;
	}

	public void setSubEname(String subEname) {
		this.subEname = subEname;
	}

	public String getSubQuantity() {
		return subQuantity;
	}
	public void setSubQuantity(String subQuantity) {
		this.subQuantity = subQuantity;
	}
	public String getSubStartPrice() {
		return subStartPrice;
	}
	public void setSubStartPrice(String subStartPrice) {
		this.subStartPrice = subStartPrice;
	}
	public String getSubImage() {
		return subImage;
	}
	public void setSubImage(String subImage) {
		this.subImage = subImage;
	}
	public String getDefaultSize() {
		return defaultSize;
	}
	public void setDefaultSize(String defaultSize) {
		this.defaultSize = defaultSize;
	}
	public String getDefaultColor() {
		return defaultColor;
	}
	public void setDefaultColor(String defaultColor) {
		this.defaultColor = defaultColor;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
}
