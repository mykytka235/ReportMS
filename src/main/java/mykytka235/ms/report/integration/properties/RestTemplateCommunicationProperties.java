package mykytka235.ms.report.integration.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RestTemplateCommunicationProperties {

    private String uri;
    private Timeout timeout;

    public int getReadTimeout() {
        return timeout.getRead();
    }

    public int getConnectTimeout() {
        return timeout.getConnect();
    }

    @Getter
    @Setter
    public static class Timeout {
        private int connect;
        private int read;
    }
}
