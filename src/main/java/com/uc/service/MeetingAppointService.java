package com.uc.service;

import com.uc.entity.CurrentUserInfo;
import com.uc.entity.MeetingAndRoom;

public interface MeetingAppointService {

    boolean add(MeetingAndRoom meetingAndRoom, CurrentUserInfo currentUserInfo);
}
