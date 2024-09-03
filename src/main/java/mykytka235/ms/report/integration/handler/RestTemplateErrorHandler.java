package mykytka235.ms.report.integration.handler;

import mykytka235.ms.report.constants.ServiceName;
import mykytka235.ms.report.integration.model.ErrorInfoDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestTemplateErrorHandler extends BasicResponseErrorHandler {

    private final ServiceName serviceName;

    @Override
    String getServiceName() {
        return serviceName.getName();
    }

    @Override
    void handleError(ErrorInfoDto errorInfoDto) {
        throw new RemoteMsPassThroughException(errorInfoDto);
    }
}
