package com.efe.ms.crawlerservice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

public interface BaseDao<T> {
	public void add(T entity) throws DataAccessException;

	public void addList(List<T> list) throws DataAccessException;

	public T get(T entity) throws DataAccessException;

	public String getBySingle(String entity) throws DataAccessException;

	public List<T> getListPage(T entity, RowBounds rowBounds)
			throws DataAccessException;

	public void edit(T entity) throws DataAccessException;

	public void remove(T entity) throws DataAccessException;

	public List<T> getList() throws DataAccessException;

	public Long count(T entity) throws DataAccessException;

	public List<T> getListByParam(String param) throws DataAccessException;

	public T getEntity(String param) throws DataAccessException;

	public List<T> getListByEntity(T entity) throws DataAccessException;

	public List<T> getListByMapPage(Map<String, Object> params,
			RowBounds rowBounds);

	public List<T> getListByMap(Map<String, Object> params);

	public Long count(Map<String, Object> params);

	public List<T> getListByListPage(List<T> list, RowBounds rowBounds)
			throws DataAccessException;

	public List<T> getListByList(List<T> list) throws DataAccessException;

	public void batchRemove(List<T> list) throws DataAccessException;

	public void batcheditbyMap(Map<String, Object> params)
			throws DataAccessException;

	public void dynamicAdd(T entity);

	public T selectById(Object id);

	public void dynamicEdit(T entity);
}
