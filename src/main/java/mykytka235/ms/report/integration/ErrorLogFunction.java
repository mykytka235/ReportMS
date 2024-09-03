package mykytka235.ms.report.integration;

@FunctionalInterface
public interface ErrorLogFunction {

    void log(Exception e);

}
