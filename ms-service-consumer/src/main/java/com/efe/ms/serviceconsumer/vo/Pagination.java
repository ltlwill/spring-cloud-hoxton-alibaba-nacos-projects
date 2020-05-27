package com.efe.ms.serviceconsumer.vo;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 
 * <p>
 * 分页实体VO类:
 * </p>
 * 
 * @author Liu TianLong 2018年10月17日 下午2:23:14
 */
@SuppressWarnings({ "serial", "unused" })
public class Pagination<T> implements Serializable, Cloneable {

	private int pageSize = 20; // 每页显示的行数，默认20条
	private int pageNo = 1; // 当前页号,默认第一页
	private long totalPages; // 总页数
	private long totalCount; // 记录数
	private String sort;
	private String direction;
	private Collection<T> data; // 数据

	public Pagination() {
	}

	public Pagination(Collection<T> data) {
		this.data = data;
	}

	public Pagination(Integer pageNo, Integer pageSize) {
		this.pageNo = pageNo == null ? this.pageNo : pageNo;
		this.pageSize = pageSize == null ? this.pageSize : pageSize;
	}

	public Pagination(Integer pageNo, Integer pageSize, Long totalCount,
			Collection<T> data) {
		this(pageNo, pageSize);
		this.totalCount = totalCount == null ? 0 : totalCount;
		this.data = data;
	}

	public Pagination(Page<T> page) {
		this(page.getNumber() + 1, page.getSize(), page.getTotalElements(),
				page.getContent());
	}

	public PageRequest asPageRequest(Pagination<T> page) {
		return PageRequest.of(page.getPageNo() - 1, page.getPageSize());
	}

	public PageRequest asPageRequest(Pagination<T> page, Sort sort) {
		return PageRequest.of(page.getPageNo() - 1, page.getPageSize(), sort);
	}

	public static <T> Pagination<T> of(Page<T> page) {
		return page == null ? new Pagination<T>() : new Pagination<T>(page);
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public long getTotalPages() {
		return (this.totalCount % this.pageSize == 0) ? this.totalCount
				/ this.pageSize : this.totalCount / this.pageSize + 1;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Collection<T> getData() {
		return data;
	}

}
