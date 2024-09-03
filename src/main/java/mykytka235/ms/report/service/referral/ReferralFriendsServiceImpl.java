package mykytka235.ms.report.service.referral;

import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.constants.ExportFileType;
import mykytka235.ms.report.db.model.LastExportData;
import mykytka235.ms.report.integration.rest.MyAccountCommunicationService;
import mykytka235.ms.report.integration.rest.NotificationCommunicationService;
import mykytka235.ms.report.service.export.LastExportDataService;
import mykytka235.ms.report.service.file.ReportProviderStrategy;
import mykytka235.ms.report.web.model.referral.CustomerReferralDto;
import mykytka235.ms.report.web.model.referral.CustomerReferralDtoPaginated;
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
public class ReferralFriendsServiceImpl implements ReferralFriendsService {

    private static final ExportFileType SERVICE_EXPORT_FILE_TYPE = ExportFileType.REFERRAL_FRIENDS;

    private final MyAccountCommunicationService myAccountCommunicationService;
    private final NotificationCommunicationService notificationCommunicationService;
    private final LastExportDataService lastExportDataService;
    private final ReportProviderStrategy<CustomerReferralDto> referralParticipantsProviderStrategy;

    @Value("${pagination.size.referral-friends}")
    private Integer referralBonusesPageSize;

    @Override
    public ResponseEntity<byte[]> export(String customerId, ExportFileExtension extension) {
        LastExportData historyRecord = lastExportDataService.getCustomerExportDataIfExportAllowed(customerId, SERVICE_EXPORT_FILE_TYPE);
        List<CustomerReferralDto> list = getReferrals(customerId);
        ByteArrayInputStream byteArray = referralParticipantsProviderStrategy.provideStrategy(extension, list);
        lastExportDataService.updateHistoryRecordForOneType(historyRecord, SERVICE_EXPORT_FILE_TYPE);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
                        SERVICE_EXPORT_FILE_TYPE.getFileName() + extension.getExpansion())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArray.readAllBytes());
    }

    @Override
    public void sendOnEmail(String customerId, String locale, ExportFileExtension extension) {
        LastExportData historyRecord = lastExportDataService.getCustomerExportDataIfExportAllowed(customerId, SERVICE_EXPORT_FILE_TYPE);
        List<CustomerReferralDto> list = getReferrals(customerId);

        notificationCommunicationService.sendReferralBonusesReport(
                customerId,
                customerId,
                locale,
                SERVICE_EXPORT_FILE_TYPE.getFileName() + extension.getExpansion(),
                referralParticipantsProviderStrategy.provideStrategy(extension, list).readAllBytes()
        );
        lastExportDataService.updateHistoryRecordForOneType(historyRecord, SERVICE_EXPORT_FILE_TYPE);

    }

    private List<CustomerReferralDto> getReferrals(String customerId) {
        int page = 0;
        CustomerReferralDtoPaginated referrals = myAccountCommunicationService.getAllCustomerReferralByCustomerId(customerId, page, referralBonusesPageSize);

        if (referrals.getTotalItems() == null) {
            referrals.setTotalItems(0L);
        }

        List<CustomerReferralDto> response = new ArrayList<>(referrals.getList());
        for (int items = referralBonusesPageSize; items < referrals.getTotalItems(); items += referralBonusesPageSize) {
            referrals = myAccountCommunicationService.getAllCustomerReferralByCustomerId(customerId, ++page, referralBonusesPageSize);
            response.addAll(referrals.getList());
        }

        return response;
    }

}
