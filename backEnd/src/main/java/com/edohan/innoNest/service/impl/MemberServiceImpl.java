package com.edohan.innoNest.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edohan.innoNest.mapper.MemberMapper;
import com.edohan.innoNest.service.MemberService;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
    
    @Autowired
    private MemberMapper mapper;

    @Override
    @Transactional
    public Map<String, Object> register(Map<String, String> memberRequest) {
        String id = memberRequest.get("id");
        memberRequest.put("id", id); 
        mapper.insertMember(memberRequest);
        return Collections.singletonMap("status", "registered");
    }

    @Override
    @Transactional
    public Map<String, Object> login(String userId, String password) {
        String storedPassword = mapper.getPassword(userId);
        if (password.equals(storedPassword)) {
            String username = mapper.getUserNm(userId);
            String role = mapper.getRole(userId);

            return Map.of(
                "status", "success",
                "username", username,
                "role", role
            );
        } else {
            return Map.of("status", "failure", "message", "Invalid credentials");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> memberList() {
        return mapper.memberList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getProfile(String userId) {
        return mapper.getProfile(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkIdExists(String id) {
        Integer count = mapper.checkIdExists(id);
    return count != null && count > 0;
    }
}
