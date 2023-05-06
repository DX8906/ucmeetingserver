package com.uc.service.impl;

import com.uc.entity.ConferencePresentationAttribute;
import com.uc.mapper.ConferencePresentationAttributeMapper;
import com.uc.service.ConferencePresentationAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConferencePresentationAttributeServiceImpl implements ConferencePresentationAttributeService {

    @Autowired
    private ConferencePresentationAttributeMapper conferencePresentationAttributeMapper;

    @Override
    public void update(ConferencePresentationAttribute conferencePresentationAttribute) {
        conferencePresentationAttribute.setId(1);
        conferencePresentationAttribute.setUpdateTime(LocalDateTime.now());
        conferencePresentationAttributeMapper.update(conferencePresentationAttribute);
    }

    @Override
    public ConferencePresentationAttribute getAttribute() {
        return conferencePresentationAttributeMapper.select();
    }
}
