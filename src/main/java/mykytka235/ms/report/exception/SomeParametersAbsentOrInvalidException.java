package mykytka235.ms.report.exception;

public class SomeParametersAbsentOrInvalidException extends BaseException {

    public SomeParametersAbsentOrInvalidException(String message) {
        super(ExceptionCode.SOME_PARAMETERS_ABSENT_OR_INVALID_EXCEPTION, message);
    }

}
