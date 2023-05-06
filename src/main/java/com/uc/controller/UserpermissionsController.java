package com.uc.controller;

import com.uc.entity.Result;
import com.uc.service.UserpermissionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/userpermissions")
public class UserpermissionsController {
    @Autowired
    private UserpermissionsService userpermissionsservice;

    @GetMapping
    public Result list(){
        log.info("查询全部的权限数据");
        List<String> userpermissionsList= userpermissionsservice.list();
        return Result.success(userpermissionsList);
    }
}
