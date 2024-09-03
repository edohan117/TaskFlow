package com.edohan.innoNest.service.impl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edohan.innoNest.mapper.ChallengeMapper;
import com.edohan.innoNest.service.ChallengeService;

@Service("ChallengeService")
public class ChallengeServiceImpl implements ChallengeService {
    
    @Autowired
    private ChallengeMapper mapper;
    
    @Override
    public List<Map<String, Object>> challengeList() {
        return mapper.challengeList();
    }

    @Override
    public List<Map<String, Object>> myChallengeList(String userId) {
        return mapper.myChallengeList(userId);
    }

    @Override
    public Map<String, Object> getChallengeDetail(int id) {
        Map<String, Object> ideaDetail = mapper.getChallengeDetail(id);
        
        if (ideaDetail != null) {
            // LIKE_COUNT와 DISLIKE_COUNT를 Long으로 받아오고, 필요에 따라 Integer로 변환
            Long likeCountLong = ((Number) ideaDetail.get("LIKE_COUNT")).longValue();
            Integer likeCount = likeCountLong != null ? likeCountLong.intValue() : 0;

            Long dislikeCountLong = ((Number) ideaDetail.get("DISLIKE_COUNT")).longValue();
            Integer dislikeCount = dislikeCountLong != null ? dislikeCountLong.intValue() : 0;

            ideaDetail.put("LIKE_COUNT", likeCount);
            ideaDetail.put("DISLIKE_COUNT", dislikeCount);
        }

        return ideaDetail;
    }

    @Override
    public void saveChallenge(String userId, String title, String content, String tags, String category) {
        mapper.saveChallenge(userId, title, content, tags, category);
    }

    @Override
    public void updateChallenge(int id, String title, String content) {
        mapper.updateChallenge(id, title, content);
    }

    @Override
    public void deleteChallenge(int id) {
        mapper.deleteChallenge(id);
    }

    @Override
    public void incViewCount(int id) {
        mapper.incViewCount(id);
    }

    @Override
    public void addReaction(String userId, int ideaId, String reactionType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("ideaId", ideaId);
        params.put("reactionType", reactionType);

        // 반응 추가 또는 업데이트
        mapper.addReaction(params);
    }

    @Override
    public void removeReaction(String userId, int ideaId, String reactionType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("ideaId", ideaId);
        params.put("reactionType", reactionType);

        // 반응 삭제
        mapper.removeReaction(params);
    }

}
