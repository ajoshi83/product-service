package com.poc.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Year;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    private static final String TITLE = "Product Service API";
    private static final String DESCRIPTION = "Product service API";

    private ApiInfo apiInfo() {
        String copyright = new StringBuilder().append("\u00a9 ")
                                              .append(Year.now())
                                              .append(" TechieSoft Pvt Ltd, all rights reserved. For internal use only.")
                                              .toString();
        return new ApiInfoBuilder().title(TITLE)
                                   .description(DESCRIPTION)
                                   .license(copyright)
                                   .build();
    }

    @Bean
    public Docket assessmentPlatformApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                                                      .pathMapping("/")
                                                      .select()
                                                      .paths(PathSelectors.regex("/api/.*"))
                                                      .build();
    }
}
