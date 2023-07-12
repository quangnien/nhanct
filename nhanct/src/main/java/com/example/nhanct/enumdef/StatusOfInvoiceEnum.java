package com.example.nhanct.enumdef;

import com.example.nhanct.dto.CodeAndTextDto;
import com.example.nhanct.dto.Select2Dto;

import java.util.ArrayList;
import java.util.List;

public enum StatusOfInvoiceEnum {
    CHO_LAY_HANG("Waiting for pick-up", "Waiting for pick-up."),
    DANG_LAY_HANG("Currently picking up the goods", "Currently picking up the goods"),
    DANG_GIAO_HANG("Currently delivering the goods", "Currently delivering the goods"),
    DA_GIAO_HANG("The goods have been delivered", "The goods have been delivered"),
    DA_YEU_CAU_HUY_DON_HANG("Cancellation of the order has been requested", "Cancellation of the order has been requested"),
    XAC_NHAN_DA_HUY_DON_HANG("Cancellation of the order has been confirmed", "Cancellation of the order has been confirmed");

    private final String code;
    private final String text;

    StatusOfInvoiceEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static List<CodeAndTextDto> getComboList() {
        List<CodeAndTextDto> list = new ArrayList<>();
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.CHO_LAY_HANG.getCode(), StatusOfInvoiceEnum.CHO_LAY_HANG.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.DANG_LAY_HANG.getCode(), StatusOfInvoiceEnum.DANG_LAY_HANG.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.DANG_GIAO_HANG.getCode(), StatusOfInvoiceEnum.DANG_GIAO_HANG.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.DA_GIAO_HANG.getCode(), StatusOfInvoiceEnum.DA_GIAO_HANG.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.DA_YEU_CAU_HUY_DON_HANG.getCode(), StatusOfInvoiceEnum.DA_YEU_CAU_HUY_DON_HANG.getText()));
        list.add(new CodeAndTextDto(StatusOfInvoiceEnum.XAC_NHAN_DA_HUY_DON_HANG.getCode(), StatusOfInvoiceEnum.XAC_NHAN_DA_HUY_DON_HANG.getText()));
        return list;
    }

    public static List<Select2Dto> getSelect2ComboList() {
        List<Select2Dto> list = new ArrayList<>();
        list.add(new Select2Dto(StatusOfInvoiceEnum.CHO_LAY_HANG.getCode(), StatusOfInvoiceEnum.CHO_LAY_HANG.getText(), StatusOfInvoiceEnum.CHO_LAY_HANG.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.DANG_LAY_HANG.getCode(), StatusOfInvoiceEnum.DANG_LAY_HANG.getText(), StatusOfInvoiceEnum.DANG_LAY_HANG.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.DANG_GIAO_HANG.getCode(), StatusOfInvoiceEnum.DANG_GIAO_HANG.getText(), StatusOfInvoiceEnum.DANG_GIAO_HANG.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.DA_GIAO_HANG.getCode(), StatusOfInvoiceEnum.DA_GIAO_HANG.getText(), StatusOfInvoiceEnum.DA_GIAO_HANG.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.DA_YEU_CAU_HUY_DON_HANG.getCode(), StatusOfInvoiceEnum.DA_YEU_CAU_HUY_DON_HANG.getText(), StatusOfInvoiceEnum.DA_YEU_CAU_HUY_DON_HANG.getText()));
        list.add(new Select2Dto(StatusOfInvoiceEnum.XAC_NHAN_DA_HUY_DON_HANG.getCode(), StatusOfInvoiceEnum.XAC_NHAN_DA_HUY_DON_HANG.getText(), StatusOfInvoiceEnum.XAC_NHAN_DA_HUY_DON_HANG.getText()));
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
