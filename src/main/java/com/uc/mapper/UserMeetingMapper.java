package com.uc.mapper;

import com.uc.entity.FrontMeeting;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMeetingMapper {

    List<FrontMeeting> list(String content, String participants, LocalDateTime startTime, LocalDateTime endTime, String username);
}
