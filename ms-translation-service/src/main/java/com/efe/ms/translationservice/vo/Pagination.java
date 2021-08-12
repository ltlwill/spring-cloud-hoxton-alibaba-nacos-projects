package com.efe.ms.translationservice.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;

import com.efe.ms.common.vo.PaginationVO;

/**
 * 
 * <p>
 * 分页实体VO类:
 * </p>
 * 
 * @author Liu TianLong 2018年10月17日 下午2:23:14
 */
@SuppressWarnings("serial")
public class Pagination<T> extends PaginationVO<T> implements Serializable, Cloneable {

	public Pagination() {
	}

	public Pagination(Collection<T> data) {
		super(data);
	}

	public Pagination(Integer pageNo, Integer pageSize) {
		super(pageNo, pageSize);
	}

	public Pagination(Integer pageNo, Integer pageSize, Long totalCount, Collection<T> data) {
		super(pageNo, pageSize, totalCount, data);
	}

	public Pagination(Page<T> page) {
		this(page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getContent());
	}

	public Pagination<T> with(List<T> list) {
		return new Pagination<>(list);
	}

	public Pagination<T> with(List<T> list, long total) {
		Pagination<T> pageVo = new Pagination<>(list);
		pageVo.setTotal(total);
		return pageVo;
	}

	public RowBounds toRowBounds() {
		int pageNum = this.getPage(), pageSize = this.getPageSize();
		return new RowBounds(pageNum == 0 ? 1 : pageNum, pageSize == 0 ? 10 : pageSize);
	}

	public static <T> Pagination<T> from(List<T> list) {
		return new Pagination<>(list);
	}

}
