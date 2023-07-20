package com.example.nhanct.config;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static javax.swing.text.StyleConstants.ALIGN_LEFT;

/**
 * UserPDFExporter
 *
 * @author NienNQ
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

    /* TABLE */
    private int colNum;
    private List<List<String>> listDataTable;

    /* STYLE */
    Font fontBlueHeader6 = PDFExporterUtils.fontBlueHeader6();
    Font fontBlackHeader1 = PDFExporterUtils.fontBlackHeader1();
    Font fontBlackHeader3 = PDFExporterUtils.fontBlackHeader3();
    Font fontBlackNormal = PDFExporterUtils.fontBlackNormal();

    private void writeTableHeader(PdfPTable table,List<String> listHeader) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        for(String itemHeader : listHeader){
            cell.setPhrase(new Phrase(itemHeader, font));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table) {
        for (List<String> itemObject : listDataTable) {
            for(int i = 0; i < colNum; i++){
                table.addCell(itemObject.get(i));
            }
        }
        table.addCell("");
        table.addCell("");
        table.addCell("SUM");
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        /* ___________ TITLE ___________ */
        writeHeader(document);

        PdfPTable table = new PdfPTable(colNum);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f, 3.0f, 3.0f});
//        table.setWidths(new float[] {1.5f, 3.5f, 3.0f});
        table.setSpacingBefore(10);
//        writeTableHeader(table);
        writeTableHeader(table, listHeader);

        writeTableData(table);
//        writeTableData(table);

        document.add(table);

        document.close();

    }

    private void writeHeader(Document document) throws DocumentException {
        documentParaCommon(document, titleHeader, "", fontBlackHeader1, Paragraph.ALIGN_CENTER);
        documentParaCommon(document, "__________________________________________", "", fontBlackNormal, Paragraph.ALIGN_CENTER);
        for(int i = 0; i < listHeader.size(); i++){
            documentParaCommon(document, listHeader.get(i), listHeaderContent.get(i), fontBlackNormal, Paragraph.ALIGN_LEFT);
        }
    }

    private void documentParaCommon(Document document,String header, String headerContent, Font font, int alignment) throws DocumentException {
        Paragraph paragraph= new Paragraph(header + ": " + headerContent, font);
        paragraph.setAlignment(alignment);
        document.add(paragraph);
    }
}