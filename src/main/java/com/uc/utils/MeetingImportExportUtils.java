package com.uc.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.FrontMeeting;
import com.uc.mapper.MeetingMapper;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MeetingImportExportUtils {
    public static String export(List<FrontMeeting> meetingList, String content, String participants, LocalDate startData, LocalDate endData){
        StringBuilder fileName = new StringBuilder();
        if(content!=null&&content!=""){
            fileName.append("内容："+content+"_");
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");
        if (participants!=null&&participants!=""){
            fileName.append("人员："+participants+"_");
        }
        if(startData!=null){
            String start = startData.format(fmt);
            fileName.append("开始时间："+start+"_");
        }
        if(startData!=null){
            String end = endData.format(fmt);
            fileName.append("结束时间："+end+"_");
        }
        String filePath = "D:\\" + fileName +System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("id");
        EasyExcel.write(filePath, FrontMeeting.class)
                .sheet("会议")
                .excludeColumnFieldNames(excludeColumnFiledNames)
                .doWrite(() -> {
                    // 分页查询数据
                    return meetingList;
                });
        return filePath;
    }


    public static void deleteFile(String sPath) {
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    public static boolean importMeetings(String filePath, CurrentUserInfo currentUserInfo, MeetingMapper meetingMapper){
        // 一个文件一个reader
        try (ExcelReader excelReader = EasyExcel.read(filePath, FrontMeeting.class, new FrontMeetingListener(meetingMapper,currentUserInfo)).build()) {
            // 构建一个sheet 这里可以指定名字或者no
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            // 读取一个sheet
            excelReader.read(readSheet);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
