package com.kr.pub.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class ExcelReadDTO {
	
    private String filePath;
    private List<String> outputColumns = new ArrayList<>();
    private int startRow;
    private int sheetNum;

    //가변 인자 여러 개의 문자열 추가...
    public void setOutputColumns(String... outputColumns) {
        this.outputColumns.addAll(Arrays.asList(outputColumns));
    }

}
