package com.example.sistemacabeleleiro.Domain.UseCases.Reports;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class ExportSchedules {

    public void exportSchedules(String fileName) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("This is a sample PFD report.");
                contentStream.endText();
            }

            document.save("src/main/resources/com/example/sistemacabeleleiro/reports/" + fileName + ".pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExportSchedules generator = new ExportSchedules();
        generator.exportSchedules("sample_report");
    }
}
