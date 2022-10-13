package com.doinb.spring.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig extends WebMvcConfigurationSupport {

    /**
     * 可以分目录搭建 swagger
     * 扫描 com.doinb.web 包路径
     *
     * 使用文档参考： https://blog.csdn.net/X_Xian_/article/details/82969105
     * https://baijiahao.baidu.com/s?id=1716807526757014391&wfr=spider&for=pc
     *
     * 注意： 使用 Knife4j 2.0.6+ 版本，Spring Boot 的版本要求 2.2.x+
     * https://doc.xiaominfo.com/v2/documentation/
     * @return
     */
    @Bean
    public Docket createRestApiDoc1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("BAT-OFFER-【web页面接口】")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doinb.web"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApiDoc2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("BAT-OFFER-【用户详情】")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doinb.spring.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BAT-OFFER")
                .description("BAT-OFFER RESTFul APIs")
                .version("2.0.0")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
