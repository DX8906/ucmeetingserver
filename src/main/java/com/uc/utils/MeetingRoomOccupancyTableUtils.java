package com.uc.utils;

import com.uc.entity.MeetingRoomOccupancy;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class MeetingRoomOccupancyTableUtils {
    private static final Map<Integer, String> WEEK_DAY_MAP = new HashMap<>();
    static {
        WEEK_DAY_MAP.put(0, "星期一");
        WEEK_DAY_MAP.put(1, "星期二");
        WEEK_DAY_MAP.put(2, "星期三");
        WEEK_DAY_MAP.put(3, "星期四");
        WEEK_DAY_MAP.put(4, "星期五");
        WEEK_DAY_MAP.put(5, "星期六");
        WEEK_DAY_MAP.put(6, "星期日");
    }

    private static final int WEEK_DAY = 7;
    private static final long HALF_HOUR = 1800000L;
    private static final long ONE_WEEK = 604800000L;
    public static long[] getCurrentWeekTimeFrame(Integer offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //start of the week
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_YEAR,-1);
        }
        calendar.add(Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK) - 2));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long startTime = calendar.getTimeInMillis();
        //end of the week
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long endTime = calendar.getTimeInMillis();
        startTime += ONE_WEEK*offset;
        endTime += ONE_WEEK*offset;
        return new long[]{startTime, endTime};
    }

    public static List<Integer> analyzingTimeSlot(List<MeetingRoomOccupancy> meetingRoomOccupancies,Long startTime){
        List<Integer> result = new ArrayList<>();
        for (MeetingRoomOccupancy m:
             meetingRoomOccupancies) {
            long startMillis=m.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            long endMillis=m.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            long start=(startMillis-startTime)/HALF_HOUR+1;
            long end=(endMillis-startTime)/HALF_HOUR+1;
            for(int i=(int)start;i<end;i++){
                int tmp=i/48;
                if(tmp==0){
                    result.add(i-16);
                }else{
                    result.add(i-16-tmp*21);
                }
            }
        }
        return result;
    }

    public static List<String> getWeekdayAndDate(int offset) {
        List<String> result=new ArrayList<>();
        result.add("time");
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        Date[] dates = new Date[7];
        calendar.add(Calendar.WEEK_OF_YEAR,offset);
        for (int i = 0; i < WEEK_DAY; i++) {
            dates[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        for(int i=0;i<WEEK_DAY;++i){
            result.add(dateFormat.format(dates[i])+"\n"+WEEK_DAY_MAP.get(i));
        }
        return result;
    }
}
