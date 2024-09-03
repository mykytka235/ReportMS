package mykytka235.ms.report.exception;

public class InnerServiceException extends BaseException {

    public InnerServiceException(String message) {
        super(ExceptionCode.INTERNAL_SERVICE_EXCEPTION, message);
    }

}
