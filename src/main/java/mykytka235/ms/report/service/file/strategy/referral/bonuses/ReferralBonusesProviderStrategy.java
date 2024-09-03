package mykytka235.ms.report.service.file.strategy.referral.bonuses;

import mykytka235.ms.report.constants.ExportFileExtension;
import mykytka235.ms.report.service.file.FileGenerationStrategy;
import mykytka235.ms.report.service.file.ReportProviderStrategy;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ReferralBonusesProviderStrategy implements ReportProviderStrategy<ReferralBonusesDtoResponse> {

    private final Map<ExportFileExtension, FileGenerationStrategy<ReferralBonusesDtoResponse>> strategyMap;

    @Override
    public ByteArrayInputStream provideStrategy(ExportFileExtension extension,
                                                List<ReferralBonusesDtoResponse> resourceList) {
        return strategyMap.get(extension).load(resourceList);
    }

}
