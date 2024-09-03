package mykytka235.ms.report.integration.uri;

import mykytka235.ms.report.integration.properties.NotificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class NotificationUriBuilder extends UriBuilder {

    private final NotificationProperties notificationProperties;

    public URI getSendReportUri(String from, String to, String locale, String fileName) {
        return UriComponentsBuilder.fromUriString(notificationProperties.getUri())
                .path(notificationProperties.getPath().getSendReport())
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("locale", locale)
                .queryParam("fileName", fileName)
                .build()
                .encode()
                .toUri();
    }

}
