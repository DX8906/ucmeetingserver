package com.uc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferencePresentationAttribute {
    private Integer id;
    private String title;//标题
    private Integer quantityPerPage;//每页会议条数
    private Integer refreshInterval;//刷新时间，分钟计
    private Integer rollingInterval;//滚动时间，秒计
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime; //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime; //修改时间
}
