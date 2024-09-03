package mykytka235.ms.report.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExportFileExtension {
    PDF(".pdf"),
    XLS(".xls"),
    CSV(".csv");

    private final String expansion;
}
