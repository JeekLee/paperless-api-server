package com.paperless.api.core.error.httpException;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * ServiceUnavailable (503)
 * HttpExceptionCreator 사용 시, 재요청 대기 시간(초)을 Retry-After 헤더에 명시해 HttpHeaders 파라미터로 주입
 */
public class ServiceUnavailableException extends AbstractHttpException {
    public ServiceUnavailableException(String errorCode, String message) {
        super(SERVICE_UNAVAILABLE, errorCode, message, null, new HttpHeaders(new LinkedMultiValueMap<>() {{add("Retry-After", "0");}}));
    }

    /**
     * ServiceUnavailable (503)
     * 재요청 대기 시간(초)을 Retry-after 헤더에 명시하지 않는 경우, Retry-After:0으로 지정
     *
     * @param errorCode the error code
     * @param message   the message
     * @param errorLog  the error log
     */
    public ServiceUnavailableException(String errorCode, String message, String errorLog) {
        super(SERVICE_UNAVAILABLE, errorCode, message, errorLog, new HttpHeaders(new LinkedMultiValueMap<>() {{add("Retry-After", "0");}}));
    }

    /**
     * ServiceUnavailable (503)
     * 재요청 대기 시간(초)을 Retry-after 헤더에 명시
     *
     * @param errorCode     the error code
     * @param message       the message
     * @param errorLog      the error log
     * @param httpHeaders   503 status must contain the retry-after time in seconds
     */
    public ServiceUnavailableException(String errorCode, String message, String errorLog, HttpHeaders httpHeaders) {
        super(SERVICE_UNAVAILABLE, errorCode, message, errorLog, httpHeaders);
    }
}
