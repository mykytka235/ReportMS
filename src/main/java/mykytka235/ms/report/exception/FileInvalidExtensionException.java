package mykytka235.ms.report.exception;

public class FileInvalidExtensionException extends BaseException {

    public FileInvalidExtensionException(String message) {
        super(ExceptionCode.FILE_INVALID_EXTENSION_EXCEPTION, message);
    }

}
