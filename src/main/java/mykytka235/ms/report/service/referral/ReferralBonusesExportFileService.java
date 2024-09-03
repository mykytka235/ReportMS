package mykytka235.ms.report.service.referral;

import mykytka235.ms.report.constants.CurrencyIso;
import mykytka235.ms.report.constants.ExportFileExtension;
import org.springframework.http.ResponseEntity;

public interface ReferralBonusesExportFileService {

    ResponseEntity<byte[]> export(String customerId, ExportFileExtension extension, CurrencyIso iso, Long fromDate,
                                  Long beforeDate);

    void sendOnEmail(String customerId, String locale, ExportFileExtension extension, CurrencyIso iso, Long fromDate,
                     Long beforeDate);

}
