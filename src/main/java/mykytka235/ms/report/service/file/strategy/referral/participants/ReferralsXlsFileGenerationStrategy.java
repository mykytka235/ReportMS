package mykytka235.ms.report.service.file.strategy.referral.participants;

import mykytka235.ms.report.exception.InnerServiceException;
import mykytka235.ms.report.service.file.FileGenerationStrategy;
import mykytka235.ms.report.web.model.referral.CustomerReferralDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service(value = "xlsReferralsStrategy")
public class ReferralsXlsFileGenerationStrategy implements FileGenerationStrategy<CustomerReferralDto> {

    private static final String[] HEADERS = {"Referral email", "Registration date"};

    private void writeHeaderLine(XSSFWorkbook workbook, Sheet sheet) {
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createAndFillCell(row, 0, HEADERS[0], style, sheet);
        createAndFillCell(row, 1, HEADERS[1], style, sheet);

    }

    private void createAndFillCell(Row row, int columnCount, Object value, CellStyle style, Sheet sheet) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(XSSFWorkbook workbook, Sheet sheet, List<CustomerReferralDto> bonuses) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (CustomerReferralDto bonus : bonuses) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createAndFillCell(row, columnCount++, bonus.getReferralId(), style, sheet);
            createAndFillCell(row, columnCount, new Date(bonus.getDate()).toString(), style, sheet);

        }
    }

    @Override
    public ByteArrayInputStream load(List<CustomerReferralDto> bonuses) {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Referral bonuses");
            writeHeaderLine(workbook, sheet);
            writeDataLines(workbook, sheet, bonuses);

            workbook.write(stream);

            return new ByteArrayInputStream(stream.toByteArray());
        } catch (IOException e) {
            throw new InnerServiceException("XLS writing error: " + e.getMessage());
        }

    }
}
