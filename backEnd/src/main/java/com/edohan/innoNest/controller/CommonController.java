package com.edohan.innoNest.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.edohan.innoNest.service.CommonService;

@RestController
@RequestMapping("/api/common")
public class CommonController {
    
    @Autowired
    private CommonService service;

    @GetMapping("/code/{type}")
    public List<Map<String, Object>> codeList(@PathVariable("type") String type) {
        return service.codeList(type);
    }
    
    @GetMapping("/areaCd/{regionCode}")
    public List<Map<String, Object>> areaCdList(@PathVariable("regionCode") String regionCode) {
        return service.areaCdList(regionCode);
    }
}
