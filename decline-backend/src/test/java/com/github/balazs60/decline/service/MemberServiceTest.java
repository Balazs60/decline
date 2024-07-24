package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.AnswerDataDto;
import com.github.balazs60.decline.dto.AnswerStatisticDto;
import com.github.balazs60.decline.model.UnSuccessfulTask;
import com.github.balazs60.decline.model.members.Member;
import com.github.balazs60.decline.model.members.Role;
import com.github.balazs60.decline.repositories.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private UnSuccessfulTaskService unSuccessfulTaskService;

    private MemberService memberService;
    private AutoCloseable autoCloseable;
    private Member member;
    private AnswerStatisticDto answerStatisticDto;
    private AnswerDataDto answerDataDto;
    private UnSuccessfulTask unSuccessfulTask;
    private List<UnSuccessfulTask> unSuccessfulTasks;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        memberService = new MemberService(memberRepository, unSuccessfulTaskService);
        member = new Member(1L, "User1", "1", "l@gmail.com", Role.USER, 0, 0, new ArrayList<>());
        answerStatisticDto = new AnswerStatisticDto();
        answerDataDto = new AnswerDataDto();
        unSuccessfulTask = new UnSuccessfulTask();
        unSuccessfulTasks = new ArrayList<>();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getMemberByName() {
        when(memberRepository.findMemberByName("User1")).thenReturn(member);
        Member foundMember = memberService.getMemberByName("User1");
        assertEquals(foundMember, member);
    }

    @Test
    void addAnswerDataWhenAnswerIsCorrect() {
        answerDataDto.setAnswerCorrect(true);
        answerDataDto.setMemberName(member.getName());
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);
        MemberService mockMemberService = mock(MemberService.class);

        when(memberRepository.findMemberByName(answerDataDto.getMemberName())).thenReturn(member);
        doNothing().when(mockMemberService).handleAnswerIsCorrect(member,answerDataDto);
        memberService.addAnswerData(answerDataDto);
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberArgumentCaptor.capture());

        Member capturedMember = memberArgumentCaptor.getValue();
        assertEquals(capturedMember, member);
    }
    @Test
    void addAnswerDataWhenAnswerIsInCorrect() {
        answerDataDto.setAnswerCorrect(false);
        answerDataDto.setMemberName(member.getName());
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);
        MemberService mockMemberService = mock(MemberService.class);

        when(memberRepository.findMemberByName(answerDataDto.getMemberName())).thenReturn(member);
        doNothing().when(mockMemberService).handleAnswerNotCorrect(answerDataDto,member);
        memberService.addAnswerData(answerDataDto);
        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberArgumentCaptor.capture());

        Member capturedMember = memberArgumentCaptor.getValue();
        assertEquals(capturedMember, member);
    }

    @Test
    void addAnswerDataWhenAnswerIsFalse() {
        answerDataDto.setAnswerCorrect(false);
        answerDataDto.setMemberName(member.getName());
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);

        when(memberRepository.findMemberByName("User1")).thenReturn(member);

        memberService.addAnswerData(answerDataDto);

        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberArgumentCaptor.capture());

        Member capturedMember = memberArgumentCaptor.getValue();
        Boolean isUnsuccessfulTaskSaved = capturedMember.getUnSuccessfulTasks().contains(answerDataDto.getUnSuccessfulTask());
        assertEquals(isUnsuccessfulTaskSaved, true);
    }

    @Test
    void addAnswerDataWhenAnswerIsTrue() {
        answerDataDto.setAnswerCorrect(true);
        answerDataDto.setMemberName(member.getName());
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);

        when(memberRepository.findMemberByName("User1")).thenReturn(member);
        int numberOfGoodAnswersBeforeAddTheAnswerData = member.getNumberOfGoodAnswers();
        memberService.addAnswerData(answerDataDto);

        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberArgumentCaptor.capture());

        Member capturedMember = memberArgumentCaptor.getValue();
        assertEquals(numberOfGoodAnswersBeforeAddTheAnswerData + 1, capturedMember.getNumberOfGoodAnswers());
    }


    @Test
    void getStatisticByUserName() {
        answerStatisticDto.setNumberOfGoodAnswers(member.getNumberOfGoodAnswers());
        answerStatisticDto.setNumberOfWrongAnswers(member.getNumberOfWrongAnswers());
        answerStatisticDto.setUnSuccessfulTasks(member.getUnSuccessfulTasks());

        when(memberRepository.findMemberByName("User1")).thenReturn(member);
        AnswerStatisticDto answerStatisticDtoByUserName = memberService.getStatisticByUserName("User1");
        assertEquals(answerStatisticDtoByUserName.getNumberOfGoodAnswers(), answerStatisticDto.getNumberOfGoodAnswers());
    }

    @Test
    void handleAnswerIsCorrect() {
        Member mockMember = mock(Member.class);
        unSuccessfulTasks.add(unSuccessfulTask);
        member.setUnSuccessfulTasks(unSuccessfulTasks);
        member.setUnSuccessfulTasks(unSuccessfulTasks);
        answerDataDto.setAnswerCorrect(true);
        answerDataDto.setMemberName(member.getName());
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);

        when(mockMember.checkMemberAlreadyTriedGivenTask(unSuccessfulTask)).thenReturn(unSuccessfulTask);
        doNothing().when(unSuccessfulTaskService).removeUnsuccessfulTask(any(UnSuccessfulTask.class));
        memberService.handleAnswerIsCorrect(member, answerDataDto);
        assertEquals(1, member.getNumberOfGoodAnswers());
    }

    @Test
    void handleAnswerNotCorrectWhenMemberDidntTryGivenTask() {
        Member mockMember = mock(Member.class);
        answerDataDto.setAnswerCorrect(false);
        answerDataDto.setMemberName(member.getName());
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);
        member.setUnSuccessfulTasks(unSuccessfulTasks);
        int numberOfWrongAnswersBeforeAddNewAnswer = member.getNumberOfWrongAnswers();
        int sizeOfUnsuccessfulTaskListBeforeAddNewAnswer = member.getUnSuccessfulTasks().size();

        when(mockMember.checkMemberAlreadyTriedGivenTask(answerDataDto.getUnSuccessfulTask())).thenReturn(null);
        doNothing().when(unSuccessfulTaskService).addUnsuccessfulTask(any(AnswerDataDto.class),any(Member.class));
        memberService.handleAnswerNotCorrect(answerDataDto,member);

        assertEquals(numberOfWrongAnswersBeforeAddNewAnswer +1,member.getNumberOfWrongAnswers());
        assertEquals(sizeOfUnsuccessfulTaskListBeforeAddNewAnswer +1,member.getUnSuccessfulTasks().size());
    }

    @Test
    void handleAnswerNotCorrectWhenMemberTriedGivenTask() {
        Member mockMember = mock(Member.class);
        answerDataDto.setAnswerCorrect(false);
        answerDataDto.setMemberName(member.getName());
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);
        unSuccessfulTasks.add(unSuccessfulTask);
        member.setUnSuccessfulTasks(unSuccessfulTasks);
        member.setNumberOfWrongAnswers(1);
        int numberOfWrongAnswersBeforeAddNewAnswer = member.getNumberOfWrongAnswers();
        int sizeOfUnsuccessfulTaskListBeforeAddNewAnswer = member.getUnSuccessfulTasks().size();

        when(mockMember.checkMemberAlreadyTriedGivenTask(answerDataDto.getUnSuccessfulTask())).thenReturn(unSuccessfulTask);
        memberService.handleAnswerNotCorrect(answerDataDto,member);

        assertEquals(numberOfWrongAnswersBeforeAddNewAnswer, member.getNumberOfWrongAnswers());
        assertEquals(sizeOfUnsuccessfulTaskListBeforeAddNewAnswer, member.getUnSuccessfulTasks().size());
    }

    @Test
    void removeUnsuccessfulTask() {
        unSuccessfulTasks.add(unSuccessfulTask);
        member.setUnSuccessfulTasks(unSuccessfulTasks);
        member.setNumberOfWrongAnswers(1);
        int numberOfWrongAnswersBeforeRemoveUnsuccessfulTask = member.getNumberOfWrongAnswers();
        int sizeOfUnsuccessfulTaskListBeforeRemoveTask = member.getUnSuccessfulTasks().size();

        doNothing().when(unSuccessfulTaskService).removeUnsuccessfulTask(any(UnSuccessfulTask.class));
        memberService.removeUnsuccessfulTask(unSuccessfulTasks,unSuccessfulTask,member);

        assertEquals(numberOfWrongAnswersBeforeRemoveUnsuccessfulTask -1,member.getNumberOfWrongAnswers());
        assertEquals(sizeOfUnsuccessfulTaskListBeforeRemoveTask -1,member.getUnSuccessfulTasks().size());
    }
}
