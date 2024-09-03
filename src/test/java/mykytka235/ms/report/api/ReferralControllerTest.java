package mykytka235.ms.report.api;

import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.util.TestUtil;
import mykytka235.ms.report.web.model.ResponsePageDto;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static mykytka235.ms.report.util.TestUtil.CSV_REFERRAL_BONUSES_CONTENT_DISPOSITION_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReferralControllerTest extends BaseApiTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        when(lastExportDataRepository.findByCustomerId(any())).thenReturn(TestUtil.createLastExportData());
    }

    @Test
    void shouldReturnReportOfReferralBonusesAsCSVFile() throws Exception {
        ResponsePageDto<ReferralBonusesDtoResponse> referralBonusesList = TestUtil.createReferralBonusesResponsePageDto();
        mockResponseWithSuccess(
                myAccountServiceServer,
                HttpMethod.GET,
                myAccountUriBuilder.getReferralBonusesByCustomerIdAndMultipleCriteriaUri(TestUtil.USER_EMAIL, null,
                        null, null, TestUtil.PAGE_DEFAULT, TestUtil.SIZE_DEFAULT).toString(),
                referralBonusesList);

        byte[] response = performGetInteraction(TestUtil.buildReferralBonusesExportUriWithParam(TestUtil.USER_EMAIL,
                ExportFileExtension.CSV, null, null, null))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, CSV_REFERRAL_BONUSES_CONTENT_DISPOSITION_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        verify(lastExportDataRepository, times(1)).findByCustomerId(TestUtil.USER_EMAIL);
        verify(lastExportDataRepository, times(1)).save(any());
        verify(utcClock, times(2)).millis();
    }

    @Test
    void shouldSendReportOnEmailOfReferralBonusesAsCsvFile() throws Exception {
        ResponsePageDto<ReferralBonusesDtoResponse> referralBonusesList = TestUtil.createReferralBonusesResponsePageDto();

        mockResponseWithSuccess(
                myAccountServiceServer,
                HttpMethod.GET,
                myAccountUriBuilder.getReferralBonusesByCustomerIdAndMultipleCriteriaUri(TestUtil.USER_EMAIL, null,
                        null, null, TestUtil.PAGE_DEFAULT, TestUtil.SIZE_DEFAULT).toString(),
                referralBonusesList);
        mockResponseWithSuccess(
                notificationServiceServer,
                HttpMethod.POST,
                notificationUriBuilder.getSendReportUri(TestUtil.USER_EMAIL, TestUtil.USER_EMAIL, "en", "Referral bonuses.csv").toString(),
                null);

        performPostInteraction(
                TestUtil.buildReferralBonusesSendOnEmailUriWithParam(TestUtil.USER_EMAIL, null,
                        ExportFileExtension.CSV, null, null, null), null)
                .andExpect(status().isOk());

        verify(lastExportDataRepository, times(1)).findByCustomerId(TestUtil.USER_EMAIL);
        verify(lastExportDataRepository, times(1)).save(any());
        verify(utcClock, times(2)).millis();
    }

}
