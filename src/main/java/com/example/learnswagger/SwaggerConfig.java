package com.example.learnswagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Title")
                .description("Description")
                .version("1.0.0")
                .contact(new Contact("user","www.com.com","test@com.com"))
                .build();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .paths(PathSelectors.any())
                .build();
    }
}
