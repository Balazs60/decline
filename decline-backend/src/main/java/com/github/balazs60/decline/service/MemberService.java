package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.AnswerDataDto;
import com.github.balazs60.decline.dto.AnswerStatisticDto;
import com.github.balazs60.decline.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import com.github.balazs60.decline.model.members.Member;

import java.util.List;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getMemberByName(String memberName){
        return memberRepository.findMemberByName(memberName);
    }

    public void addAnswerData(AnswerDataDto answerDataDto) {
        Member member = memberRepository.findMemberByName(answerDataDto.getMemberName());
        if(!answerDataDto.isAnswerCorrect()){
            int numberOfWrongAnswers = member.getNumberOfWrongAnswers();
            numberOfWrongAnswers++;
            member.setNumberOfWrongAnswers(numberOfWrongAnswers);
            List<String> unsuccessfulQuestions = member.getUnsuccessfulQuestions();
            unsuccessfulQuestions.add(answerDataDto.getQuestion());
            member.setUnsuccessfulQuestions(unsuccessfulQuestions);
        } else {
            int numberOfGoodAnswers = member.getNumberOfGoodAnswers();
            numberOfGoodAnswers++;
            member.setNumberOfGoodAnswers(numberOfGoodAnswers);
        }
        memberRepository.save(member);
    }

    public AnswerStatisticDto getStatisticByUserName(String userName){
        AnswerStatisticDto answerStatisticDto = new AnswerStatisticDto();
        Member member = memberRepository.findMemberByName(userName);
        answerStatisticDto.setNumberOfWrongAnswers(member.getNumberOfWrongAnswers());
        answerStatisticDto.setNumberOfGoodAnswers(member.getNumberOfGoodAnswers());
        answerStatisticDto.setWrongAnsweredQuestions(member.getUnsuccessfulQuestions());
        return answerStatisticDto;
    }
}
