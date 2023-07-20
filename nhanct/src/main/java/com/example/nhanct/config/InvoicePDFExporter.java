//package com.example.nhanct.config;
//
//import com.example.doan.entity.DetailInvoiceEntity;
//import com.lowagie.text.Font;
//import com.lowagie.text.*;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.servlet.http.HttpServletResponse;
//import java.awt.*;
//import java.io.IOException;
//import java.util.List;
//
///**
// * UserPDFExporter
// *
// * @author NhanCT
// * @date 7/17/2023 5:33 PM
// */
//@Builder
//@AllArgsConstructor
//@Getter
//@Setter
//public class InvoicePDFExporter {
//    private List<DetailInvoiceEntity> listDetailInvoice;
//    private int price;
//    private int invoiceId;
//    private String status;
//    private String customerName;
//    private String phone;
//    private String email;
//    private String address;
//
//    private void writeTableHeader(PdfPTable table) {
//        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(Color.BLUE);
//        cell.setPadding(5);
//
//        Font font = FontFactory.getFont(FontFactory.HELVETICA);
//        font.setColor(Color.WHITE);
//
//        cell.setPhrase(new Phrase("Product Name", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Quantity", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Price", font));
//        table.addCell(cell);
//    }
//
//    private void writeTableData(PdfPTable table) {
//        for (DetailInvoiceEntity detailInvoice : listDetailInvoice) {
////            table.addCell(String.valueOf(user.getId()));
//            table.addCell(detailInvoice.getProduct().getProductName());
//            table.addCell(String.valueOf(detailInvoice.getProduct().getQuantity()));
//            table.addCell(String.valueOf(detailInvoice.getProduct().getPrice()));
////            table.addCell(String.valueOf(detailInvoice.isEnabled()));
//        }
//        table.addCell("");
//        table.addCell("");
//        table.addCell(String.valueOf(price));
//    }
//
//    public void export(HttpServletResponse response) throws DocumentException, IOException {
//        Document document = new Document(PageSize.A4);
//        PdfWriter.getInstance(document, response.getOutputStream());
//
//        document.open();
//        Font fontBlue = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        fontBlue.setSize(18);
//        fontBlue.setColor(Color.BLUE);
//
//        Font fontBlack = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        fontBlack.setSize(14);
//        fontBlack.setColor(Color.BLACK);
//
//        /* ___________ TITLE ___________ */
//        Paragraph title = new Paragraph("List of Invoice", fontBlue);
//        title.setAlignment(Paragraph.ALIGN_CENTER);
//        document.add(title);
//
//        /* ___________ CUSTOMER NAME ___________ */
////        Paragraph customerName = new Paragraph("Customer Name: " + this.customerName, fontBlack);
////        customerName.setAlignment(Paragraph.ALIGN_LEFT);
////        document.add(customerName);
//        documentParaCommon(document, "Customer Name", this.customerName,fontBlack, Paragraph.ALIGN_LEFT);
//        documentParaCommon(document, "Address", this.address,fontBlack, Paragraph.ALIGN_LEFT);
//        documentParaCommon(document, "Email", this.email,fontBlack, Paragraph.ALIGN_LEFT);
//        documentParaCommon(document, "Phone", this.phone,fontBlack, Paragraph.ALIGN_LEFT);
//        documentParaCommon(document, "Status", this.status,fontBlack, Paragraph.ALIGN_LEFT);
//
//        PdfPTable table = new PdfPTable(3);
//        table.setWidthPercentage(100f);
////        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
//        table.setWidths(new float[] {1.5f, 3.5f, 3.0f});
//        table.setSpacingBefore(10);
//
//        writeTableHeader(table);
//        writeTableData(table);
//
//        document.add(table);
//
//        document.close();
//
//    }
//
//    private void documentParaCommon(Document document, String title, String titleContent, Font font, int alignment){
//        Paragraph paragraph= new Paragraph(title + ": " + titleContent, font);
//        paragraph.setAlignment(alignment);
//        document.add(paragraph);
//    }
//}