package mykytka235.ms.report.web.contoller;

import mykytka235.ms.report.constants.CurrencyIso;
import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.service.referral.ReferralBonusesExportFileService;
import mykytka235.ms.report.service.referral.ReferralFriendsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/referral")
public class ReferralController {

    private static final List<ExportFileExtension> AVAILABLE_EXTENSIONS = Arrays.asList(ExportFileExtension.CSV, ExportFileExtension.PDF, ExportFileExtension.XLS);

    private final ReferralFriendsService referralFriendsService;
    private final ReferralBonusesExportFileService referralBonusesExportFileService;

    @GetMapping("/export-types")
    public List<ExportFileExtension> getExportTypes() {
        return AVAILABLE_EXTENSIONS;
    }

    @GetMapping("/generate-file/bonuses")
    public ResponseEntity<byte[]> getCustomerReferralBonusesFile(@RequestParam String customerId,
                                                                 @RequestParam ExportFileExtension extension,
                                                                 @RequestParam(required = false) CurrencyIso iso,
                                                                 @RequestParam(required = false) Long fromDate,
                                                                 @RequestParam(required = false) Long beforeDate) {

        log.info("Request to export all referral bonuses by customer id {}", customerId);
        ResponseEntity<byte[]> response = referralBonusesExportFileService.export(customerId, extension, iso, fromDate, beforeDate);
        log.info("Request to export all referral bonuses processed.");

        return response;
    }

    @PostMapping("/email/bonuses")
    public void sendReferralBonusesToEmail(@RequestParam String customerId,
                                           @RequestParam(defaultValue = "en", required = false) String locale,
                                           @RequestParam ExportFileExtension extension,
                                           @RequestParam(required = false) CurrencyIso iso,
                                           @RequestParam(required = false) Long fromDate,
                                           @RequestParam(required = false) Long beforeDate) {
        log.info("Request to send all referral bonuses by customer id {}", customerId);
        referralBonusesExportFileService.sendOnEmail(customerId, locale, extension,
                iso, fromDate, beforeDate);
        log.info("Request to send all referral bonuses processed.");

    }

    @GetMapping("/generate-file/participants")
    public ResponseEntity<byte[]> getCustomerReferrals(@RequestParam String customerId,
                                                       @RequestParam ExportFileExtension extension) {

        log.info("Request to export all referral bonuses by customer id {}", customerId);
        ResponseEntity<byte[]> response = referralFriendsService.export(customerId, extension);
        log.info("Request to export all referral bonuses processed.");

        return response;
    }

    @PostMapping("/email/participants")
    public void sendReferralsToEmail(@RequestParam String customerId,
                                     @RequestParam(defaultValue = "en", required = false) String locale,
                                     @RequestParam ExportFileExtension extension) {
        log.info("Request to send all referral bonuses by customer id {}", customerId);
        referralFriendsService.sendOnEmail(customerId, locale, extension);
        log.info("Request to send all referral bonuses processed.");

    }

}
