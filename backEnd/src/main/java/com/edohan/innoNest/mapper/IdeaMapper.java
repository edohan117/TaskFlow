package com.edohan.innoNest.mapper;

import java.util.*;

public interface IdeaMapper {
    List<Map<String, Object>> ideaList();
    List<Map<String, Object>> myIdeaList(String userId);
    Map<String, Object> getIdeaDetail(int id);
    void saveIdea(String userId, String title, String content, String tags, String category);
    void updateIdea(int id, String title, String content);
    void deleteIdea(int id);
    void incViewCount(int id);
    void addReaction(Map<String, Object> params);  // 반응 추가 또는 업데이트
    void removeReaction(Map<String, Object> params); // 반응 삭제
}
