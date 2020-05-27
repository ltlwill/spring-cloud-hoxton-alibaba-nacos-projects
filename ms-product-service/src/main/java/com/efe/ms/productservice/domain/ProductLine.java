package com.efe.ms.productservice.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * <p>Product JPA实体类: </p> 
 * @author Liu TianLong
 * 2018年8月24日 下午5:44:22
 */
@SuppressWarnings("serial")
@Entity
@Table(name="product_line")
public class ProductLine implements Serializable
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="product_line")
	private String productLine;
	private String sort;
	@Column(name="create_time")
	private Date createTime;
	@Transient // 查询时忽略此字段
	private Date updateTime;
	@Transient // 查询时忽略此字段
	private String text;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getProductLine()
	{
		return productLine;
	}
	public void setProductLine(String productLine)
	{
		this.productLine = productLine;
		this.text = this.productLine;
	}
	public String getSort()
	{
		return sort;
	}
	public void setSort(String sort)
	{
		this.sort = sort;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
