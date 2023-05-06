package com.uc.mapper;

import com.uc.entity.FrontMeeting;
import com.uc.entity.Meeting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MeetingMapper {

    List<FrontMeeting> list(String content, String participants, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 新增会议
     * @param meeting
     */
    @Insert("insert into meeting(" +
            "start_time,end_time,meeting_room,content,participants,undertaking_unit," +
            "declarer_id,meeting_notes,meeting_occupancy_id,create_time,update_time) " +
            "values(#{startTime},#{endTime},#{meetingRoom},#{content},#{participants},#{undertakingUnit}," +
            "#{declarerId},#{meetingNotes},#{meetingOccupancyId},#{createTime},#{updateTime})")
    void insert(Meeting meeting);

    void update(Meeting meeting);

    @Select("select * from meeting where  id = #{id}")
    Meeting getById(Integer id);

    @Select("select meeting_occupancy_id from meeting where id=#{id}")
    Integer getOccupancyId(Integer id);

    @Delete("delete from meeting where id=#{id}")
    void deleteById(Integer id);
}
