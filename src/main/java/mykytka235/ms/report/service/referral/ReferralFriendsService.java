package mykytka235.ms.report.service.referral;

import mykytka235.ms.report.constants.ExportFileExtension;
import org.springframework.http.ResponseEntity;

public interface ReferralFriendsService {

    ResponseEntity<byte[]> export(String customerId, ExportFileExtension extension);

    void sendOnEmail(String customerId, String locale, ExportFileExtension extension);

}
