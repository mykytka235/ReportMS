package mykytka235.ms.report.configuration.rest.interceptor;

import mykytka235.ms.report.util.CorrelationIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static mykytka235.ms.report.util.CorrelationIdUtil.CORRELATION_ID_HEADER;

public class CorrelationIdRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        prepareRequest(request);
        ClientHttpResponse clientHttpResponse = execution.execute(request, body);
        processResponse(clientHttpResponse);
        return clientHttpResponse;
    }

    private void prepareRequest(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String correlationId = CorrelationIdUtil.getId();
        if (StringUtils.isBlank(correlationId)) {
            correlationId = CorrelationIdUtil.generateId();
        }
        headers.add(CORRELATION_ID_HEADER, correlationId);
    }

    private void processResponse(ClientHttpResponse clientHttpResponse) {
        HttpHeaders headers = clientHttpResponse.getHeaders();
        String correlationId = headers.getFirst(CORRELATION_ID_HEADER);

        if (!StringUtils.isBlank(correlationId)) {
            CorrelationIdUtil.setId(correlationId);
        }
    }
}
