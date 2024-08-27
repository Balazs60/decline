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
        memberService.addAnswerData(answerDataDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/member/statistic/{userName}")
    public AnswerStatisticDto getStatisticByUserName(@PathVariable String userName) {

        AnswerStatisticDto answerStatisticDto = memberService.getStatisticByUserName(userName);
        return answerStatisticDto;
    }
}
