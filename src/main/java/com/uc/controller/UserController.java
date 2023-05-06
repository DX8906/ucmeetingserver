package com.uc.controller;

import com.uc.annotation.CurrentUser;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.PageBean;
import com.uc.entity.Result;
import com.uc.entity.User;
import com.uc.service.UserService;
import com.uc.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User u = userService.login(user);

        //登录成功,生成令牌,下发令牌
        if (u != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("permission", u.getPermission());
            claims.put("name", u.getName());
            claims.put("username", u.getUsername());
            claims.put("id",u.getId());
            String jwt = JwtUtils.generateJwt(claims); //jwt包含了当前登录的员工信息

            return Result.success(jwt);
        }

        //登录失败, 返回错误信息
        return Result.error("用户名或密码错误");
    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "9") Integer pageSize,
                       String name, String deptName, @CurrentUser CurrentUserInfo currentUserInfo){
        //调用service分页查询
        PageBean pageBean = userService.page(page,pageSize,name,deptName,currentUserInfo);
        return Result.success(pageBean);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("删除操作, id:{}",id);
        userService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody User user){
        log.info("新增员工, user: {}", user);
        if(userService.save(user).equals("Ok")){
            return Result.success();
        }
        return Result.error("用户名已存在");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询员工信息, id: {}",id);
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PutMapping
    public Result update(@RequestBody User user){
        log.info("更新员工信息 : {}", user);
        userService.update(user);
        return Result.success();
    }
    @PutMapping("/{id}")
    public Result resetPassword(@PathVariable Integer id){
        userService.resetPassword(id);
        return Result.success();
    }
}