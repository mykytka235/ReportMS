package mykytka235.ms.report.service.file.strategy.referral.participants;

import mykytka235.ms.report.exception.InnerServiceException;
import mykytka235.ms.report.service.file.FileGenerationStrategy;
import mykytka235.ms.report.web.model.referral.CustomerReferralDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service(value = "csvReferralsStrategy")
public class ReferralsCsvFileGenerationStrategy implements FileGenerationStrategy<CustomerReferralDto> {

    private static final String[] HEADERS = {"Referral email", "Registration date"};
    private static final CSVFormat FORMAT = CSVFormat.DEFAULT.withHeader(HEADERS);

    @Override
    public ByteArrayInputStream load(List<CustomerReferralDto> resourceList) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {
            for (CustomerReferralDto referralBonus : resourceList) {
                List<String> data = Arrays.asList(
                        referralBonus.getReferralId(),
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
