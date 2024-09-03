package mykytka235.ms.report.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION(40001, "Some parameters are absent, or invalid.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVICE_EXCEPTION(50001, "Issue with internal service", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_EXCEPTION(99999, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),

    FILE_INVALID_EXTENSION_EXCEPTION(40002, "File extension is not valid.", HttpStatus.BAD_REQUEST),
    FILE_EXPORT_TIME_LIMIT_EXCEPTION(40003, "File cannot be exported. Reason: export time limit", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

}
