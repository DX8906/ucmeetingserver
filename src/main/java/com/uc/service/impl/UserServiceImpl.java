package com.uc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uc.entity.CurrentUserInfo;
import com.uc.entity.PageBean;
import com.uc.entity.User;
import com.uc.mapper.UserMapper;
import com.uc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String deptName, CurrentUserInfo currentUserInfo) {
        PageHelper.startPage(page,pageSize);
        List<User> userList = userMapper.list(name, deptName);
        if(currentUserInfo.getPermission().equals("管理员")){
            for (User user:
                 userList) {
                if(user.getUsername().equals(currentUserInfo.getUsername())){
                    user.setPossessPermissions(1);
                }
                else if( user.getPermission().equals("管理员") ){
                    user.setPossessPermissions(0);
                }else{
                    user.setPossessPermissions(1);
                }
            }
        }else{
            for (User user:
                 userList) {
                user.setPossessPermissions(1);
            }
        }
        Page<User> p = (Page<User>) userList;
        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public String save(User user) {
        if(userMapper.listUsername(user.getUsername())==null){
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            userMapper.insert(user);
        }else{
            return "用户名已存在";
        }
        return "Ok";
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());

        userMapper.update(user);
    }

    @Override
    public User login(User user) {
        return userMapper.getByUsernameAndPassword(user);
    }

    @Override
    public void resetPassword(Integer id) {
        userMapper.resetPassword(id);
    }
}

