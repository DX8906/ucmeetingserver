package com.uc.service;


import com.uc.entity.Dept;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {
    /**
     * 查询全部部门数据
     * @return
     */
    List<Dept> list();

    /**
     * 删除部门
     * @param id
     */
    void delete(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    void add(Dept dept);

    /**
     * 查询部门
     * @param name
     * @return
     */
    List<Integer> search(String name);

    void update(Dept dept);

    List<String> selectDept();
}
