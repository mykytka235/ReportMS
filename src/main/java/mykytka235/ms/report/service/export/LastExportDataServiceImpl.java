package mykytka235.ms.report.service.export;

import mykytka235.ms.report.constants.ExportFileType;
import mykytka235.ms.report.db.model.LastExportData;
import mykytka235.ms.report.db.repository.LastExportDataRepository;
import mykytka235.ms.report.exception.FileExportTimeLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LastExportDataServiceImpl implements LastExportDataService {

    private final Clock utcClock;
    private final LastExportDataRepository lastExportDataRepository;

    @Value("${export-file.timeoutInMilliseconds}")
    private Long exportFileTimeout;

    @Override
    public LastExportData getCustomerExportDataIfExportAllowed(String customerId, ExportFileType type) {
        LastExportData historyRecord = lastExportDataRepository
                .findByCustomerId(customerId)
                .orElseGet(() -> LastExportData.builder()
                        .customerId(customerId)
                        .lastTimeExport(0L)
                        .build());
        historyRecord.getLastTimeExportMap().putIfAbsent(type, 0L);
        long currentTime = utcClock.millis();

        checkIfCustomerAllowedToExportFileOfThisType(historyRecord.getCustomerId(), type, historyRecord.getLastTimeExportMap(), currentTime);

        return historyRecord;
    }

    @Override
    public void updateHistoryRecordForOneType(LastExportData historyRecord, ExportFileType type) {
        long currentTime = utcClock.millis();

        historyRecord.getLastTimeExportMap().put(type, currentTime);
        historyRecord.setLastTimeExport(currentTime);

        lastExportDataRepository.save(historyRecord);
    }

    private void checkIfCustomerAllowedToExportFileOfThisType(String customerId, ExportFileType type,
                                                              Map<ExportFileType, Long> lastTimeExportMap, long currentTime) {
        if (lastTimeExportMap.get(type) + exportFileTimeout > currentTime) {
            throw new FileExportTimeLimitException(String.format("User with email = %s has timeout on export file", customerId));
        }
    }
}
