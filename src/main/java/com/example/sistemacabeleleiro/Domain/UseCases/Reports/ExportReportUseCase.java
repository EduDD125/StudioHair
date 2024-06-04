package com.example.sistemacabeleleiro.Domain.UseCases.Reports;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ExportReportUseCase {

    private GenerateReportUseCase generateReportUseCase;

    public ExportReportUseCase(GenerateReportUseCase generateReportUseCase) {
        this.generateReportUseCase = generateReportUseCase;
    }
    public void exportSchedules(String fileName) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters();
        generatePDF(fileName, schedules);
    }

    public void exportSchedules(String fileName, LocalDate startDate, LocalDate endDate) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters(startDate, endDate);
        generatePDF(fileName, schedules);
    }

    public void exportSchedules(String fileName, Integer employeeId) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters(employeeId);
        generatePDF(fileName, schedules);
    }

    public void exportSchedules(String fileName, LocalDate startDate, LocalDate endDate, Integer employeeId) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters(startDate, endDate, employeeId);
        generatePDF(fileName, schedules);
    }

    public void exportSchedules(String fileName, LocalDate startDate) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters(startDate);
        generatePDF(fileName, schedules);
    }

    public void exportSchedules(String fileName, LocalDate endDate, boolean isEndDate) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters(endDate, isEndDate);
        generatePDF(fileName, schedules);
    }

    public void exportSchedules(String fileName, LocalDate startDate, Integer employeeId) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters(startDate, employeeId);
        generatePDF(fileName, schedules);
    }

    public void exportSchedules(String fileName, LocalDate endDate, boolean isEndDate, Integer employeeId) {
        List<Scheduling> schedules = generateReportUseCase.findSchedulesByFilters(endDate, isEndDate, employeeId);
        generatePDF(fileName, schedules);
    }

    private void generatePDF(String fileName, List<Scheduling> schedules) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 700);

                for (Scheduling scheduling : schedules) {
                    contentStream.showText("Date: " + scheduling.getDataRealizacao().toString() +
                            " | Employee: " + scheduling.getEmployee().getName() +
                            " | Service: " + scheduling.getService().getName() +
                            " | Client: " + scheduling.getClient().getName() +
                            " | Value: " + scheduling.getService().getValueOfService().toString());
                    contentStream.newLineAtOffset(0, -50); // Move the cursor down for the next line
                }

                contentStream.endText();
            }

            document.save("src/main/resources/com/example/sistemacabeleleiro/reports/" + fileName + ".pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportSampleReport(String fileName) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("This is a sample PDF report.");
                contentStream.endText();
            }

            document.save("src/main/resources/com/example/sistemacabeleleiro/reports/" + fileName + ".pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
