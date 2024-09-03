package mykytka235.ms.report.service.file.strategy.referral.bonuses;

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
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import static com.lowagie.text.Element.ALIGN_CENTER;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.PageSize.A4;
import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

@RequiredArgsConstructor
@Service(value = "pdfReferralBonusesStrategy")
public class ReferralsBonusesPdfFileGenerationStrategy implements FileGenerationStrategy<ReferralBonusesDtoResponse> {

    private static final String[] HEADERS = {"Currency Iso", "Amount", "Date"};

    private final DecimalFormat df = new DecimalFormat("0");

    @Override
    public ByteArrayInputStream load(List<ReferralBonusesDtoResponse> bonuses) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            Document document = new Document(A4);
            PdfWriter.getInstance(document, stream);
            df.setMaximumFractionDigits(24);

            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);

            Paragraph p = new Paragraph("Referral Bonuses", font);
            p.setAlignment(ALIGN_CENTER);

            document.add(p);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100f);
            table.setWidths(new float[]{1.5f, 3.0f, 3.0f});
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

        cell.setPhrase(new Phrase(HEADERS[2], font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<ReferralBonusesDtoResponse> bonuses) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(4);
        cell.setHorizontalAlignment(ALIGN_CENTER);
        cell.setVerticalAlignment(ALIGN_MIDDLE);

        for (ReferralBonusesDtoResponse bonus : bonuses) {
            cell.setPhrase(new Phrase(bonus.getCurrencyIso().toString()));
            table.addCell(cell);
            cell.setPhrase(new Phrase(df.format(bonus.getAmount())));
            table.addCell(cell);
            cell.setPhrase(new Phrase(new Date(bonus.getDate()).toString()));
            table.addCell(cell);
        }
    }
}