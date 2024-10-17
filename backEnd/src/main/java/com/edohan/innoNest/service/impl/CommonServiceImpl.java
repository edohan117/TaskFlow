package com.edohan.innoNest.service.impl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edohan.innoNest.mapper.CommonMapper;
import com.edohan.innoNest.service.CommonService;

@Service("CommonService")
public class CommonServiceImpl implements CommonService {
    
    @Autowired
    private CommonMapper mapper;
    
    @Override
    public List<Map<String, Object>> codeList(String type) {
        return mapper.codeList(type);
    }
    
    @Override
    public List<Map<String, Object>> areaCdList(String regionCode) {
        return mapper.areaCdList(regionCode);
    }

}
