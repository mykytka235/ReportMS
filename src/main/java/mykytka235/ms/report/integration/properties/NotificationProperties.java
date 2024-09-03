package mykytka235.ms.report.integration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ms.notification")
public class NotificationProperties extends RestTemplateCommunicationProperties {

    private Path path;

    @Getter
    @Setter
    public static class Path {
        private String sendReport;
    }
}
