package com.uc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferencePresentation {
    private String date;
    private String weekDay;
    private String fullDate;
    private String time;
    private String location;
    private String content;
    private String participants;
    private String undertakingUnit;
    private String notes;
}
