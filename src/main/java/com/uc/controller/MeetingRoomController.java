package com.uc.controller;

import com.uc.entity.*;
import com.uc.service.MeetingRoomService;
import com.uc.utils.MeetingRoomOccupancyTableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/meetingRooms")
public class MeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    /**
     * 查询部门数据
     * @return
     */
    @GetMapping
    public Result list(){
        log.info("查询全部的会议室数据");
        List<MeetingRoom> meetingRoomList= meetingRoomService.list();
        return Result.success(meetingRoomList);
    }

    @GetMapping("/listFree")
    public Result list(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime){
        log.info("查询全部的部门数据");
        List<MeetingRoom> meetingRoomList= meetingRoomService.listFree(startTime,endTime);
        return Result.success(meetingRoomList);
    }

    /**
     * 删除部门
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除会议室:{}",id);
        meetingRoomService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门
     * @return
     */
    @PostMapping
    public Result add(@RequestBody MeetingRoom meetingRoom){
        log.info("新增会议室: {}" , meetingRoom);
        meetingRoomService.add(meetingRoom);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody MeetingRoom meetingRoom){
        log.info("更新会议室信息 : {}", meetingRoom);
        meetingRoomService.update(meetingRoom);
        return Result.success();
    }

    /**
     * 前端展示会议室占用表格
     * @param id
     * @param offset
     * @return
     */
    @GetMapping("/getOccupancyTable")
    public Result getOccupancyTable(Integer id, Integer offset){
        List<Integer> occupancyList = meetingRoomService.getOccupancyTable(id,offset);
        List<String> weekDayAndDate=MeetingRoomOccupancyTableUtils.getWeekdayAndDate(offset);
        MeetingRoomOccupancyTable meetingRoomOccupancyTable=
                new MeetingRoomOccupancyTable(weekDayAndDate,occupancyList);
        return Result.success(meetingRoomOccupancyTable);
    }
}
