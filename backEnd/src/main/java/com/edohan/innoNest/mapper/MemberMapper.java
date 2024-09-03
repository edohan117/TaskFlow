package com.edohan.innoNest.mapper;

import java.util.*;

public interface MemberMapper {

    void insertMember(Map<String, String> member);

    String getPasswordByUsername(String id);

    String getUsernameById(String id);

    String getRoleById(String id);

    List<Map<String, Object>> memberList();

    // 사용자 프로필 정보 조회
    Map<String, Object> getProfileById(String id);
}
