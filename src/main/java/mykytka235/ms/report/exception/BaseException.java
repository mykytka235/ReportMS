package mykytka235.ms.report.exception;

import lombok.Getter;

public abstract class BaseException extends RuntimeException {

    @Getter
    private final ExceptionCode exceptionCode;

    protected BaseException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    protected BaseException(ExceptionCode exceptionCode, String message, Throwable throwable) {
        super(message, throwable);
        this.exceptionCode = exceptionCode;
    }

    protected BaseException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    protected BaseException(ExceptionCode exceptionCode, String messageFormat, Object... messageArgs) {
        super(String.format(messageFormat, messageArgs));
        this.exceptionCode = exceptionCode;
    }

    @Override
    public final String getMessage() {
        return super.getMessage();
    }
}
