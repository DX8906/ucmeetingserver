package com.uc.utils;

import com.uc.entity.ConferencePresentation;
import com.uc.entity.FrontMeeting;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConferencePresentationUtils {

    private static final Map<Integer, String> NUMBNER_MAP = new HashMap<>();

    static {
        NUMBNER_MAP.put(0, "零");
        NUMBNER_MAP.put(1, "一");
        NUMBNER_MAP.put(2, "二");
        NUMBNER_MAP.put(3, "三");
        NUMBNER_MAP.put(4, "四");
        NUMBNER_MAP.put(5, "五");
        NUMBNER_MAP.put(6, "六");
        NUMBNER_MAP.put(7, "日");
        NUMBNER_MAP.put(8, "八");
        NUMBNER_MAP.put(9, "九");
    }

    public static long[] getCurrentWeekTimeFrame() {
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
        return new long[]{startTime, endTime};
    }

    public static String[] timeToString(LocalDateTime time){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        String dateTimeString=time.format(dtf);
        String month=dateTimeString.substring(0,2);
        String day=dateTimeString.substring(3,5);
        String dateString=month+"月"+day+"日";
        String timeString=dateTimeString.substring(6,11);
        Integer weekDayNum = time.getDayOfWeek().getValue();
        String weekDayChar=NUMBNER_MAP.get(weekDayNum);
        String weekDayString = "星期"+weekDayChar;
        String[] result=new String[4];
        result[0]=dateString;
        result[1]=weekDayString;
        result[2]=dateString+"\n"+weekDayString;
        result[3]=timeString;
        return result;
    }

    public static ConferencePresentation frontMeetingToPresentation(FrontMeeting meeting){
        String [] dataTimeString=timeToString(meeting.getStartTime());
        ConferencePresentation cp=new ConferencePresentation(
                dataTimeString[0],dataTimeString[1],dataTimeString[2],dataTimeString[3],
                meeting.getMeetingRoom(),meeting.getContent(),
                meeting.getParticipants(),meeting.getUndertakingUnit(),
                meeting.getMeetingNotes()
        );
        return cp;
    }

    //对记录进行时间排序的方法eventData(List<Event> eventList)

    public static List<FrontMeeting> eventData(List<FrontMeeting> eventList) {
        Collections.sort(eventList, (e1, e2) -> {
            int diff = e1.getStartTime().compareTo(e2.getStartTime());
            if (diff<0){
                return -1;
            }else if (diff>0){
                return 1;
            }
            return 0;//相等为0
        });
        return eventList;
    }
}
