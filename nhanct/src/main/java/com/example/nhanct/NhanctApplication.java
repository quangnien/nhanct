package com.example.nhanct;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@SpringBootApplication
public class NhanctApplication {

	public static void main(String[] args) {
//		  JasperReport jasperReport = null;
//		try {
//			jasperReport = JasperCompileManager
//			           .compileReport("F:/test/StyledTextReport/StyledTextReport.jrxml");
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	       // Tham số truyền vào báo cáo.
//	       Map<String, Object> parameters = new HashMap<String, Object>();
//
//	       // DataSource
//	       // Đây là báo cáo đơn giản, không kết nối vào DB
//	       // vì vậy không cần nguồn dữ liệu.
//	       JRDataSource dataSource = new JREmptyDataSource();
//
//	       JasperPrint jasperPrint = null;
//		try {
//			jasperPrint = JasperFillManager.fillReport(jasperReport,
//			           parameters, dataSource);
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	       // Đảm bảo thư mục đầu ra tồn tại.
//	       File outDir = new File("F:/test");
//	       outDir.mkdirs();
//
//	       // Chạy báo cáo và export ra file PDF.
//	       try {
//			JasperExportManager.exportReportToPdfFile(jasperPrint,
//			           "F:/test/StyledTextReport.pdf");
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	       System.out.println("Done!");
		
		SpringApplication.run(NhanctApplication.class, args);
	}

}
