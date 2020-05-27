package com.efe.ms.productservice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * swagger2启用配置
 * </p>
 * 
 * @author liutianlong 2018年6月8日 上午11:43:46
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

	@Value("${spring.application.name}")
	private String applicationName; // 应用名称（即注册到服务注册中心的服务名称）

	@Bean
	public Docket createRestApi(ServletContext servletContext) {
		return new Docket(DocumentationType.SWAGGER_2)
		  	    /*
		  	     * 注:在使用spring cloud gateway作为服务网关聚合swagger2文档时，文档中的Base URL缺少服务名(服务提供者)的问题,如：localhost:8730/getProductlines,
		  	     * 应该是：localhost:8730/product-service/getProductlines才对(其中localhost:8730为网关地址)，这样就会导致在swagger页面做Try it out时请求为404,
		  	     * 为了解决使用spring cloud gateway作为网关带来的文档聚合问题，需要重写一下pathProvider，如果使用的是zuul,可以不用重写。如下所示：
		  	     */
				.pathProvider(new RelativePathProvider(servletContext) { 
					@Override
					public String getApplicationBasePath() {
						return super.getDocumentationPath() + applicationName;
					}
				})
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("com.efe.ms.productservice.web"))
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.apis(RequestHandlerSelectors
						.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build();
	}

	/**
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param
	 * @author liutianlong
	 * @date 2018年6月8日 上午11:46:05
	 * @return ApiInfo
	 */
	@SuppressWarnings("deprecation")
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("基础产品服务").description("基础产品服务文档")
		// .termsOfServiceUrl("http://www.hengzhiyi.cn")
				.contact("liutl@iefiel.com").version("1.0").build();
	}
}
