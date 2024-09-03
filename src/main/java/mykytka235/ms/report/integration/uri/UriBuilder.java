package mykytka235.ms.report.integration.uri;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class UriBuilder {

    protected URI buildStaticUri(String uri, String path) {
        return UriComponentsBuilder.fromUriString(uri)
                .path(path)
                .build()
                .encode()
                .toUri();
    }

}
