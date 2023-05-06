package com.uc.mapper;

import com.uc.entity.ConferencePresentationAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ConferencePresentationAttributeMapper {

    void update(ConferencePresentationAttribute conferencePresentationAttribute);

    @Select("select * from conference_presentation_attribute where id = 1")
    ConferencePresentationAttribute select();
}
