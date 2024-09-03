package mykytka235.ms.report.configuration.rest.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class DefaultContentTypeRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        addDefaultContentTypeHeadersIfNotPresent(httpRequest);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

    private void addDefaultContentTypeHeadersIfNotPresent(HttpRequest httpRequest) {
        HttpHeaders headers = httpRequest.getHeaders();

        if(Objects.isNull(headers.getContentType())) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        if(headers.getAccept().isEmpty()) {
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        }
    }
}
