package com.paperless.api.core.document.config;

import com.paperless.api.core.document.CustomOperation;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerGroupConfig {
    @Bean
    GroupedOpenApi allApi() {
        return GroupedOpenApi.builder()
                .group("01-ALL")
                .packagesToScan("com.connectingroad.renewalapiserver")
                .addOperationCustomizer(CustomOperation.customizer())
                .build();
    }

    @Bean
    public GroupedOpenApi authenticationApi() {
        return GroupedOpenApi.builder()
                .group("02-Authentication")
                .packagesToScan("com.connectingroad.renewalapiserver.authentication")
                .addOperationCustomizer(CustomOperation.customizer())
                .build();
    }

    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
                .group("03-member")
                .packagesToScan("com.connectingroad.renewalapiserver.member")
                .addOperationCustomizer(CustomOperation.customizer())
                .build();
    }

    @Bean
    public GroupedOpenApi notificationApi() {
        return GroupedOpenApi.builder()
                .group("04-Notification")
                .packagesToScan("com.connectingroad.renewalapiserver.notification")
                .addOperationCustomizer(CustomOperation.customizer())
                .build();
    }
}
