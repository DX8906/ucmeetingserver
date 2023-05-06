package com.uc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.FrontMeeting;
import com.uc.entity.Meeting;
import com.uc.entity.PageBean;
import com.uc.mapper.MeetingMapper;
import com.uc.mapper.MeetingRoomOccupancyMapper;
import com.uc.mapper.UserMeetingMapper;
import com.uc.service.UserMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserMeetingServiceImpl implements UserMeetingService {

    @Autowired
    private UserMeetingMapper userMeetingMapper;
    @Autowired
    private MeetingMapper meetingMapper;
    @Autowired
    private MeetingRoomOccupancyMapper meetingRoomOccupancyMapper;
    @Override
    public PageBean page(Integer page, Integer pageSize, String content, String participants, LocalDateTime startTime, LocalDateTime endTime, CurrentUserInfo currentUserInfo) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<FrontMeeting> meetingList = userMeetingMapper.list(content, participants,startTime,endTime,currentUserInfo.getUsername());
        Page<FrontMeeting> p = (Page<FrontMeeting>) meetingList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void deleteById(Integer id) {
        meetingMapper.deleteById(id);
    }

    @Override
    public void update(Meeting meeting) {
        meeting.setUpdateTime(LocalDateTime.now());
        Integer occupancyId=meetingMapper.getOccupancyId(meeting.getId());
        if(occupancyId!=null&&(meeting.getStartTime()!=null||meeting.getEndTime()!=null)){
            meetingRoomOccupancyMapper.update(occupancyId,meeting.getStartTime(),meeting.getEndTime());
        }
        meetingMapper.update(meeting);
    }
}
