package mykytka235.ms.report.integration.handler;

import mykytka235.ms.report.constants.ServiceName;
import mykytka235.ms.report.integration.model.ErrorInfoDto;

public class NotificationResponseErrorHandler extends BasicResponseErrorHandler {

    @Override
    String getServiceName() {
        return ServiceName.NOTIFICATION.getName();
    }

    @Override
    void handleError(ErrorInfoDto errorInfoDto) {
        throw new RemoteMsPassThroughException(errorInfoDto);
    }
}
