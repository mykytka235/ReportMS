package mykytka235.ms.report.configuration;

import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.service.file.FileGenerationStrategy;
import mykytka235.ms.report.service.file.ReportProviderStrategy;
import mykytka235.ms.report.service.file.strategy.referral.bonuses.ReferralBonusesProviderStrategy;
import mykytka235.ms.report.service.file.strategy.referral.bonuses.ReferralsBonusesCsvFileGenerationStrategy;
import mykytka235.ms.report.service.file.strategy.referral.bonuses.ReferralsBonusesPdfFileGenerationStrategy;
import mykytka235.ms.report.service.file.strategy.referral.bonuses.ReferralsBonusesXlsFileGenerationStrategy;
import mykytka235.ms.report.service.file.strategy.referral.participants.ReferralsCsvFileGenerationStrategy;
import mykytka235.ms.report.service.file.strategy.referral.participants.ReferralsPdfFileGenerationStrategy;
import mykytka235.ms.report.service.file.strategy.referral.participants.ReferralsProviderStrategy;
import mykytka235.ms.report.service.file.strategy.referral.participants.ReferralsXlsFileGenerationStrategy;
import mykytka235.ms.report.web.model.referral.CustomerReferralDto;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneOffset;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class ReportConfig {

    @Bean
    public Clock utcClock() {
        return Clock.system(ZoneOffset.UTC);
    }

    @Bean
    public ReportProviderStrategy<ReferralBonusesDtoResponse> referralBonusesProvider(ReferralsBonusesCsvFileGenerationStrategy csvReferralBonusesStrategy,
                                                                                      ReferralsBonusesPdfFileGenerationStrategy pdfReferralBonusesStrategy,
                                                                                      ReferralsBonusesXlsFileGenerationStrategy xlsReferralBonusesStrategy) {
        Map<ExportFileExtension, FileGenerationStrategy<ReferralBonusesDtoResponse>> strategyMap = new EnumMap<>(ExportFileExtension.class);

        strategyMap.put(ExportFileExtension.CSV, csvReferralBonusesStrategy);
        strategyMap.put(ExportFileExtension.PDF, pdfReferralBonusesStrategy);
        strategyMap.put(ExportFileExtension.XLS, xlsReferralBonusesStrategy);

        return new ReferralBonusesProviderStrategy(strategyMap);
    }

    @Bean
    public ReportProviderStrategy<CustomerReferralDto> referralsParticipantsProvider(ReferralsCsvFileGenerationStrategy csvReferralsStrategy,
                                                                                     ReferralsPdfFileGenerationStrategy pdfReferralsStrategy,
                                                                                     ReferralsXlsFileGenerationStrategy xlsReferralsStrategy) {
        Map<ExportFileExtension, FileGenerationStrategy<CustomerReferralDto>> strategyMap = new EnumMap<>(ExportFileExtension.class);

        strategyMap.put(ExportFileExtension.CSV, csvReferralsStrategy);
        strategyMap.put(ExportFileExtension.PDF, pdfReferralsStrategy);
        strategyMap.put(ExportFileExtension.XLS, xlsReferralsStrategy);

        return new ReferralsProviderStrategy(strategyMap);
    }

}
