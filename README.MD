Документация:  
https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api#1-enabling-springfoxs-swagger-ui  
https://blog.mloza.pl/spring-boot-swagger-ui/

### АКТУАЛЬНОЕ  
https://stackoverflow.com/questions/37671125/how-to-configure-spring-security-to-allow-swagger-url-to-be-accessed-without-aut
Если вы используете Spring Boot 3, вам нужно использовать: springdoc-openapi-starter-webmvc-uiкак написано во введении к документу.

И используйте конфигурацию безопасности, что-то вроде этого:
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
               .csrf().disable()
               .authorizeHttpRequests(a -> a
                       .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                       .anyRequest().authenticated()
               ).build();
   }
}
Интерфейс Swagger:http://{your host}:{your port}/swagger-ui/index.html
JSON:http://{your host}:{your port}/v3/api-docs
ямл:http://{your host}:{your port}/v3/api-docs.yaml


### Доступ
http://localhost:8080/swagger-ui.html
http://localhost:8080/swagger-ui/index.html


### Зависимости Maven 

    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>3.0.0</version>
    </dependency>

### Исправление проблем запуска
https://stackoverflow.com/questions/42863382/java-io-filenotfoundexception-class-path-resource-cannot-be-opened-because-it-d  

в application.properties добавить 
    
    spring.mvc.pathmatch.matching-strategy=ant_path_matcher

Иначе Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException: Cannot invoke "org.springframework.web.servlet.mvc.condition.PatternsRequestCondition.getPatterns()" because "this.condition" is null

### Описание эндпойнта   
    @Operation(summary = "Что делает", description = "Описание")   

    @ApiResponses(value = {
        @ApiResponse(responseCode = "Какой код, например 200", description = "Что он значит"),
        @ApiResponse(responseCode = "Другой код, например 500", description = "Что он значит")
    })
ApiResponses и ApiResponse импортируем из io.swagger.v3

### Изменение общего описания проекта в интерфейсе  
см. класс SwaggerConfig - создаётся ApiInfo и передаётся в new Docket(DocumentationType.OAS_30)
.apiInfo(apiInfo())

### Скрыть basic-error-controller

В конфиг Swagger к Docket добавлем пакет, от которого только искать/описывать эндпойнты

    .apis(RequestHandlerSelectors.basePackage("ПУТЬ ПАКЕТА, например com.baeldung.swaggerconf.controller"))

### Скрыть автоматически добавленные коды ответа  
В конфиг Swagger к Docket между select() и build() добавлем

    .useDefaultResponseMessages(false)
