package com.edohan.innoNest.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edohan.innoNest.mapper.CategoryMapper;
import com.edohan.innoNest.service.CategoryService;

@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryMapper mapper;
    
    @Override
    public List<Map<String, Object>>categoryList(){
        return mapper.categoryList();
    };
}
