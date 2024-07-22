package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.AnswerDataDto;
import com.github.balazs60.decline.dto.AnswerStatisticDto;
import com.github.balazs60.decline.model.UnSuccessfulTask;
import com.github.balazs60.decline.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import com.github.balazs60.decline.model.members.Member;

import java.util.List;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private UnSuccessfulTaskService unSuccessfulTaskService;

    public MemberService(MemberRepository memberRepository, UnSuccessfulTaskService unSuccessfulTaskService) {

        this.memberRepository = memberRepository;
        this.unSuccessfulTaskService = unSuccessfulTaskService;
    }

    public Member getMemberByName(String memberName) {
        return memberRepository.findMemberByName(memberName);
    }

    public void addAnswerData(AnswerDataDto answerDataDto) {
        Member member = memberRepository.findMemberByName(answerDataDto.getMemberName());
        if (!answerDataDto.isAnswerCorrect()) {
            handleAnswerNotCorrect(answerDataDto, member);
        } else {
            handleAnswerIsCorrect(member, answerDataDto);
        }
        memberRepository.save(member);
    }

    public AnswerStatisticDto getStatisticByUserName(String userName) {
        AnswerStatisticDto answerStatisticDto = new AnswerStatisticDto();
        Member member = memberRepository.findMemberByName(userName);
        answerStatisticDto.setNumberOfWrongAnswers(member.getNumberOfWrongAnswers());
        answerStatisticDto.setNumberOfGoodAnswers(member.getNumberOfGoodAnswers());
        answerStatisticDto.setUnSuccessfulTasks(member.getUnSuccessfulTasks());
        return answerStatisticDto;
    }

    public void handleAnswerIsCorrect(Member member, AnswerDataDto answerDataDto) {
        UnSuccessfulTask givenTaskWhatMemberAlreadyTried = member.checkMemberAlreadyTriedGivenTask(answerDataDto.getUnSuccessfulTask());
        if ( givenTaskWhatMemberAlreadyTried!= null) {
            List<UnSuccessfulTask> unSuccessfulTasks = member.getUnSuccessfulTasks();
            removeUnsuccessfulTask(unSuccessfulTasks,givenTaskWhatMemberAlreadyTried,member);
        }
        int numberOfGoodAnswers = member.getNumberOfGoodAnswers();
        numberOfGoodAnswers++;
        member.setNumberOfGoodAnswers(numberOfGoodAnswers);
    }

    public void handleAnswerNotCorrect(AnswerDataDto answerDataDto, Member member) {
        if (member.checkMemberAlreadyTriedGivenTask(answerDataDto.getUnSuccessfulTask()) == null) {
            unSuccessfulTaskService.addUnsuccessfulTask(answerDataDto, member);
            int numberOfWrongAnswers = member.getNumberOfWrongAnswers();
            numberOfWrongAnswers++;
            member.setNumberOfWrongAnswers(numberOfWrongAnswers);
            List<UnSuccessfulTask> unSuccessfulTasks = member.getUnSuccessfulTasks();
            unSuccessfulTasks.add(answerDataDto.getUnSuccessfulTask());
            member.setUnSuccessfulTasks(unSuccessfulTasks);
        }
    }

    public void removeUnsuccessfulTask(List<UnSuccessfulTask> unSuccessfulTasks,
                                       UnSuccessfulTask taskForRemove,
                                       Member member) {
        unSuccessfulTasks.remove(taskForRemove);
        member.setUnSuccessfulTasks(unSuccessfulTasks);
        unSuccessfulTaskService.removeUnsuccessfulTask(taskForRemove);
        int numberOfWrongAnswers = member.getNumberOfWrongAnswers();
        numberOfWrongAnswers -= 1;
        member.setNumberOfWrongAnswers(numberOfWrongAnswers);
    }
}
