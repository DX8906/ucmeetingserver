package com.uc.controller;

import com.uc.annotation.CurrentUser;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.MeetingAndRoom;
import com.uc.entity.Result;
import com.uc.service.MeetingAppointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/meetingAppoint")
public class MeetingAppointController {

    @Autowired
    private MeetingAppointService meetingAppointService;

    /**
     * 新增预约
     * @return
     */
    @PostMapping
    public Result add(@RequestBody MeetingAndRoom meetingAndRoom,@CurrentUser CurrentUserInfo currentUserInfo){
        log.info("新增预约: {}" , meetingAndRoom);
        boolean isOk = meetingAppointService.add(meetingAndRoom,currentUserInfo);
        if(isOk){
            return Result.success();
        }
        return Result.error("时间冲突");
    }
}
