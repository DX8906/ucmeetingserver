package com.uc.service.impl;

import com.uc.entity.MeetingRoom;
import com.uc.entity.MeetingRoomOccupancy;
import com.uc.mapper.MeetingRoomMapper;
import com.uc.mapper.MeetingRoomOccupancyMapper;
import com.uc.service.MeetingRoomService;
import com.uc.utils.MeetingRoomOccupancyTableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    private MeetingRoomMapper meetingRoomMapper;

    @Autowired
    private MeetingRoomOccupancyMapper meetingRoomOccupancyMapper;

    @Override
    public List<MeetingRoom> list() {
        return meetingRoomMapper.list();
    }

    @Override
    public void delete(Integer id) {
        meetingRoomMapper.deleteById(id);
    }

    @Override
    public void add(MeetingRoom meetingRoom) {
        meetingRoom.setCreateTime(LocalDateTime.now());
        meetingRoom.setUpdateTime(LocalDateTime.now());

        meetingRoomMapper.insert(meetingRoom);
    }

    @Override
    public void update(MeetingRoom meetingRoom) {
        meetingRoomMapper.update(meetingRoom);
    }

    @Override
    public List<MeetingRoom> listFree(LocalDateTime startTime, LocalDateTime endTime) {
        List<MeetingRoom> result = meetingRoomMapper.listFree(startTime, endTime);
        for (MeetingRoom m:
             result) {
            m.setNameCapacity(m.getName()+"("+m.getCapacity()+")");
        }
        return result;
    }

    @Override
    public List<Integer> getOccupancyTable(Integer id, Integer offset) {
        long[] startEndTime= MeetingRoomOccupancyTableUtils.getCurrentWeekTimeFrame(offset);
        LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startEndTime[0]), ZoneId.of("GMT+8"));
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startEndTime[1]), ZoneId.of("GMT+8"));
        List<MeetingRoomOccupancy> meetingRoomOccupancies = meetingRoomOccupancyMapper.getTimeSlot(id,startTime,endTime);
        List<Integer> result = MeetingRoomOccupancyTableUtils.analyzingTimeSlot(meetingRoomOccupancies,startEndTime[0]);
        return result;
    }
}
