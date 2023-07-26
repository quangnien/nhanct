package com.example.nhanct.enumdef;

import com.example.nhanct.dto.CodeAndTextDto;
import com.example.nhanct.dto.Select2Dto;

import java.util.ArrayList;
import java.util.List;

public enum StatusOfInvoiceEnum {
    DU_THAO("Draft", "Draft"),
    CHO_DUYET("Waiting Approve", "Waiting Approve"),
    DA_DUYET("Approved", "Approved"),
    DA_HUY("Canceled", "Canceled"),
    ALL("All", "All"),
    ;

    private final String code;
    private final String text;

    StatusOfInvoiceEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static List<CodeAndTextDto> getComboList() {
        List<CodeAndTextDto> list = new ArrayList<>();
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.CHO_DUYET.getCode(), StatusOfInvoiceEnum.CHO_DUYET.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.DA_DUYET.getCode(), StatusOfInvoiceEnum.DA_DUYET.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.DA_HUY.getCode(), StatusOfInvoiceEnum.DA_HUY.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.DU_THAO.getCode(), StatusOfInvoiceEnum.DU_THAO.getText()));
        return list;
    }

    public static List<Select2Dto> getSelect2ComboList() {
        List<Select2Dto> list = new ArrayList<>();
        list.add(new Select2Dto(StatusOfInvoiceEnum.ALL.getCode(), StatusOfInvoiceEnum.ALL.getText(), StatusOfInvoiceEnum.ALL.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.CHO_DUYET.getCode(), StatusOfInvoiceEnum.CHO_DUYET.getText(), StatusOfInvoiceEnum.CHO_DUYET.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.DA_DUYET.getCode(), StatusOfInvoiceEnum.DA_DUYET.getText(), StatusOfInvoiceEnum.DA_DUYET.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.DA_HUY.getCode(), StatusOfInvoiceEnum.DA_HUY.getText(), StatusOfInvoiceEnum.DA_HUY.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.DU_THAO.getCode(), StatusOfInvoiceEnum.DU_THAO.getText(), StatusOfInvoiceEnum.DU_THAO.getText()));
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getTextByCode(String code) {
        for (StatusOfInvoiceEnum en : StatusOfInvoiceEnum.values()) {
            if (code.equals(en.getCode())) {
                return en.getText();
            }
        }
        return "";
    }
}
