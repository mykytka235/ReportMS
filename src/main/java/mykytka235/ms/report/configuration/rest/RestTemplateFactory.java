package mykytka235.ms.report.configuration.rest;

import mykytka235.ms.report.configuration.rest.interceptor.CorrelationIdRestTemplateInterceptor;
import mykytka235.ms.report.configuration.rest.interceptor.DefaultContentTypeRestTemplateInterceptor;
import mykytka235.ms.report.integration.properties.RestTemplateCommunicationProperties;
import mykytka235.ms.report.constants.ServiceName;
import mykytka235.ms.report.integration.handler.RestTemplateErrorHandler;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RestTemplateFactory {

    public static RestTemplate buildInnerServiceRestTemplate(RestTemplateBuilder builder, ServiceName serviceName,
                                                             RestTemplateCommunicationProperties properties) {
        builder = builder.errorHandler(new RestTemplateErrorHandler(serviceName))
                .interceptors(new DefaultContentTypeRestTemplateInterceptor(), new CorrelationIdRestTemplateInterceptor())
                .rootUri(properties.getUri())
                .setConnectTimeout(Duration.ofMillis(properties.getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.getReadTimeout()));
        return builder.build();
    }
}
