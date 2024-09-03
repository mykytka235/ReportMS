package mykytka235.ms.report.service.export;

import mykytka235.ms.report.constants.ExportFileType;
import mykytka235.ms.report.db.model.LastExportData;

public interface LastExportDataService {
    LastExportData getCustomerExportDataIfExportAllowed(String customerId, ExportFileType type);

    void updateHistoryRecordForOneType(LastExportData historyRecord, ExportFileType type);
}
