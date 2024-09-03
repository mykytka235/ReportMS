package mykytka235.ms.report.service.file;

import mykytka235.ms.report.constants.ExportFileExtension;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ReportProviderStrategy<T> {
    ByteArrayInputStream provideStrategy(ExportFileExtension extension,
                                         List<T> dataList);
}
