package mykytka235.ms.report.configuration.advice;

import mykytka235.ms.report.exception.ExceptionCode;
import mykytka235.ms.report.exception.BaseException;
import mykytka235.ms.report.integration.model.ErrorInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Slf4j
public abstract class AbstractGlobalControllerExceptionHandler {

    private static final String MESSAGE_PREFIX = " | Message: ";

    protected void log(HttpServletRequest request, BaseException baseException) {
        String message = request.getRequestURI() + "?" + request.getQueryString() + MESSAGE_PREFIX + baseException.getMessage();
        if (baseException.getExceptionCode().getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error(message);
        } else {
            log.warn(message);
        }
    }

    protected static ErrorInfoDto createErrorDto(ExceptionCode exceptionCode, Integer serviceExceptionCode) {
        ErrorInfoDto errorInfoDto = new ErrorInfoDto();
        errorInfoDto.setCode(exceptionCode.getCode() * 100 + serviceExceptionCode);
        errorInfoDto.setStatus(exceptionCode.getHttpStatus().value());
        errorInfoDto.setMessages(Collections.singletonList(exceptionCode.getMessage()));

        return errorInfoDto;
    }

    protected void logError(HttpServletRequest request, RuntimeException exception) {
        String message = request.getRequestURI() + "?" + request.getQueryString();
        log.error(message, exception);
    }

    protected void logError(HttpServletRequest request, Exception exception) {
        String message = request.getRequestURI() + "?" + request.getQueryString();
        log.error(message, exception);
    }

    protected void logWarning(HttpServletRequest request, String errorMessage) {
        String message = request.getRequestURI() + "?" + request.getQueryString() + MESSAGE_PREFIX + errorMessage;
        log.warn(message);
    }
}
