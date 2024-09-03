package mykytka235.ms.report.integration.handler;

import mykytka235.ms.report.exception.InnerServiceException;
import mykytka235.ms.report.integration.model.ErrorInfoDto;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public abstract class BasicResponseErrorHandler extends DefaultResponseErrorHandler {

    private static final String PROVIDER_ERROR_MESSAGE_TEMPLATE = "Server error [%s]: %s";

    abstract String getServiceName();

    abstract void handleError(ErrorInfoDto errorInfoDto);

    protected MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        Optional<ErrorInfoDto> errorInfoDtoOptional = fetchErrorInfoDto(response);
        errorInfoDtoOptional.ifPresent(errorInfoDto -> {
            if (!CollectionUtils.isEmpty(errorInfoDto.getMessages())) {
                int code = formatExceptionCode(errorInfoDto.getCode());
                errorInfoDto.setCode(code);
                handleError(errorInfoDto);
            }
        });

        throwProviderException(response, statusCode);
    }

    private int formatExceptionCode(int code) {
        String codeString = String.valueOf(code);
        return codeString.length() > 5 ? Integer.parseInt(codeString.substring(0, 5)) : code;
    }

    private void throwProviderException(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        String errorMsg = getErrorMessage(response, statusCode);
        throw new InnerServiceException(String.format(PROVIDER_ERROR_MESSAGE_TEMPLATE, getServiceName(), errorMsg));
    }

    private String getErrorMessage(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        return statusCode + " " + IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
    }

    private Optional<ErrorInfoDto> fetchErrorInfoDto(ClientHttpResponse response) {
        try {
            return Optional.of((ErrorInfoDto) converter.read(ErrorInfoDto.class, response));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
