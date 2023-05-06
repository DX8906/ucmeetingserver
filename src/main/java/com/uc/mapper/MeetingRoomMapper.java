package com.uc.mapper;

import com.uc.entity.MeetingRoom;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MeetingRoomMapper {
    /**
     * 查询全部会议室数据
     * @return
     */
    @Select("select * from meeting_room")
    List<MeetingRoom> list();

    /**
     * 根据id删除会议室
     * @param id
     */
    @Delete("delete from meeting_room where id=#{id}")
    void deleteById(Integer id);

    /**
     * 新增会议室
     * @param meetingRoom
     */
    @Insert("insert into meeting_room(name,capacity,description, create_time, update_time) " +
            "values(#{name},#{capacity},#{description},#{createTime},#{updateTime})")
    void insert(MeetingRoom meetingRoom);

    /**
     * 查询会议室
     * @return
     */
    @Select("select id from meeting_room where name like concat('%', #{name}, '%')")
    List<Integer> search(String name);

    void update(MeetingRoom meetingRoom);

    @Select("select *\n" +
            "    from meeting_room r\n" +
            "    where r.id not in(\n" +
            "        select meeting_room_id\n" +
            "            from meeting_room_occupancy o\n" +
            "                where not (o.end_time < #{startTime} or o.start_time > #{endTime}))\n" +
            "    order by r.update_time desc")
    List<MeetingRoom> listFree(LocalDateTime startTime, LocalDateTime endTime);
}
