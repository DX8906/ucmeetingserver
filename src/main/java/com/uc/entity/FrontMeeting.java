package com.uc.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontMeeting {
//    @ExcelProperty("ID")
//    @ColumnWidth(10)
    private Integer id;

    @ExcelProperty("开始时间")
    @ColumnWidth(25)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat("yyyy年MM月dd日 HH:mm")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    @ColumnWidth(25)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat("yyyy年MM月dd日 HH:mm")
    private LocalDateTime endTime;

    @ExcelProperty("地点")
    @ColumnWidth(20)
    private String meetingRoom;

    @ExcelProperty("内容")
    @ColumnWidth(30)
    private String content;

    @ExcelProperty("参会人员")
    @ColumnWidth(30)
    private String participants;

    @ExcelProperty("承办单位")
    @ColumnWidth(20)
    private String undertakingUnit;

    @ExcelProperty("备注")
    @ColumnWidth(20)
    private String meetingNotes;

    @ExcelProperty("申报人")
    @ColumnWidth(20)
    private String name;//申报人姓名
}
