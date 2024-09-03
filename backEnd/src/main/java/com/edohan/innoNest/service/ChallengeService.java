package com.edohan.innoNest.service;

import java.util.*;

public interface ChallengeService {
    List<Map<String, Object>> challengeList();
    List<Map<String, Object>> myChallengeList(String userId);
    Map<String, Object> getChallengeDetail(int id);
    void saveChallenge(String userId, String title, String content, String tags, String category);
    void updateChallenge(int id, String title, String content);
    void deleteChallenge(int id);
    void incViewCount(int id);

    void addReaction(String userId, int ideaId, String reactionType);
    void removeReaction(String userId, int ideaId, String reactionType);
}
