package mykytka235.ms.report.exception;

public class FileExportTimeLimitException extends BaseException {

    public FileExportTimeLimitException(String message) {
        super(ExceptionCode.FILE_EXPORT_TIME_LIMIT_EXCEPTION, message);
    }
}
