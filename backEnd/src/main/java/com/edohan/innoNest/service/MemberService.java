package com.edohan.innoNest.service;

import java.util.*;

public interface MemberService {
    Map<String, Object> register(Map<String, String> memberRequest);
    Map<String, Object> login(String userId, String password);
    List<Map<String, Object>> memberList();
    Map<String, Object> getProfile(String userId);
    boolean checkIdExists(String id);
}
