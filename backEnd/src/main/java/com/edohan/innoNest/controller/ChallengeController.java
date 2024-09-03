package com.edohan.innoNest.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edohan.innoNest.service.ChallengeService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {
    
    @Autowired
    private ChallengeService service;

    @GetMapping("/list")
    public List<Map<String, Object>> challengeList() {
        return service.challengeList();
    }

    @GetMapping("/myList")
    public List<Map<String, Object>> myChallengeList(@RequestHeader(value = "User-Id", required = false) String userId) {
        List<Map<String, Object>>  myListideaList = service.myChallengeList(userId);
        return myListideaList;
    }

    @GetMapping("/detail/{id}")
    public Map<String, Object> getChallengeDetail(@PathVariable("id") int id, HttpServletRequest request, @RequestHeader(value = "User-Id", required = false) String userId) {
        Map<String, Object> ideaDetail = service.getChallengeDetail(id);
    
        if (userId != null && !userId.equals(ideaDetail.get("WRITER"))) {
                service.incViewCount(id);
                ideaDetail = service.getChallengeDetail(id);  // 조회수 증가 후 다시 데이터를 가져옵니다.
        }
    
        return ideaDetail;
    }

    // 아이디어 제출
    @PostMapping("/submit")
    public void saveChallenge(@RequestBody Map<String, Object> idea) {
        String userId = (String) idea.get("userId"); 
        String title = (String) idea.get("title");
        String content = (String) idea.get("content");
        String category = (String) idea.get("category");
        String tags = (String) idea.get("tags");
        service.saveChallenge(userId, title, content, tags, category);
    }

    // 아이디어 수정
    @PutMapping("/update/{id}")
    public void updateChallenge(@PathVariable("id") int id, @RequestBody Map<String, String> idea) {
        String title = idea.get("TITLE");
        String content = idea.get("CONTENT");
        service.updateChallenge(id, title, content);
    }

    // 아이디어 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteChallenge(@PathVariable("id") int id) {
        service.deleteChallenge(id);
    }

    // 좋아요/싫어요 추가
    @PostMapping("/reaction/{id}")
    public ResponseEntity<String> addReaction(@PathVariable("id") int id, @RequestBody Map<String, String> request) {
        Map<String, Object> ideaDetail = service.getChallengeDetail(id);
    
        String userId = request.get("userId");
        String reactionType = request.get("reactionType"); // "LIKE" or "DISLIKE"

        if (userId != null && !userId.isEmpty() && !userId.equals(ideaDetail.get("WRITER"))) {
            service.addReaction(userId, id, reactionType);
            return ResponseEntity.ok("Reaction added successfully.");
        } else {
            return ResponseEntity.badRequest().body("UserId is required.");
        }
    }

    // 좋아요/싫어요 취소
    @DeleteMapping("/reaction/{id}")
    public void removeReaction(@PathVariable("id") int id, @RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String reactionType = request.get("reactionType"); // "LIKE" or "DISLIKE"
        
        service.removeReaction(userId, id, reactionType);
    }
}
