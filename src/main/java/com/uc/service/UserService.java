package com.uc.service;

import com.uc.entity.CurrentUserInfo;
import com.uc.entity.PageBean;
import com.uc.entity.User;

/**
 * 员工管理
 */
public interface UserService {
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param currentUserInfo
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String name, String dept, CurrentUserInfo currentUserInfo);


    void delete(Integer id);

    /**
     * 新增员工
     * @param user
     */
    String save(User user);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 更新员工
     * @param user
     */
    void update(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 重置密码
     * @param id
     */
    void resetPassword(Integer id);
}
