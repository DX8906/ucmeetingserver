package com.uc.mapper;

import com.uc.entity.MeetingAndRoom;
import com.uc.entity.MeetingRoomOccupancy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MeetingRoomOccupancyMapper {
    /**
     * 新增部门
     * @param meetingRoomOccupancy
     */

    Integer insert(MeetingRoomOccupancy meetingRoomOccupancy);

    @Delete("delete from meeting_room_occupancy where id=#{id}")
    void deleteById(Integer id);

    void update(Integer occupancyId, LocalDateTime startTime, LocalDateTime endTime);

    @Select("select id,start_time,end_time from meeting_room_occupancy where start_time between #{startTime} and #{endTime} and end_time between #{startTime} and #{endTime} and meeting_room_id=#{roomId}")
    List<MeetingRoomOccupancy> getTimeSlot(Integer roomId, LocalDateTime startTime, LocalDateTime endTime);

    @Select("select * from meeting_room_occupancy o where not (o.end_time < #{startTime} or o.start_time > #{endTime}) and meeting_room_id = #{meetingRoomId}")
    List<MeetingRoomOccupancy> listConflict(MeetingAndRoom meetingAndRoom);
}
