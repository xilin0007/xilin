package com.fxl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 使用注解的方式来扫描API 无需在Spring的xml配置文件来配置，由 @EnableWebMvc 代替
 * 如需使用Swagger UI模板
 * 可在Swagger官网下载。地址：https://github.com/swagger-api/swagger-ui。
 * 下载完成后将swagger-ui下的dist目录下的模板放入项目中，如在项目webapp下新建swagger放swagger-ui模板。
 * 在spring-mvc中配置swagger文件夹自动过滤。
 * <mvc:resources mapping="/swagger/**" location="/swagger/" cache-period="31556926" />
 */
@Configuration //相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文
@EnableSwagger2
@EnableWebMvc //启用Spring MVC组件
//com.fxl.template.index.encyclopedia.controller
//@ComponentScan(basePackages = { "com.fxl.template.*.controller" })
public class MySwaggerConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()// 选择哪些路径和API会生成document
                .apis(RequestHandlerSelectors.any())// 对所有api进行监控
                .paths(PathSelectors.any())// 对所有路径进行监控
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("template项目")
                .termsOfServiceUrl("https://github.com/xilin0007")
                .description("template模板")
                .contact(new Contact("fangxilin", "https://github.com/xilin0007", "fangxlsmile@163.com"))
                .build();

    }

}