package com.example.nhanct.enumdef;

import com.example.nhanct.dto.CodeAndTextDto;
import com.example.nhanct.dto.Select2Dto;

import java.util.ArrayList;
import java.util.List;

public enum StatusOfKindOfTaxEnum {
    G5("G5", "Thuế GTGT được khấu trừ 5%"),
    G10("G10", "Thuế GTGT được khấu trừ 10%"),
    G0("G0", "Thuế GTGT được khấu trừ 0%"),
    GKCT("GKCT", "Không thuộc đối tượng chịu thuế GTGT"),
    G8("G8", "Thuế GTGT được khấu trừ 8%"),
    T5("T5", "Thuế GTGT phải nộp 5%"),
    T10("T10", "Thuế GTGT phải nộp 10%"),
    T0("T0", "Thuế GTGT phải nộp 0%"),
    TKCT("TKCT", "Không thuộc đối tượng chịu thuế GTGT"),
    T8("T8", "Thuế GTGT phải nộp 8%"),
    ALL("All", "All"),
    ;

    private final String code;
    private final String text;

    StatusOfKindOfTaxEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static List<CodeAndTextDto> getComboList() {
        List<CodeAndTextDto> list = new ArrayList<>();
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.G5.getCode(), StatusOfKindOfTaxEnum.G5.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.G10.getCode(), StatusOfKindOfTaxEnum.G10.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.G0.getCode(), StatusOfKindOfTaxEnum.G0.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.GKCT.getCode(), StatusOfKindOfTaxEnum.GKCT.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.G8.getCode(), StatusOfKindOfTaxEnum.G8.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.T10.getCode(), StatusOfKindOfTaxEnum.T10.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.T0.getCode(), StatusOfKindOfTaxEnum.T0.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.TKCT.getCode(), StatusOfKindOfTaxEnum.TKCT.getText()));
        list.add(new CodeAndTextDto(StatusOfKindOfTaxEnum.T8.getCode(), StatusOfKindOfTaxEnum.T8.getText()));
        return list;
    }

    public static List<Select2Dto> getSelect2ComboList() {
        List<Select2Dto> list = new ArrayList<>();
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.ALL.getCode(), StatusOfKindOfTaxEnum.ALL.getText(), StatusOfKindOfTaxEnum.ALL.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.G5.getCode(), StatusOfKindOfTaxEnum.G5.getText(), StatusOfKindOfTaxEnum.G5.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.G10.getCode(), StatusOfKindOfTaxEnum.G10.getText(), StatusOfKindOfTaxEnum.G10.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.G0.getCode(), StatusOfKindOfTaxEnum.G0.getText(), StatusOfKindOfTaxEnum.G0.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.GKCT.getCode(), StatusOfKindOfTaxEnum.GKCT.getText(), StatusOfKindOfTaxEnum.GKCT.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.G8.getCode(), StatusOfKindOfTaxEnum.G8.getText(), StatusOfKindOfTaxEnum.G8.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.T10.getCode(), StatusOfKindOfTaxEnum.T10.getText(), StatusOfKindOfTaxEnum.T10.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.T0.getCode(), StatusOfKindOfTaxEnum.T0.getText(), StatusOfKindOfTaxEnum.T0.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.TKCT.getCode(), StatusOfKindOfTaxEnum.TKCT.getText(), StatusOfKindOfTaxEnum.TKCT.getText()));
        list.add(new Select2Dto(StatusOfKindOfTaxEnum.T8.getCode(), StatusOfKindOfTaxEnum.T8.getText(), StatusOfKindOfTaxEnum.T8.getText()));
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getTextByCode(String code) {
        for (StatusOfKindOfTaxEnum en : StatusOfKindOfTaxEnum.values()) {
            if (code.equals(en.getCode())) {
                return en.getText();
            }
        }
        return "";
    }
}
