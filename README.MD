Документация:  
https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api#1-enabling-springfoxs-swagger-ui  
https://blog.mloza.pl/spring-boot-swagger-ui/

###Доступ

http://localhost:8080/swagger-ui/index.html

###Зависимости Maven 

    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>3.0.0</version>
    </dependency>

###Исправление проблем запуска
https://stackoverflow.com/questions/42863382/java-io-filenotfoundexception-class-path-resource-cannot-be-opened-because-it-d  

в application.properties добавить 
    
    spring.mvc.pathmatch.matching-strategy=ant_path_matcher

Иначе Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException: Cannot invoke "org.springframework.web.servlet.mvc.condition.PatternsRequestCondition.getPatterns()" because "this.condition" is null

###Описание эндпойнта   
    @Operation(summary = "Что делает", description = "Описание")   

    @ApiResponses(value = {
        @ApiResponse(responseCode = "Какой код, например 200", description = "Что он значит"),
        @ApiResponse(responseCode = "Другой код, например 500", description = "Что он значит")
    })


###Изменение общего описания проекта в интерфейсе  
см. класс SwaggerConfig - создаётся ApiInfo и передаётся в new Docket(DocumentationType.OAS_30)
.apiInfo(apiInfo())

###Скрыть basic-error-controller

В конфиг Swagger к Docket добавлем пакет, от которого только искать/описывать эндпойнты

    .apis(RequestHandlerSelectors.basePackage("ПУТЬ ПАКЕТА, например com.baeldung.swaggerconf.controller"))

###Скрыть автоматически добавленные коды ответа  
В конфиг Swagger к Docket добавлем

    .useDefaultResponseMessages(false)