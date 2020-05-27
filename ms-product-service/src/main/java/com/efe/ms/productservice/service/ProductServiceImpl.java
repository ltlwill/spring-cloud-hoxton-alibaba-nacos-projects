package com.efe.ms.productservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.efe.ms.productservice.dao.ComboRepository;
import com.efe.ms.productservice.dao.ProductRepository;
import com.efe.ms.productservice.domain.Combo;
import com.efe.ms.productservice.domain.Product;
import com.efe.ms.productservice.vo.Pagination;

/**
 * 
 * <p>
 * 产品(Product)业务实现类:
 * </p>
 * 
 * @author Liu TianLong 2018年8月24日 下午5:52:37
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ComboRepository comboRepository;

	@Override
	public Product getProductBySku(String sku) {
		if (StringUtils.isBlank(sku)) {
			return null;
		}
		return productRepository.getProductBySku(sku);
	}

	@SuppressWarnings("serial")
	@Override
	public List<Product> getProductsBySkus(List<String> skus) {
		if (CollectionUtils.isEmpty(skus)) {
			return null;
		}
		return productRepository.findAll(new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and(
						cb.conjunction(),
						cb.and(cb.function("concat", String.class,
								(root.get("id"))).in(skus))); // where id
																// in(???);
			}
		}, Sort.by(Direction.ASC, "id"));
	}

	@SuppressWarnings("serial")
	@Override
	public Pagination<Product> getAllProducts(final Pagination<Product> page,
			final Product product) {
		return Pagination.of(productRepository.findAll(
				new Specification<Product>() {
					@Override
					public Predicate toPredicate(Root<Product> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.and(cb.conjunction(),
								cb.equal(root.get("status"), "archive")); // 产品状态必须是"archive"
						if (StringUtils.isNotBlank(product.getId())) { // 产品ID
							predicate = cb.and(predicate, cb.equal(
									cb.function("concat", String.class,
											root.get("id")), product.getId()));
						}
						if (StringUtils.isNotBlank(product.getSku())
								&& StringUtils.isBlank(product.getId())) { // SKU条件(即：ID)条件
							predicate = cb.and(predicate, cb.equal(
									cb.function("concat", String.class,
											root.get("id")), product.getSku()));
						}
						if (StringUtils.isNotBlank(product.getEname())) { // 产品名称条件
							predicate = cb.and(
									predicate,
									cb.like(root.get("ename"),
											"%" + product.getEname() + "%"));
						}
						if (StringUtils.isNotBlank(product.getWareHouse())) {
							predicate = cb.and(
									predicate,
									cb.like(root.get("wareHouse"), "%"
											+ product.getWareHouse() + "%"));
						}
						if (product.getProductLine() != null) { // 产品线条件
							predicate = cb.and(
									predicate,
									cb.equal(root.get("productLine"),
											product.getProductLine()));
						}
						if (StringUtils
								.isNotBlank(product.getProductLinesStr())) { // 产品线集合条件
							predicate = cb.and(
									predicate,
									root.get("productLine").in(
											product.getProductLinesStr()));
						}
						if (StringUtils.isNotBlank(product.getKeyword())) { // 关键字查询
							Predicate orPredicate = cb.and(cb.like(
									root.get("ename"),
									"%" + product.getKeyword() + "%"));
							orPredicate = cb.or(
									orPredicate,
									cb.like(root.get("wareHouse"), "%"
											+ product.getKeyword() + "%"));
							orPredicate = cb.or(
									orPredicate,
									cb.like(root.get("id"),
											"%" + product.getKeyword() + "%"));
							predicate = cb.and(predicate, orPredicate);
						}
						return predicate;
					}
				}, PageRequest.of(page.getPageNo() - 1, page.getPageSize(),
						Sort.by(Direction.DESC, "id")))); // 按ID字段降序排序
	}
	
	@Override
	public Pagination<Product> getAllProductsByPage(Integer pageNo, Integer pageSize,
			Product product) {
		return getAllProducts(new Pagination<Product>(pageNo, pageSize), product); 
	}

	@Override
	public List<Combo> getComboListBySku(String sku) {
		if (StringUtils.isBlank(sku)) {
			return null;
		}
		List<Combo> combos = comboRepository.getComboListBySku(sku);
		if (CollectionUtils.isEmpty(combos)) {
			combos = new ArrayList<Combo>();
			Product product = getProductBySku(sku);
			if (product == null) {
				return null;
			}
			combos.add(new Combo(sku));
		}
		// 根据所有的subId(即:sku)获取产品并转换为Map(key:sku,value:产品信息)
		Map<String, Product> productMap = Optional
				.ofNullable(
						getProductsBySkus(combos.stream().map(Combo::getSubId)
								.collect(Collectors.toList()))).get().stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));
		// 从Product中获取其他字段信息
		combos.forEach(combo -> setComboInfoFromProduct(
				productMap.get(combo.getSubId()), combo));
		return combos;
	}

	private Combo setComboInfoFromProduct(Product product, Combo combo) {
		product = product == null ? new Product() : product;
		combo = combo == null ? new Combo() : combo;
		combo.setSubId(product.getId());
		combo.setSubEname(product.getEname());
		combo.setSubQty(product.getQuantity());
		combo.setDefaultColor(product.getColor());
		combo.setDefaultSize(product.getSize());
		combo.setWeight(product.getWeight() == null ? null : String
				.valueOf((product.getWeight() / 1000)));
		return combo;
	}

	@Override
	public List<String> getComboSkusBySku(String sku) {
		if (StringUtils.isBlank(sku)) {
			return null;
		}
		return Optional.ofNullable(getComboListBySku(sku))
				.orElse(Collections.emptyList()).stream().map(Combo::getSubId)
				.collect(Collectors.toList());
	}

	@Override
	public String getMainSku(String sku) {
		if (StringUtils.isBlank(sku)) {
			return null;
		}
		return String.valueOf(Optional.ofNullable(getProductBySku(sku))
				.orElse(new Product()).getShipping());
	}
}
