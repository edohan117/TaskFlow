package com.edohan.fuse.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edohan.fuse.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    
    @Autowired
    private CategoryService service;

    @GetMapping("/list")
    public List<Map<String, Object>> ideaList() {
        return service.categoryList();
    }
}
