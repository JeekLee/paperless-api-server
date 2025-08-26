package com.paperless.api.core.document.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "AccessToken Authentication",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "access-token"
)
@SecurityScheme(
        name = "RefreshToken Authentication",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "refresh-token"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Connectors API")
                .description(
                        "Connectors API document by " +
                                    "[@JeekLee](https://github.com/JeekLee), [@Baek](https://github.com/naver0504)"
                );


        Server localServer = new Server();
        localServer.setDescription("Connectors API local server");
        localServer.setUrl("http://localhost:8080");

        Server devServer = new Server();
        devServer.setDescription("Connectors API development server");
        devServer.setUrl("https://dev-api.connectforme.com");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, devServer))
                .security(List.of(
                        new SecurityRequirement().addList("AccessToken Authentication"),
                        new SecurityRequirement().addList("RefreshToken Authentication")
                ));
    }
}
