package com.edohan.innoNest.controller;

import java.util.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edohan.innoNest.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    
    @Autowired
    private MemberService service;
    
    @GetMapping("/list")
    public List<Map<String, Object>> memberList() {
        return service.memberList();
    }
    
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> memberRequest) {
        return service.register(memberRequest);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        String id = loginRequest.get("id");
        String password = loginRequest.get("password");
        Map<String, Object> response = service.authenticate(id, password);

        if ("success".equals(response.get("status"))) {
            session.setAttribute("id", id);
            session.setAttribute("username", response.get("username"));
            session.setAttribute("role", response.get("role")); // 사용자 권한 저장
        }

        return response;
    }

    @GetMapping("/profile")
    public Map<String, Object> getProfile(HttpSession session) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return Collections.singletonMap("status", "error");
        }
        return service.getProfile(id);
    }
}
