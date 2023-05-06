package com.uc.controller;

import com.uc.annotation.CurrentUser;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.Result;
import com.uc.entity.User;
import com.uc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/changePassword")
@CrossOrigin
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @PutMapping
    public Result changePassword(String oldPassword,String newPassword, @CurrentUser CurrentUserInfo currentUserInfo){
        log.info("更新密码");
        User user=new User();
        user.setId(currentUserInfo.getId());
        user.setUsername(currentUserInfo.getUsername());
        user.setPassword(oldPassword);
        User u=userService.login(user);
        if(u!=null){
            user.setPassword(newPassword);
            userService.update(user);
            return Result.success();
        }
        return Result.error("密码错误");
    }
}
