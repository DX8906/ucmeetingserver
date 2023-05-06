package com.uc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uc.entity.*;
import com.uc.mapper.MeetingMapper;
import com.uc.mapper.MeetingRoomOccupancyMapper;
import com.uc.service.MeetingService;
import com.uc.utils.ConferencePresentationUtils;
import com.uc.utils.MeetingImportExportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MeetingRoomOccupancyMapper meetingRoomOccupancyMapper;
    @Override
    public PageBean page(Integer page, Integer pageSize, String content, String participants, LocalDateTime startTime, LocalDateTime endTime) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<FrontMeeting> meetingList = meetingMapper.list(content, participants,startTime,endTime);
        Page<FrontMeeting> p = (Page<FrontMeeting>) meetingList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        Integer occupancyId=meetingMapper.getOccupancyId(id);
        if(occupancyId!=null){
            meetingRoomOccupancyMapper.deleteById(occupancyId);
        }
        meetingMapper.deleteById(id);
    }

    @Override
    public void save(Meeting meeting) {
        meeting.setCreateTime(LocalDateTime.now());
        meeting.setUpdateTime(LocalDateTime.now());
        meetingMapper.insert(meeting);
    }

    @Override
    public Meeting getById(Integer id) {
        return meetingMapper.getById(id);
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

    @Override
    public List<ConferencePresentation> listPresentation() {
        long[] startAndEndTime= ConferencePresentationUtils.getCurrentWeekTimeFrame();
        LocalDateTime startTime=new Date(startAndEndTime[0]).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        LocalDateTime endTime=new Date(startAndEndTime[1]).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        List<FrontMeeting> meetingList = meetingMapper.list(null, null,startTime,endTime);
        meetingList=ConferencePresentationUtils.eventData(meetingList);
        List<ConferencePresentation> cpList=new ArrayList<>();
        for (FrontMeeting item:
             meetingList) {
            cpList.add(ConferencePresentationUtils.frontMeetingToPresentation(item));
        }
        return cpList;
    }

    @Override
    public boolean upload(MultipartFile file, CurrentUserInfo currentUserInfo) {
        boolean re=true;
        try {
            String originalFileName = file.getOriginalFilename();
            int index = originalFileName.lastIndexOf('.');
            String extname = originalFileName.substring(index);
            String newFileName = UUID.randomUUID() + extname;
            String filePath = "D:\\" + newFileName;
            file.transferTo(new File(filePath));
            re = MeetingImportExportUtils.importMeetings(filePath,currentUserInfo,meetingMapper);
            MeetingImportExportUtils.deleteFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return re;
    }

    @Override
    public String download(String content, String participants, LocalDate startData, LocalDate endData) {
        List<FrontMeeting> meetingList;
        if(startData!=null&&endData!=null){
            LocalDateTime startTime=startData.atTime(0,0,0);
            LocalDateTime endTime=endData.atTime(23,59,59);
            meetingList=meetingMapper.list(content,participants,startTime,endTime);
        }else{
            meetingList = meetingMapper.list(content, participants,null,null);
        }
        String filePath =  "file:///"+MeetingImportExportUtils.export(meetingList,content, participants, startData, endData);
        return filePath;
    }
}
