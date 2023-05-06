package com.uc.service;

import com.uc.entity.ConferencePresentation;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.Meeting;
import com.uc.entity.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {

    PageBean page(Integer page, Integer pageSize, String content, String participants, LocalDateTime startTime, LocalDateTime endTime);

    void deleteById(Integer id);

    void save(Meeting meeting);

    Meeting getById(Integer id);

    void update(Meeting meeting);

    List<ConferencePresentation> listPresentation();

    boolean upload(MultipartFile file, CurrentUserInfo currentUserInfo);

    String download(String content, String participants, LocalDate startData, LocalDate endData);
}
