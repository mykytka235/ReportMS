package mykytka235.ms.report.integration.handler;

import mykytka235.ms.report.integration.model.ErrorInfoDto;
import org.springframework.util.ObjectUtils;

public class RemoteMsPassThroughException extends RuntimeException {

    private final ErrorInfoDto errorInfoDto;

    RemoteMsPassThroughException(ErrorInfoDto errorInfoDto) {
        super(ObjectUtils.isEmpty(errorInfoDto.getMessages()) ? "Unknown remote MS error." : errorInfoDto.getMessages().get(0));
        this.errorInfoDto = errorInfoDto;
    }

    public ErrorInfoDto getErrorInfoDto() {
        return errorInfoDto;
    }
}
