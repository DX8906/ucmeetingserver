package com.uc.service;

import com.uc.entity.MeetingRoom;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomService {
    List<MeetingRoom> list();

    void delete(Integer id);

    void add(MeetingRoom meetingRoom);

    void update(MeetingRoom meetingRoom);

    List<MeetingRoom> listFree(LocalDateTime startTime, LocalDateTime endTime);

    List<Integer> getOccupancyTable(Integer id, Integer offset);
}
