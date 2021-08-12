package com.efe.ms.translationservice;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	private String applicationName;

	@Bean
	public Docket createRestApi(ServletContext servletContext) {
		return new Docket(DocumentationType.SWAGGER_2).pathProvider(new RelativePathProvider(servletContext) {
			@Override
			public String getApplicationBasePath() {
				return super.getDocumentationPath() + applicationName;
			}
		}).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(this.getClass().getPackage().getName()))
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any())
				.build();
	}

	/**
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param
	 * @author liutianlong
	 * @date 2019年6月8日 上午11:46:05
	 * @return ApiInfo
	 */
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("谷歌翻译服务").description("谷歌翻译服务API文档").version("1.0").build();
	}
}
