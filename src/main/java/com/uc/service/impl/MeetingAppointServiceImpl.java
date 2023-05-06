package com.uc.service.impl;

import com.uc.entity.CurrentUserInfo;
import com.uc.entity.Meeting;
import com.uc.entity.MeetingAndRoom;
import com.uc.entity.MeetingRoomOccupancy;
import com.uc.mapper.MeetingMapper;
import com.uc.mapper.MeetingRoomOccupancyMapper;
import com.uc.service.MeetingAppointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingAppointServiceImpl implements MeetingAppointService {

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MeetingRoomOccupancyMapper meetingRoomOccupancyMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(MeetingAndRoom meetingAndRoom, CurrentUserInfo currentUserInfo) {
        List<MeetingRoomOccupancy> meetingRoomOccupancies = meetingRoomOccupancyMapper.listConflict(meetingAndRoom);
        if(meetingRoomOccupancies.isEmpty()){
            LocalDateTime lt=LocalDateTime.now();
            MeetingRoomOccupancy meetingRoomOccupancy=new MeetingRoomOccupancy
                    (null,meetingAndRoom.getMeetingRoomId(), meetingAndRoom.getStartTime(),
                            meetingAndRoom.getEndTime(), lt,lt);
            meetingRoomOccupancyMapper.insert(meetingRoomOccupancy);
            Integer lastId=meetingRoomOccupancy.getId();

            Meeting meeting=new Meeting(null,meetingAndRoom.getStartTime(),meetingAndRoom.getEndTime(),
                    meetingAndRoom.getMeetingRoom(),meetingAndRoom.getContent(),
                    meetingAndRoom.getParticipants(),meetingAndRoom.getUndertakingUnit(),
                    currentUserInfo.getUsername(), meetingAndRoom.getMeetingNotes(),lastId,
                    lt,lt);
            meetingMapper.insert(meeting);
            return true;
        }
        return false;
    }
}
