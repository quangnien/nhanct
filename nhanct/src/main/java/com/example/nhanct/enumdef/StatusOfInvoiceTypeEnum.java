package com.example.nhanct.enumdef;

import com.example.nhanct.dto.CodeAndTextDto;
import com.example.nhanct.dto.Select2Dto;

import java.util.ArrayList;
import java.util.List;

public enum StatusOfInvoiceTypeEnum {
    VAT("VAT", "Hoá đơn giá trị gia tăng."),
    WC("WC", "Hóa đơn xuất kho kiêm vận chuyển nội bộ."),
    ALL("All", "All"),
    ;

    private final String code;
    private final String text;

    StatusOfInvoiceTypeEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static List<CodeAndTextDto> getComboList() {
        List<CodeAndTextDto> list = new ArrayList<>();
        list.add(new CodeAndTextDto(StatusOfInvoiceTypeEnum.WC.getCode(), StatusOfInvoiceTypeEnum.WC.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceTypeEnum.VAT.getCode(), StatusOfInvoiceTypeEnum.VAT.getText()));
        return list;
    }

    public static List<Select2Dto> getSelect2ComboList() {
        List<Select2Dto> list = new ArrayList<>();
        list.add(new Select2Dto(StatusOfInvoiceTypeEnum.ALL.getCode(), StatusOfInvoiceTypeEnum.ALL.getText(), StatusOfInvoiceTypeEnum.ALL.getText()));
        list.add(new Select2Dto(StatusOfInvoiceTypeEnum.WC.getCode(), StatusOfInvoiceTypeEnum.WC.getText(), StatusOfInvoiceTypeEnum.WC.getText()));
        list.add(new Select2Dto(StatusOfInvoiceTypeEnum.VAT.getCode(), StatusOfInvoiceTypeEnum.VAT.getText(), StatusOfInvoiceTypeEnum.VAT.getText()));
        return list;
    }

    public static List<Select2Dto> getSelect2VAT() {
        List<Select2Dto> list = new ArrayList<>();
        list.add(new Select2Dto(StatusOfInvoiceTypeEnum.VAT.getCode(), StatusOfInvoiceTypeEnum.VAT.getText(), StatusOfInvoiceTypeEnum.VAT.getText()));
        return list;
    }

    public static List<Select2Dto> getSelect2WC() {
        List<Select2Dto> list = new ArrayList<>();
        list.add(new Select2Dto(StatusOfInvoiceTypeEnum.WC.getCode(), StatusOfInvoiceTypeEnum.WC.getText(), StatusOfInvoiceTypeEnum.WC.getText()));
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getTextByCode(String code) {
        for (StatusOfInvoiceTypeEnum en : StatusOfInvoiceTypeEnum.values()) {
            if (code.equals(en.getCode())) {
                return en.getText();
            }
        }
        return "";
    }
}
