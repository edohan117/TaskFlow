package com.edohan.innoNest.mapper;

import java.util.*;

public interface ChallengeMapper {
    List<Map<String, Object>> challengeList();
    List<Map<String, Object>> myChallengeList(String userId);
    Map<String, Object> getChallengeDetail(int id);
    void saveChallenge(String userId, String title, String content, String tags, String category);
    void updateChallenge(int id, String title, String content);
    void deleteChallenge(int id);
    void incViewCount(int id);
    void addReaction(Map<String, Object> params);  // 반응 추가 또는 업데이트
    void removeReaction(Map<String, Object> params); // 반응 삭제
}
