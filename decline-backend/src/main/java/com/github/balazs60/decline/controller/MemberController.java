package com.github.balazs60.decline.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balazs60.decline.dto.AnswerDataDto;
import com.github.balazs60.decline.dto.AnswerStatisticDto;
import com.github.balazs60.decline.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PatchMapping("/member/statistic")
    public ResponseEntity<Void> UpdateStatistic(@RequestBody AnswerDataDto answerDataDto) {
        try {
            memberService.addAnswerData(answerDataDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/member/statistic/{userName}")
    public AnswerStatisticDto getStatisticByUserName(@PathVariable String userName) {
        AnswerStatisticDto answerStatisticDto = memberService.getStatisticByUserName(userName);
        // Add this line to log the JSON response
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(answerStatisticDto);
            System.out.println("JSON Response: " + jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return answerStatisticDto;
    }
}
