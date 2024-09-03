package com.edohan.fuse.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edohan.fuse.mapper.MainMapper;
import com.edohan.fuse.service.MainService;

@Service("MainService")
public class MainServiceImpl implements MainService {
    
    @Autowired
    private MainMapper mapper;
    
    @Override
    public List<Map<String, Object>> highViewCountSelect(){
        return mapper.highViewCountSelect();
    };
    @Override
    public List<Map<String, Object>> recentPostsSelect(){
        return mapper.recentPostsSelect();
    };
    @Override
    public List<Map<String, Object>> topLikedPostsSelect(){
        return mapper.topLikedPostsSelect();
    };
}
