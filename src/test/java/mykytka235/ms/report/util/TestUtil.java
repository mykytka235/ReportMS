package mykytka235.ms.report.util;

import mykytka235.ms.report.constants.CurrencyIso;
import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.constants.ExportFileType;
import mykytka235.ms.report.db.model.LastExportData;
import mykytka235.ms.report.web.model.ResponsePageDto;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestUtil {

    private static final String BASE_URL_REFERRAL_BONUSES_REPORT_EXPORT = "/api/referral/generate-file/bonuses";
    private static final String BASE_URL_REFERRAL_BONUSES_REPORT_SEND_ON_EMAIL = "/api/referral/email/bonuses";

    public static final String CSV_REFERRAL_BONUSES_CONTENT_DISPOSITION_VALUE = "attachment;filename=Referral bonuses.csv";
    public static final String PDF_REFERRAL_BONUSES_CONTENT_DISPOSITION_VALUE = "attachment;filename=Referral bonuses.pdf";
    public static final String XLS_REFERRAL_BONUSES_CONTENT_DISPOSITION_VALUE = "attachment;filename=Referral bonuses.xls";

    public static final String CSV_REFERRAL_SIZE_DEFAULT = "43757272656e63792049736f2c416d6f756e742c4461746520696e205554432074696d657374616d700d0a4332342c302e3032352c313634303236363133383030300d0a4332342c302e3032352c313634303236363133383030300d0a";

    public static final String USER_EMAIL = "some@email.com";
    public static final CurrencyIso CURRENCY_ISO_DEFAULT = CurrencyIso.C24;
    public static final Double REFERRAL_BONUSES_AMOUNT_DEFAULT = 0.025D;
    public static final Long CURRENT_TIME_MILLIS_DEFAULT = 1640266138000L;
    public static final Long LAST_TIME_EXPORT_MILLIS_DEFAULT = 1479250540110L;
    public static final Integer PAGE_DEFAULT = 0;
    public static final Integer SIZE_DEFAULT = 100;


    public static URI buildReferralBonusesExportUriWithParam(String email, ExportFileExtension extension, CurrencyIso iso,
                                                             Long fromDate, Long beforeDate) {
        return UriComponentsBuilder
                .fromPath(BASE_URL_REFERRAL_BONUSES_REPORT_EXPORT)
                .queryParam("customerId", email)
                .queryParam("extension", extension)
                .queryParam("iso", iso)
                .queryParam("fromDate", fromDate)
                .queryParam("beforeDate", beforeDate)
                .build()
                .encode()
                .toUri();
    }

    public static URI buildReferralBonusesSendOnEmailUriWithParam(String email, String locale, ExportFileExtension extension, CurrencyIso iso,
                                                                  Long fromDate, Long beforeDate) {
        return UriComponentsBuilder
                .fromPath(BASE_URL_REFERRAL_BONUSES_REPORT_SEND_ON_EMAIL)
                .queryParam("customerId", email)
                .queryParam("extension", extension)
                .queryParam("to", email)
                .queryParam("locale", locale)
                .queryParam("iso", iso)
                .queryParam("fromDate", fromDate)
                .queryParam("beforeDate", beforeDate)
                .build()
                .encode()
                .toUri();
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static MockMultipartFile getFile(String hexString) {
        return new MockMultipartFile("Referral bonuses", hexStringToByteArray(hexString));
    }

    public static Map<ExportFileType, Long> createLastTimeExportMap() {
        Map<ExportFileType, Long> lastTimeExportMap = new EnumMap<>(ExportFileType.class);
        lastTimeExportMap.put(ExportFileType.REFERRAL_BONUSES, LAST_TIME_EXPORT_MILLIS_DEFAULT);
        return lastTimeExportMap;
    }

    public static Optional<LastExportData> createLastExportData() {
        return Optional.of(LastExportData.builder()
                .customerId(USER_EMAIL)
                .lastTimeExportMap(createLastTimeExportMap())
                .lastTimeExport(LAST_TIME_EXPORT_MILLIS_DEFAULT)
                .build());
    }

    public static ReferralBonusesDtoResponse createReferralBonusesResponseDto() {
        ReferralBonusesDtoResponse referralBonuses = new ReferralBonusesDtoResponse();
        referralBonuses.setEmail(USER_EMAIL);
        referralBonuses.setProvidedReferralId(null);
        referralBonuses.setCurrencyIso(CURRENCY_ISO_DEFAULT);
        referralBonuses.setAmount(REFERRAL_BONUSES_AMOUNT_DEFAULT);
        referralBonuses.setDate(CURRENT_TIME_MILLIS_DEFAULT);
        return referralBonuses;
    }

    public static ResponsePageDto<ReferralBonusesDtoResponse> createReferralBonusesResponsePageDto() {
        List<ReferralBonusesDtoResponse> responseBody = Arrays.asList(createReferralBonusesResponseDto(), createReferralBonusesResponseDto());
        return ResponsePageDto.createOf(responseBody, 1, responseBody.size());
    }

}
