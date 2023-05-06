package com.uc.controller;

import com.uc.annotation.CurrentUser;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.Meeting;
import com.uc.entity.PageBean;
import com.uc.entity.Result;
import com.uc.service.UserMeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/userMeetings")
public class UserMeetingController {
    @Autowired
    private UserMeetingService userMeetingService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "9") Integer pageSize,
                       String content, String participants,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startData,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endData,
                       @CurrentUser CurrentUserInfo currentUserInfo){
        //调用service分页查询
        if(startData!=null&&endData!=null){
            LocalDateTime startTime=startData.atTime(0,0,0);
            LocalDateTime endTime=endData.atTime(23,59,59);
            PageBean pageBean = userMeetingService.page(page,pageSize,content,participants,startTime,endTime,currentUserInfo);
            return Result.success(pageBean);
        }
        PageBean pageBean = userMeetingService.page(page,pageSize,content,participants,null,null,currentUserInfo);
        return Result.success(pageBean);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除会议, id:{}",id);
        userMeetingService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Meeting meeting){
        log.info("更新会议信息 : {}", meeting);
        userMeetingService.update(meeting);
        return Result.success();
    }
}
