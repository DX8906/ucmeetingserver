package com.uc.service;

import com.uc.entity.CurrentUserInfo;
import com.uc.entity.Meeting;
import com.uc.entity.PageBean;

import java.time.LocalDateTime;

public interface UserMeetingService {
    PageBean page(Integer page, Integer pageSize, String content, String participants, LocalDateTime startTime, LocalDateTime endTime, CurrentUserInfo currentUserInfo);

    void deleteById(Integer id);

    void update(Meeting meeting);
}
