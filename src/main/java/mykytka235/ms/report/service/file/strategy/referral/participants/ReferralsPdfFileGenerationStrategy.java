package mykytka235.ms.report.service.file.strategy.referral.participants;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import mykytka235.ms.report.exception.InnerServiceException;
import mykytka235.ms.report.service.file.FileGenerationStrategy;
import mykytka235.ms.report.web.model.referral.CustomerReferralDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.lowagie.text.Element.ALIGN_CENTER;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.PageSize.A4;
import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

@Service(value = "pdfReferralsStrategy")
public class ReferralsPdfFileGenerationStrategy implements FileGenerationStrategy<CustomerReferralDto> {

    private static final String[] HEADERS = {"Referral emails", "Registration date"};

    @Override
    public ByteArrayInputStream load(List<CustomerReferralDto> bonuses) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            Document document = new Document(A4);
            PdfWriter.getInstance(document, stream);

            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);

            Paragraph p = new Paragraph("Referral Participants", font);
            p.setAlignment(ALIGN_CENTER);

            document.add(p);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80f);
            table.setWidths(new float[]{3.0f, 3.0f});
            table.setSpacingBefore(10);

            writeTableHeader(table);
            writeTableData(table, bonuses);

            document.add(table);
            document.close();

            return new ByteArrayInputStream(stream.toByteArray());
        } catch (IOException e) {
            throw new InnerServiceException("Pdf writing error: " + e.getMessage());
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BLUE);
        cell.setPadding(5);
        cell.setHorizontalAlignment(ALIGN_CENTER);
        cell.setVerticalAlignment(ALIGN_MIDDLE);

        Font font = FontFactory.getFont(FontFactory.HELVETICA, Font.UNDEFINED, Font.BOLD);
        font.setColor(WHITE);

        cell.setPhrase(new Phrase(HEADERS[0], font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(HEADERS[1], font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table, List<CustomerReferralDto> bonuses) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(4);
        cell.setHorizontalAlignment(ALIGN_CENTER);
        cell.setVerticalAlignment(ALIGN_MIDDLE);

        for (CustomerReferralDto bonus : bonuses) {
            cell.setPhrase(new Phrase(bonus.getReferralId()));
            table.addCell(bonus.getReferralId());
            cell.setPhrase(new Phrase(new Date(bonus.getDate()).toString()));
            table.addCell(cell);
        }
    }
}