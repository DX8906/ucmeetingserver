package com.uc.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.FrontMeeting;
import com.uc.entity.Meeting;
import com.uc.mapper.MeetingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FrontMeetingListener implements ReadListener<FrontMeeting> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<FrontMeeting> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private MeetingMapper meetingMapper;
    private CurrentUserInfo currentUserInfo;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param meetingMapper
     */
    public FrontMeetingListener(MeetingMapper meetingMapper, CurrentUserInfo currentUserInfo) {
        this.meetingMapper = meetingMapper;
        this.currentUserInfo=currentUserInfo;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(FrontMeeting data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        List<Meeting> meetingList = new ArrayList<>();
        for (FrontMeeting tmp:
             cachedDataList) {
            meetingList.add(new Meeting(null,tmp.getStartTime(),tmp.getEndTime(),
                    tmp.getMeetingRoom(),tmp.getContent(),tmp.getParticipants(),tmp.getUndertakingUnit(),
                    currentUserInfo.getUsername(), tmp.getMeetingNotes(), null, LocalDateTime.now(),LocalDateTime.now()));
        }
        for (Meeting m :
                meetingList) {
            meetingMapper.insert(m);
        }
        log.info("存储数据库成功！");
    }
}
