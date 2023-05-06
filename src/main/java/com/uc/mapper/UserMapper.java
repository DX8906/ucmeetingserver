package com.uc.mapper;

import com.uc.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 用户信息查询
     * @return
     */
    List<User> list(String name, String deptName);

    /**
     * 删除
     * @param id
     */
    @Delete("delete from user where id=#{id}")
    void delete(Integer id);

    /**
     * 新增用户
     * @param user
     */
    @Insert("insert into user(username, password,name, permission,  telephone,dept_name, create_time, update_time) " +
            " values(#{username},#{password},#{name},#{permission},#{telephone},#{deptName},#{createTime},#{updateTime})")
    void insert(User user);

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @Select("select * from user where  id = #{id}")
    User getById(Integer id);

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    /**
     * 根据用户名和密码查询用户
     * @param user
     * @return
     */
    @Select("select * from user where username = #{username} and password = #{password}")
    User getByUsernameAndPassword(User user);

    @Select("select * from user where username = #{username}")
    User listUsername(String username);

    @Update("update user set password='123456' where id=#{id}")
    void resetPassword(Integer id);
}
