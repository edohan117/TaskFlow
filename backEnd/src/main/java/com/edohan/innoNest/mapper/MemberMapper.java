package com.edohan.innoNest.mapper;

import java.util.*;

public interface MemberMapper {

    void insertMember(Map<String, String> member);

    String getPassword(String userId);

    String getUserNm(String userId);

    String getRole(String userId);

    List<Map<String, Object>> memberList();

    Map<String, Object> getProfile(String userId);
    
    Integer checkIdExists(String id);
}
