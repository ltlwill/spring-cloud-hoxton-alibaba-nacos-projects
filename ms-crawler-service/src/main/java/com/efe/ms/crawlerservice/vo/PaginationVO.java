package com.efe.ms.crawlerservice.vo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class PaginationVO implements Serializable {

	protected int pageNo = 1;
	protected int pageSize = 20;
	protected long totalRows;
	protected int totalPages;
	protected List<?> rows;
	protected String sortProp;
	protected String sortType;
	protected String keyword;

	protected PaginationVO(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	protected PaginationVO(int pageNo, int pageSize, long totalRows, List<?> rows) {
		this(pageNo, pageSize);
		this.totalRows = totalRows;
		this.rows = rows;
	}

	protected PaginationVO(int pageNo, int pageSize, int totalPages, long totalRows, List<?> rows) {
		this(pageNo, pageSize);
		this.totalPages = totalPages;
		this.totalRows = totalRows;
		this.rows = rows;
	}

	public Sort getSort() {
		return sortProp == null ? Sort.unsorted()
				: Sort.by(
						(SortType.ASC.equalsIgnoreCase(this.sortType)
								|| SortType.ASCENDING.equalsIgnoreCase(this.sortType)) ? Direction.ASC : Direction.DESC,
						this.sortProp);
	}

	public PageRequest toPageRequest() {
		if (this.sortProp == null || "".equals(this.sortProp.trim())) {
			return PageRequest.of(this.pageNo - 1, this.pageSize, Sort.unsorted());
		} else {
			return PageRequest.of(this.pageNo - 1, this.pageSize, getSort());
		}
	}

	public PageRequest toPageRequest(Sort sort) {
		return PageRequest.of(this.pageNo - 1, this.pageSize, sort);
	}

	public static PaginationVO from(Page<?> page) {
		final PaginationVO pagedVO = new PaginationVO(page.getNumber() + 1, page.getSize(), page.getTotalPages(),
				page.getTotalElements(), page.getContent());
		if (page.getPageable().getSort().isSorted()) {
			page.getPageable().getSort().forEach(o -> {
				pagedVO.setSortProp(o.getProperty());
				pagedVO.setSortType(o.getDirection().toString());
			});
		}
		return pagedVO;
	}

	public static class SortType {
		public static final String ASC = "asc";
		public static final String ASCENDING = "ascending";
		public static final String DESC = "desc";
		public static final String DESCENDING = "descending";
	}

}
