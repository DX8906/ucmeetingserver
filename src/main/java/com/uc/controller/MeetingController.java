package com.uc.controller;

import com.uc.annotation.CurrentUser;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.Meeting;
import com.uc.entity.PageBean;
import com.uc.entity.Result;
import com.uc.service.MeetingService;
import com.uc.utils.MeetingImportExportUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "9") Integer pageSize,
                       String content, String participants,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startData,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endData){
        //调用service分页查询
        if(startData!=null&&endData!=null){
            LocalDateTime startTime=startData.atTime(0,0,0);
            LocalDateTime endTime=endData.atTime(23,59,59);
            PageBean pageBean = meetingService.page(page,pageSize,content,participants,startTime,endTime);
            return Result.success(pageBean);
        }
        PageBean pageBean = meetingService.page(page,pageSize,content,participants,null,null);
        return Result.success(pageBean);
    }

    @GetMapping("/presentation")
    public Result listPresentation(){
        return Result.success(meetingService.listPresentation());
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除会议, id:{}",id);
        meetingService.deleteById(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Meeting meeting,@CurrentUser CurrentUserInfo currentUserInfo){
        log.info("新增会议, user: {}", meeting);
        meeting.setDeclarerId(currentUserInfo.getUsername());
        meetingService.save(meeting);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询会议信息, id: {}",id);
        Meeting meeting = meetingService.getById(id);
        return Result.success(meeting);
    }

    @PutMapping
    public Result update(@RequestBody Meeting meeting){
        log.info("更新会议信息 : {}", meeting);
        meetingService.update(meeting);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file,@CurrentUser CurrentUserInfo currentUserInfo){
        boolean isOk = meetingService.upload(file,currentUserInfo);
        log.info(String.valueOf(isOk));
        if(!isOk){
            return Result.error("导入出错");
        }
        return Result.success();
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, String content, String participants,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startData,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endData){
        String path = meetingService.download(content, participants,startData,endData);
        int nameIndex=path.lastIndexOf('\\');
        String fileName=path.substring(nameIndex+1);
        try {
            URL url = new URL(path);
            URLConnection conn = null;
            conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
//            response.reset();
            //文件类型自动判断
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("UTF-8");
            //将Content-Disposition暴露给前端
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            //将文件名放入header的Content-Disposition中
//            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            log.info(fileName);
            byte[] buffer = new byte[1024 * 1024 * 10];
            int len;
            BufferedOutputStream outputStream =new BufferedOutputStream(response.getOutputStream()) ;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = path.substring(8);
        MeetingImportExportUtils.deleteFile(filePath);
    }
}
