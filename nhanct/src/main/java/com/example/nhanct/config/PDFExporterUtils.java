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

    static float[] getTableCol(int colNum){
        float f2 = 2f;
        float f35 = 3.5f;
        if(colNum == 1){
            return new float[] {f2};
        }
        else if(colNum == 2){
            return new float[] {f2, f35};
        }
        else if(colNum == 3){
            return new float[] {f2, f35, f35};
        }
        else if(colNum == 4){
            return new float[] {f2, f35, f35, f35};
        }
        else if(colNum == 5){
            return new float[] {f2, f35, f35, f35, f35};
        }
        else if(colNum == 6){
            return new float[] {f2, f35, f35, f35, f35, f35};
        }
        else if(colNum == 7){
            return new float[] {f2, f35, f35, f35, f35, f35, f35};
        }
        else if(colNum == 8){
            return new float[] {f2, f35, f35, f35, f35, f35, f35, f35};
        }
        else if(colNum == 9){
            return new float[] {f2, f35, f35, f35, f35, f35, f35, f35, f35};
        }

        return new float[0];
    }

}