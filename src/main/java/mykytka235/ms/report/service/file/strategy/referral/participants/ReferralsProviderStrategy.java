package mykytka235.ms.report.service.file.strategy.referral.participants;

import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.service.file.FileGenerationStrategy;
import mykytka235.ms.report.service.file.ReportProviderStrategy;
import mykytka235.ms.report.web.model.referral.CustomerReferralDto;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ReferralsProviderStrategy implements ReportProviderStrategy<CustomerReferralDto> {

    private final Map<ExportFileExtension, FileGenerationStrategy<CustomerReferralDto>> strategyMap;

    @Override
    public ByteArrayInputStream provideStrategy(ExportFileExtension extension,
                                                List<CustomerReferralDto> resourceList) {
        return strategyMap.get(extension).load(resourceList);
    }

}
