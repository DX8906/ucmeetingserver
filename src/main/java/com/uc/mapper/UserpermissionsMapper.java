package com.uc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserpermissionsMapper {

    @Select("select name from userpermissions")
    List<String> list();
}
