package com.uc.controller;

import com.uc.entity.Dept;
import com.uc.entity.Result;
import com.uc.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
@CrossOrigin
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询部门数据
     * @return
     */
    @GetMapping
    public Result list(){
        log.info("查询全部的部门数据");
        List<Dept> deptList= deptService.list();
        return Result.success(deptList);
    }

    @GetMapping("/select")
    public Result selectDept(){
        List<String> deptList=deptService.selectDept();
        return Result.success(deptList);
    }

    /**
     * 删除部门
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门:{}",id);
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门: {}" , dept);
        deptService.add(dept);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("更新部门信息 : {}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
