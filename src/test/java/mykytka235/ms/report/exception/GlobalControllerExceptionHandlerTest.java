package mykytka235.ms.report.exception;

import mykytka235.ms.report.configuration.advice.GlobalControllerExceptionHandler;
import mykytka235.ms.report.integration.model.ErrorInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalControllerExceptionHandlerTest {

    @InjectMocks
    private GlobalControllerExceptionHandler objectUnderTest;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(objectUnderTest, "SERVICE_EXCEPTION_CODE", 12);
    }

    @Test
    void shouldHandleInnerServiceException() {
        InnerServiceException exception = new InnerServiceException("test message");
        ResponseEntity<ErrorInfoDto> entity = objectUnderTest.handleBaseException(exception, new MockHttpServletRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        assertEquals(ExceptionCode.INTERNAL_SERVICE_EXCEPTION.getMessage(), entity.getBody().getMessages().get(0));
        assertEquals(ExceptionCode.INTERNAL_SERVICE_EXCEPTION.getCode() * 100 + 12, entity.getBody().getCode().intValue());
    }

    @Test
    void shouldHandleRuntimeException() {
        IllegalArgumentException exception = new IllegalArgumentException("parameter is not valid");
        ResponseEntity<ErrorInfoDto> entity = objectUnderTest.handleRuntimeException(exception, new MockHttpServletRequest());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        assertEquals(ExceptionCode.UNKNOWN_EXCEPTION.getMessage(), entity.getBody().getMessages().get(0));
        assertEquals(ExceptionCode.UNKNOWN_EXCEPTION.getCode() * 100 + 12, entity.getBody().getCode().intValue());
    }
}
