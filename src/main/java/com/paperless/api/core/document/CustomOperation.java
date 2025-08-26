package com.paperless.api.core.document;

import com.paperless.api.core.document.aspect.ExampleHolder;
import com.paperless.api.core.document.aspect.SwaggerExceptionExampleDto;
import com.paperless.api.core.document.aspect.annotation.ApiErrorCodeExamples;
import com.paperless.api.core.document.aspect.annotation.EnumExceptionMapping;
import com.paperless.api.core.error.ExceptionCreator;
import com.paperless.api.core.error.ExceptionInterface;
import com.paperless.api.core.error.httpException.AbstractHttpException;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

import java.util.*;
import java.util.stream.Collectors;

public class CustomOperation {

    public static OperationCustomizer customizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiErrorCodeExamples apiErrorCodeExamples = handlerMethod.getMethodAnnotation(ApiErrorCodeExamples.class);

            if (apiErrorCodeExamples != null) {
                for (EnumExceptionMapping mapping : apiErrorCodeExamples.value()) {
                    generateErrorCodeResponseExample(operation, mapping.enumClass(), mapping.values());
                }
            }
            return operation;
        };
    }

    private static void generateErrorCodeResponseExample(Operation operation, Class<? extends Enum<?>> enumClass, String[] enumConstants) {
        ApiResponses responses = operation.getResponses();

        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(enumConstants)
                .map(constantName -> {
                    Enum<?> enumConstant = Enum.valueOf(enumClass.asSubclass(Enum.class), constantName);

                    if (enumConstant instanceof ExceptionInterface exceptionInterface) {
                        AbstractHttpException httpStatus = ExceptionCreator.create(exceptionInterface);

                        return ExampleHolder.builder()
                                .holder(getSwaggerExample(exceptionInterface))
                                .code(httpStatus.getStatus().value())
                                .name(exceptionInterface.getErrorCode())
                                .build();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(ExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private static Example getSwaggerExample(ExceptionInterface errorCode) {
        SwaggerExceptionExampleDto swaggerExceptionExampleDto = SwaggerExceptionExampleDto.builder()
                .errorCode(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .build();
        Example example = new Example();
        example.setValue(swaggerExceptionExampleDto);

        return example;
    }

    private static void addExamplesToResponses(ApiResponses responses,
                                               Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach((statusCode, exampleHolders) -> {

            ApiResponse apiResponse = Optional.ofNullable(responses.get(String.valueOf(statusCode)))
                    .orElseGet(ApiResponse::new);

            Content content = Optional.ofNullable(apiResponse.getContent())
                    .orElseGet(Content::new);

            MediaType mediaType = Optional.ofNullable(content.get("application/json"))
                    .orElseGet(MediaType::new);

            exampleHolders.forEach(exampleHolder -> {
                mediaType.addExamples(exampleHolder.getName(), exampleHolder.getHolder());
            });

            content.addMediaType("application/json", mediaType);
            apiResponse.setContent(content);
            responses.addApiResponse(String.valueOf(statusCode), apiResponse);
        });
    }
}
