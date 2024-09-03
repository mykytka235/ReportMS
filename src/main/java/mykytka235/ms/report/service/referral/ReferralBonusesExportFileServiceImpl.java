package mykytka235.ms.report.service.referral;

import mykytka235.ms.report.constants.CurrencyIso;
import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.constants.ExportFileType;
import mykytka235.ms.report.db.model.LastExportData;
import mykytka235.ms.report.integration.rest.MyAccountCommunicationService;
import mykytka235.ms.report.integration.rest.NotificationCommunicationService;
import mykytka235.ms.report.service.export.LastExportDataService;
import mykytka235.ms.report.service.file.ReportProviderStrategy;
import mykytka235.ms.report.web.model.ResponsePageDto;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReferralBonusesExportFileServiceImpl implements ReferralBonusesExportFileService {

    private static final ExportFileType SERVICE_EXPORT_FILE_TYPE = ExportFileType.REFERRAL_BONUSES;

    private final MyAccountCommunicationService myAccountCommunicationService;
    private final NotificationCommunicationService notificationCommunicationService;
    private final LastExportDataService lastExportDataService;
    private final ReportProviderStrategy<ReferralBonusesDtoResponse> referralBonusesProviderStrategy;

    @Value("${pagination.size.referral-bonuses}")
    private Integer referralBonusesPageSize;

    @Override
    public ResponseEntity<byte[]> export(String customerId, ExportFileExtension extension,
                                         CurrencyIso iso, Long fromDate, Long beforeDate) {
        LastExportData historyRecord = lastExportDataService.getCustomerExportDataIfExportAllowed(customerId, SERVICE_EXPORT_FILE_TYPE);
        List<ReferralBonusesDtoResponse> list = getReferralBonusesByCriteria(customerId, iso, fromDate, beforeDate);
        ByteArrayInputStream byteArray = referralBonusesProviderStrategy.provideStrategy(extension, list);
        lastExportDataService.updateHistoryRecordForOneType(historyRecord, SERVICE_EXPORT_FILE_TYPE);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
                        ExportFileType.REFERRAL_BONUSES.getFileName() + extension.getExpansion())
                .body(byteArray.readAllBytes());
    }

    @Override
    public void sendOnEmail(String customerId, String locale, ExportFileExtension extension,
                            CurrencyIso iso, Long fromDate, Long beforeDate) {
        LastExportData historyRecord = lastExportDataService.getCustomerExportDataIfExportAllowed(customerId, SERVICE_EXPORT_FILE_TYPE);

        List<ReferralBonusesDtoResponse> list = getReferralBonusesByCriteria(customerId, iso, fromDate, beforeDate);

        notificationCommunicationService.sendReferralBonusesReport(customerId, customerId, locale,
                SERVICE_EXPORT_FILE_TYPE.getFileName() + extension.getExpansion(), referralBonusesProviderStrategy.provideStrategy(extension, list).readAllBytes());
        lastExportDataService.updateHistoryRecordForOneType(historyRecord, SERVICE_EXPORT_FILE_TYPE);
    }

    private List<ReferralBonusesDtoResponse> getReferralBonusesByCriteria(String customerId, CurrencyIso iso, Long from, Long before) {
        int page = 0;
        ResponsePageDto<ReferralBonusesDtoResponse> referralBonuses = myAccountCommunicationService.getReferralBonusesByCustomerIdAndMultipleCriteria(customerId,
                iso, from, before, page, referralBonusesPageSize);

        List<ReferralBonusesDtoResponse> response = new ArrayList<>(referralBonuses.getBody());
        for (page = 1; page < referralBonuses.getTotalPages(); page++) {
            referralBonuses = myAccountCommunicationService.getReferralBonusesByCustomerIdAndMultipleCriteria(customerId,
                    iso, from, before, page, referralBonusesPageSize);
            response.addAll(referralBonuses.getBody());
        }

        return response;
    }

}
