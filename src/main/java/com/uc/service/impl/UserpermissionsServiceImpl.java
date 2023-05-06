package com.uc.service.impl;

import com.uc.mapper.UserpermissionsMapper;
import com.uc.service.UserpermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserpermissionsServiceImpl implements UserpermissionsService {

    @Autowired
    private UserpermissionsMapper userpermissionsMapper;
    @Override
    public List<String> list() {
        return userpermissionsMapper.list();
    }
}
