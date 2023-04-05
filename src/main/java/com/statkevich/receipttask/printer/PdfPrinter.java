package com.statkevich.receipttask.printer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.dto.ReceiptRow;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

/**
 * Described class implement output of {@link ReceiptDto} in PDF file.
 */
public class PdfPrinter implements Printer {

    //Formatter used to correctly display the time in the receipt
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static void addTime(PdfPTable table) {
        String time = LocalTime.now().format(TIME_FORMATTER);
        PdfPCell cell = new PdfPCell();
        cell.setColspan(6);
        cell.setPhrase(new Phrase("Time: " + time));
        table.addCell(cell);
    }

    private static void addHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(6);
        cell.setPhrase(new Phrase("Super Marker: Clevertec"));
        table.addCell(cell);
    }

    private static void addDate(PdfPTable table) {
        String date = LocalDate.now().toString();
        PdfPCell cell = new PdfPCell();
        cell.setColspan(6);
        cell.setPhrase(new Phrase("Date: " + date));
        table.addCell(cell);
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Quantity", "Description", "Price", "Total", "Sale", "Sale Amount")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table, ReceiptDto receiptDto) {
        List<ReceiptRow> receiptRowList = receiptDto.receiptRow();
        receiptRowList
                .forEach(row -> {
                    table.addCell(String.valueOf(row.quantity()));
                    table.addCell(row.productName());
                    table.addCell(String.valueOf(row.price()));
                    table.addCell(String.valueOf(row.totalRow()));
                    table.addCell(String.valueOf(row.salePercentage()));
                    table.addCell(String.valueOf(row.saleAmount()));
                });
    }

    private static void addFooter(PdfPTable table, BigDecimal total) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(6);
        cell.setPhrase(new Phrase("Total: " + total.toString()));
        table.addCell(cell);
    }

    @Override
    public void print(ReceiptDto receiptDto) {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("receipt.pdf"));
            document.open();
            PdfReader reader = new PdfReader("Clevertec_Template.pdf");
            PdfImportedPage page = writer.getImportedPage(reader, 1);
            PdfContentByte directContent = writer.getDirectContent();
            directContent.addTemplate(page, 0, 0);

            Paragraph paragraph = new Paragraph("\n".repeat(7));
            document.add(paragraph);

            PdfPTable table = new PdfPTable(6);
            addHeader(table);
            addDate(table);
            addTime(table);
            addTableHeader(table);
            addRows(table, receiptDto);
            addFooter(table, receiptDto.total());
            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
