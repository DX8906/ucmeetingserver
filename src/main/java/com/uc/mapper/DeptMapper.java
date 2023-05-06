package com.uc.mapper;

import com.uc.entity.Dept;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     * 查询全部部门数据
     * @return
     */
    @Select("select * from dept")
    List<Dept> list();

    /**
     * 根据id删除部门
     * @param id
     */
    @Delete("delete from dept where id=#{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /**
     * 查询部门
     * @return
     */
    @Select("select id from dept where name like concat('%', #{name}, '%')")
    List<Integer> search(String name);

    @Update("update dept set name=#{name},update_time=now() where id=${id}")
    void update(Dept dept);

    @Select("select name from dept")
    List<String> selectDept();
}
