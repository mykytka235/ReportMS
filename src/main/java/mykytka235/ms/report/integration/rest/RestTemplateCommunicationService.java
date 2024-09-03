package mykytka235.ms.report.integration.rest;

import mykytka235.ms.report.exception.InnerServiceException;
import mykytka235.ms.report.integration.ErrorLogFunction;
import mykytka235.ms.report.integration.LogFunction;
import mykytka235.ms.report.integration.LogResponseFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public abstract class RestTemplateCommunicationService {

    private static final String ERROR_WHEN_CONNECTING_TO_REMOTE_RESOURCE = "Error when connecting to remote resource: ";
    private final RestTemplate restTemplate;

    protected <T> T performRequest(URI uri, HttpMethod method, HttpEntity<?> body, Class<T> responseType,
                                   LogFunction preLog, LogResponseFunction<T> postLog, ErrorLogFunction errorLog) {
        preLog.log();
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(uri, method, body, responseType);
            postLog.log(responseEntity);
            return responseEntity.getBody();
        } catch (ResourceAccessException e) {
            errorLog.log(e);
            throw new InnerServiceException(ERROR_WHEN_CONNECTING_TO_REMOTE_RESOURCE + e.getMessage());
        }
    }

    protected <T> T performRequest(URI uri, HttpMethod method, HttpEntity<?> body, Class<T> responseType,
                                   LogFunction preLog, LogFunction postLog, ErrorLogFunction errorLog) {
        preLog.log();
        try {
            T responseBody = restTemplate.exchange(uri, method, body, responseType).getBody();
            postLog.log();
            return responseBody;
        } catch (ResourceAccessException e) {
            errorLog.log(e);
            throw new InnerServiceException(ERROR_WHEN_CONNECTING_TO_REMOTE_RESOURCE + e.getMessage());
        }
    }

    protected <T> T performParametrizedRequest(URI uri, HttpMethod method, HttpEntity<?> body, ParameterizedTypeReference<T> responseType,
                                               LogFunction preLog, LogResponseFunction<T> postLog, ErrorLogFunction errorLog) {
        preLog.log();
        try {
            ResponseEntity<T> response = restTemplate.exchange(uri, method, body, responseType);
            postLog.log(response);
            return response.getBody();
        } catch (ResourceAccessException e) {
            errorLog.log(e);
            throw new InnerServiceException(ERROR_WHEN_CONNECTING_TO_REMOTE_RESOURCE + e.getMessage());
        }
    }

    protected <T> T performParametrizedRequest(URI uri, HttpMethod method, HttpEntity<?> body, ParameterizedTypeReference<T> responseType,
                                               LogFunction preLog, LogFunction postLog, ErrorLogFunction errorLog) {
        preLog.log();
        try {
            T responseBody = restTemplate.exchange(uri, method, body, responseType).getBody();
            postLog.log();
            return responseBody;
        } catch (ResourceAccessException e) {
            errorLog.log(e);
            throw new InnerServiceException(ERROR_WHEN_CONNECTING_TO_REMOTE_RESOURCE + e.getMessage());
        }
    }

    protected HttpHeaders prepareHeaders(String name, String value) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put(name, Collections.singletonList(value));
        return httpHeaders;
    }

}
