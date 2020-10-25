package com.practise.flightInfo.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage(getClass().getPackage().getName())))
                .build()
                .apiInfo(apiInfo());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //enabling swagger-ui part for visual documentation
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        //registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My REST API Flight Information Finder Cache")
                .description("This microservice has the responsability of obtaining information of a flight from its own NoSql memory or from an external service and save it")
                .version("v1")
                .contact(new Contact("Emiliano Castellanos", "https://www.linkedin.com/in/emiliano-castellanos", "castellanos.emiliano@gmail.com"))
                .build();
    }
}
