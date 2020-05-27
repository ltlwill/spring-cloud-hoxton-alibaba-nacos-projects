package com.efe.ms.zuulgateway.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * 
 * <p>
 * swagger文档聚合(聚合各个微服务的api):
 * </p>
 * 
 * @author Liu TianLong
 * @date 2018年9月25日 下午4:00:48
 */
@Component
@Primary
public class ZuulAggregationSwaggerResourcesProvider implements
		SwaggerResourcesProvider {

	private static final String REPLACE_PATTERN = "\\*\\*";
	private static final String SWAGGER_DOCS_PATH = "v2/api-docs";
	private static final String SWAGGER_VERSION = "2.0";
	private static final Pattern IGNORE_PATH_PATTERN = Pattern
			.compile("config-server|\\w*(-route)$",Pattern.CASE_INSENSITIVE);
	private static final Pattern AGGREATION_PATH_PATTERN = Pattern
			.compile("\\w*(-service)$|\\w*(-consumer)$",Pattern.CASE_INSENSITIVE); // 需要生成文档的服务名称正则表达式
	private static final Pattern NOT_AGGREATION_PATH_PATTERN = Pattern
			.compile("\\w*(-server)$",Pattern.CASE_INSENSITIVE);  // 不需要生成文档的服务名称正则表达式

	private final RouteLocator routeLocator;

	public ZuulAggregationSwaggerResourcesProvider(RouteLocator routeLocator) {
		this.routeLocator = routeLocator;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
		routeLocator
				.getRoutes()
				.stream()
				.filter(route -> AGGREATION_PATH_PATTERN.matcher(route.getId())
						.matches()) // 过滤不需要聚合的API信息
				.forEach(
						route -> resources.add(swaggerResources(
								route.getId(),
								route.getFullPath().replaceAll(REPLACE_PATTERN,
										SWAGGER_DOCS_PATH))));
		return resources;
	}

	private SwaggerResource swaggerResources(String name, String location) {
		SwaggerResource resource = new SwaggerResource();
		resource.setName(name);
		resource.setLocation(location);
		resource.setSwaggerVersion(SWAGGER_VERSION);
		return resource; 
	}

}
