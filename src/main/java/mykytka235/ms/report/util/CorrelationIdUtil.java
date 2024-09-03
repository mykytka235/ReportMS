package mykytka235.ms.report.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.util.UUID;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CorrelationIdUtil {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static final String SLF4J_KEY = "CORRELATION.ID";

    public static void setId(String correlationId) {
        MDC.put(SLF4J_KEY, correlationId);
    }

    public static String getId() {
        return MDC.get(SLF4J_KEY);
    }

    public static String generateId() {
        String correlationId = uuidSupplier.get();
        MDC.put(SLF4J_KEY, correlationId);
        return correlationId;
    }

    private static Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();
}
