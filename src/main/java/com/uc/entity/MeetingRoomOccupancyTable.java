package com.uc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRoomOccupancyTable {
    private List<String> weekDayAndDate;
    private List<Integer> occupancyList;
}
