package com.example.nhanct.config;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

/**
 * UserPDFExporter
 *
 * @author NhanCT
 * @date 7/17/2023 5:33 PM
 */
@Getter
@Setter
@AllArgsConstructor
public class PDFExporterUtils {

    public static Font fontBlueHeader6(){
        return setFont(16, Color.BLUE, FontFactory.HELVETICA_BOLD);
    }

    public static Font fontBlackHeader1(){
        return setFont(20, Color.BLACK, FontFactory.HELVETICA_BOLD);
    }

    public static Font fontBlackHeader3(){
        return setFont(14, Color.BLACK, FontFactory.HELVETICA_BOLD);
    }

    public static Font fontBlackNormal(){
        return setFont(12, Color.BLACK, FontFactory.HELVETICA);
    }

    public static Font fontBlackSmall(){
        return setFont(8, Color.BLACK, FontFactory.HELVETICA_OBLIQUE);
    }

    private static Font setFont(int size, Color color, String style){
        Font setFont = FontFactory.getFont(style);
        setFont.setSize(size);
        setFont.setColor(color);
        return setFont;
    }

}