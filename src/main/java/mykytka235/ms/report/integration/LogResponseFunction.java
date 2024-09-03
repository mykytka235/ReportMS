package mykytka235.ms.report.integration;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface LogResponseFunction<T> {

    void log(ResponseEntity<T> response);

}
