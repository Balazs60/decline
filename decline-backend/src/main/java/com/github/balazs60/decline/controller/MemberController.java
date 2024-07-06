package com.github.balazs60.decline.controller;

import com.github.balazs60.decline.dto.AnswerDataDto;
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
        System.out.println("wauwau");
        try {
            System.out.println("answertype " + answerDataDto.getAnswerType());
memberService.addAnswerData(answerDataDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
