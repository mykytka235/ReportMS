package mykytka235.ms.report.configuration.advice;

import mykytka235.ms.report.exception.BaseException;
import mykytka235.ms.report.exception.ExceptionCode;
import mykytka235.ms.report.integration.handler.RemoteMsPassThroughException;
import mykytka235.ms.report.integration.model.ErrorInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends AbstractGlobalControllerExceptionHandler {

    @Value("${service-exception-code}")
    private Integer SERVICE_EXCEPTION_CODE;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorInfoDto> handleBaseException(BaseException e, HttpServletRequest request) {
        log(request, e);

        ErrorInfoDto errorInfoDto = createErrorDto(e.getExceptionCode(), SERVICE_EXCEPTION_CODE);

        return new ResponseEntity<>(errorInfoDto, e.getExceptionCode().getHttpStatus());
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ErrorInfoDto> handleServletException(ServletException e, HttpServletRequest request) {
        logError(request, e);
        ErrorInfoDto errorInfoDto = createErrorDto(ExceptionCode.SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION, SERVICE_EXCEPTION_CODE);
        return new ResponseEntity<>(errorInfoDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfoDto> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        ErrorInfoDto errorInfoDto = createErrorDto(ExceptionCode.SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION, SERVICE_EXCEPTION_CODE);
        return new ResponseEntity<>(errorInfoDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfoDto> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        logError(request, e);

        ErrorInfoDto errorInfoDto = createErrorDto(ExceptionCode.UNKNOWN_EXCEPTION, SERVICE_EXCEPTION_CODE);
        return new ResponseEntity<>(errorInfoDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RemoteMsPassThroughException.class)
    public ResponseEntity<ErrorInfoDto> handleRemoteMsPassThroughException(RemoteMsPassThroughException e, HttpServletRequest request) {
        ErrorInfoDto errorInfoDto = e.getErrorInfoDto();
        logWarning(request, e.getMessage());
        return new ResponseEntity<>(errorInfoDto, HttpStatus.valueOf(errorInfoDto.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfoDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logWarning(request, e.getMessage());
        ErrorInfoDto errorInfoDto = createErrorDto(ExceptionCode.SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION, SERVICE_EXCEPTION_CODE);
        return new ResponseEntity<>(errorInfoDto, HttpStatus.BAD_REQUEST);
    }
}
