package com.example.nhanct.config;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * UserPDFExporter
 *
 * @author NhanCT
 * @date 7/17/2023 5:33 PM
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
public class PDFExporter {

    @Autowired
    private PDFExporterUtils pdfExporterUtil;

    /* HEADER */
    private String titleHeader;
    private List<String> listHeader;
    private List<String> listHeaderContent;

    /* TABLE 1 */
    private int colNum1;
    private List<String> listTableHeader1;
    private List<List<String>> listDataTable1;

    /* TABLE 2 */
    private int colNum2;
    private List<String> listTableHeader2;
    private List<List<String>> listDataTable2;

    /* SIGN */
    private List<String> listSign;

    private static Font fontBlueHeader6 = PDFExporterUtils.fontBlueHeader6();
    private static Font fontBlackHeader1 = PDFExporterUtils.fontBlackHeader1();
    private static Font fontBlackHeader3 = PDFExporterUtils.fontBlackHeader3();
    private static Font fontBlackNormal = PDFExporterUtils.fontBlackNormal();
    private static Font fontBlackSmall = PDFExporterUtils.fontBlackSmall();

    private void writeTableHeader(PdfPTable table, List<String> listTableHeader) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);

        for(String itemHeader : listTableHeader){
            cell.setPhrase(new Phrase(itemHeader, fontBlackNormal));
            table.addCell(cell);
        }
    }

    private void writeTableSign(PdfPTable table) throws BadPdfFormatException, BadElementException, IOException {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setBorderColor(Color.WHITE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        for(String itemHeader : listSign){
            cell.setPhrase(new Phrase(itemHeader, font));
            table.addCell(cell);
        }

        for(int i = 0; i < listSign.size(); i++){
            cell.setPhrase(new Phrase("(Ky va ghi ro ho ten)", fontBlackSmall));
            table.addCell(cell);
        }

        for(int i = 0; i < listSign.size()-1; i++){
            cell.setPhrase(new Phrase("", fontBlackSmall));
            table.addCell(cell);
        }

        Image image = Image.getInstance("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHRvgrx1YX_bMv8bLZqcZT6--Z0Wi0_kVnxK1GPaIqRoYS9ln3DUxBewAVwi9dhGk33D4&usqp=CAU"); // Replace with the actual path to your image file
        PdfPCell cellWithImage = new PdfPCell();
        cellWithImage.setImage(image);
        table.addCell(cellWithImage);
    }

    private void writeTableData(PdfPTable table, int colNum, List<List<String>> listDataTable) {
        int count = 0;
        for (List<String> itemObject : listDataTable) {
            table.addCell(String.valueOf(count++));
            for(int i = 0; i < colNum - 1; i++){
                table.addCell(itemObject.get(i));
            }
        }
//        table.addCell("");
//        table.addCell("");
//        table.addCell("SUM");
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        /* ___________ TITLE ___________ */
        writeHeader(document);

        /* __________ TABLE 1 __________*/
        addDataToTable(document, colNum1, listDataTable1, listTableHeader1);

        /* __________ TABLE 2 __________*/
        addDataToTable(document, colNum2, listDataTable2, listTableHeader2);

        /* __________ TABLE SIGN __________*/
        PdfPTable tableSign = new PdfPTable(listSign.size());
        tableSign.setWidthPercentage(100f);
        if(listSign.size() == 4){
            tableSign.setWidths(new float[] { 3.5f, 3.5f, 3.5f, 3.5f});
        }
        else if(listSign.size() == 2){
            tableSign.setWidths(new float[] { 3.5f, 3.5f});
        }
        tableSign.setSpacingBefore(10);

        writeTableSign(tableSign);
        document.add(tableSign);

        document.close();

    }

    private void writeHeader(Document document) throws DocumentException {
        documentParaCommon(document, "CONG HOA XA HOI CHU NGHIA VIET NAM", "", fontBlackHeader3, Paragraph.ALIGN_CENTER);
        documentParaCommon(document, "Doc lap - Tu do - Hanh phuc", "", fontBlackSmall, Paragraph.ALIGN_CENTER);
        documentParaCommon(document, "______________________", "", fontBlackSmall, Paragraph.ALIGN_CENTER);
        documentParaCommon(document, "                                ", "", fontBlackHeader1, Paragraph.ALIGN_CENTER);
        documentParaCommon(document, titleHeader, "", fontBlackHeader1, Paragraph.ALIGN_CENTER);
        documentParaCommon(document, "________________________________", "", fontBlackHeader1, Paragraph.ALIGN_CENTER);
        documentParaCommon(document, "                                ", "", fontBlackHeader1, Paragraph.ALIGN_CENTER);
        for(int i = 0; i < listHeader.size(); i++){
            documentParaCommon(document, listHeader.get(i), listHeaderContent.get(i), fontBlackNormal, Paragraph.ALIGN_LEFT);
        }
    }

    private void documentParaCommon(Document document,String header, String headerContent, Font font, int alignment) throws DocumentException {
        Paragraph paragraph= new Paragraph(header + headerContent, font);
        paragraph.setAlignment(alignment);
        document.add(paragraph);
    }

    private void addDataToTable(Document document, int colNum, List<List<String>> listDataTable,  List<String> listDataHeader ) throws DocumentException {
        if(colNum != 0) {
            PdfPTable table = new PdfPTable(colNum);
            table.setWidthPercentage(100f);
            table.setWidths(PDFExporterUtils.getTableCol(colNum));
            table.setSpacingBefore(10);

            writeTableHeader(table, listDataHeader);
            writeTableData(table, colNum, listDataTable);
            document.add(table);
        }
    }
}