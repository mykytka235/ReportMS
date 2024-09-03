package mykytka235.ms.report.service.file.strategy.referral.bonuses;

import mykytka235.ms.report.exception.InnerServiceException;
import mykytka235.ms.report.service.file.FileGenerationStrategy;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service(value = "csvReferralBonusesStrategy")
public class ReferralsBonusesCsvFileGenerationStrategy implements FileGenerationStrategy<ReferralBonusesDtoResponse> {

    private static final String[] HEADERS = {"Currency Iso", "Amount", "Date"};
    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS);
    private final DecimalFormat df = new DecimalFormat("0");

    @Override
    public ByteArrayInputStream load(List<ReferralBonusesDtoResponse> resourceList) {
        df.setMaximumFractionDigits(24);
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {
            for (ReferralBonusesDtoResponse referralBonus : resourceList) {
                List<String> data = Arrays.asList(
                        referralBonus.getCurrencyIso().toString(),
                        df.format(referralBonus.getAmount()),
                        new Date(referralBonus.getDate()).toString()
                );
                printer.printRecord(data);
            }

            printer.flush();
            return new ByteArrayInputStream(stream.toByteArray());
        } catch (final IOException e) {
            throw new InnerServiceException("Csv writing error: " + e.getMessage());
        }
    }

}
