package mykytka235.ms.report.service.file;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface FileGenerationStrategy<T> {
    ByteArrayInputStream load(List<T> resourceList);
}
