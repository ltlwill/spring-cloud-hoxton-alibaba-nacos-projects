package com.efe.ms.zuulgateway;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 集成配置
 * 
 * @author Tianlong Liu
 * @2020年5月22日 下午3:27:43
 */
@Configuration
@EnableSwagger2
// 用于控制是否开启swagger API文档
@ConditionalOnProperty(prefix = "swagger2", name = { "enabled" }, havingValue = "true")
public class Swagger2 {

	@Value("${spring.application.name}")
	private String applicationName; // 应用名称（即注册到服务注册中心的服务名称）

	@Bean
	public Docket createRestApi(final ServletContext servletContext) {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo());
		return new Docket(DocumentationType.SWAGGER_2).pathProvider(new RelativePathProvider(servletContext) {
			@Override
			public String getApplicationBasePath() {
				return super.getDocumentationPath() + applicationName;
			}
		}).apiInfo(getApiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.efe.ms.zuulgateway.web"))
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
				.build().globalOperationParameters(parameters());
	}

	@SuppressWarnings("deprecation")
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("EFE微服务接口文档").description("微服务文接口档聚合(zuul)")
				.termsOfServiceUrl("http://www.hengzhiyi.cn").contact("liutl@iefiel.com").version("1.0").build();
	}

	private List<Parameter> parameters() {
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new ParameterBuilder().name("access-token").description("请求令牌").modelRef(new ModelRef("string"))
				.parameterType("string").required(true).build());
		return params;
	}
}
