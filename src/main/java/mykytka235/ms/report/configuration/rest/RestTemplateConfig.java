package mykytka235.ms.report.configuration.rest;

import mykytka235.ms.report.constants.ServiceName;
import mykytka235.ms.report.integration.properties.MyAccountProperties;
import mykytka235.ms.report.integration.properties.NotificationProperties;
import mykytka235.ms.report.properties.RestTemplateProperties;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager(
            RestTemplateProperties restTemplateProperties) {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(restTemplateProperties.getMaxTotal());
        manager.setDefaultMaxPerRoute(restTemplateProperties.getDefaultMaxPerRoute());
        return manager;
    }

    @Bean
    public RequestConfig requestConfig(RestTemplateProperties restTemplateProperties) {
        return RequestConfig.custom().setSocketTimeout(restTemplateProperties.getSocketTimeout()).build();
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
                                          RequestConfig requestConfig) {
        return HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder(HttpClient httpClient) {
        return new RestTemplateBuilder().requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Bean(name = "myAccountRestTemplate")
    public RestTemplate myAccountRestTemplate(RestTemplateBuilder restTemplateBuilder, MyAccountProperties myAccountProperties) {
        return RestTemplateFactory.buildInnerServiceRestTemplate(restTemplateBuilder, ServiceName.MY_ACCOUNT, myAccountProperties);
    }

    @Bean(name = "notificationRestTemplate")
    public RestTemplate notificationRestTemplate(RestTemplateBuilder restTemplateBuilder, NotificationProperties notificationProperties) {
        return RestTemplateFactory.buildInnerServiceRestTemplate(restTemplateBuilder, ServiceName.NOTIFICATION, notificationProperties);
    }
}