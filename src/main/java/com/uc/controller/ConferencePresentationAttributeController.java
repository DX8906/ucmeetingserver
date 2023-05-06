package com.uc.controller;

import com.uc.entity.ConferencePresentationAttribute;
import com.uc.entity.Result;
import com.uc.service.ConferencePresentationAttributeService;
import com.uc.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/conferencePresentationAttribute")
@CrossOrigin
public class ConferencePresentationAttributeController {

    @Autowired
    private ConferencePresentationAttributeService conferencePresentationAttributeService;

    @PutMapping
    public Result update(@RequestBody ConferencePresentationAttribute conferencePresentationAttribute){
        conferencePresentationAttributeService.update(conferencePresentationAttribute);
        return Result.success();
    }

    @GetMapping
    public Result getAttribute(){
        ConferencePresentationAttribute conferencePresentationAttribute = conferencePresentationAttributeService.getAttribute();
        return Result.success(conferencePresentationAttribute);

    }
}
