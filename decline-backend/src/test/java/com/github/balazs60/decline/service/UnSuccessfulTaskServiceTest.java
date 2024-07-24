package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.AnswerDataDto;
import com.github.balazs60.decline.model.UnSuccessfulTask;
import com.github.balazs60.decline.model.members.Member;
import com.github.balazs60.decline.repositories.UnSuccessfulTaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class UnSuccessfulTaskServiceTest {
    @Mock
    private UnSuccessfulTaskRepository unSuccessfulTaskRepository;
    private UnSuccessfulTaskService unSuccessfulTaskService;
    private AutoCloseable autoCloseable;
    private UnSuccessfulTask unSuccessfulTask;
    private AnswerDataDto answerDataDto;
    private Member member;
    private List<String> articleAnswerOptions;
    private List<String> adjectiveAnswerOptions;
    private String inflectedAdjective;
    private String inflectedArticle;
    private String question;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        unSuccessfulTaskService = new UnSuccessfulTaskService(unSuccessfulTaskRepository);
        member = new Member();
        answerDataDto = new AnswerDataDto();
        unSuccessfulTask = new UnSuccessfulTask();
        articleAnswerOptions = new ArrayList<>();
        adjectiveAnswerOptions = new ArrayList<>();
        inflectedAdjective = "schön";
        inflectedArticle = "Dem";
        question = "Question";
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void addUnsuccessfulTask() {
        unSuccessfulTask.setInflectedArticle(inflectedArticle);
        unSuccessfulTask.setInflectedAdjective(inflectedAdjective);
        unSuccessfulTask.setAdjectiveAnswerOptions(adjectiveAnswerOptions);
        unSuccessfulTask.setArticleAnswerOptions(articleAnswerOptions);
        unSuccessfulTask.setQuestion(question);
        answerDataDto.setUnSuccessfulTask(unSuccessfulTask);

        unSuccessfulTaskService.addUnsuccessfulTask(answerDataDto, member);
        ArgumentCaptor<UnSuccessfulTask> unSuccessfulTaskArgumentCaptor = ArgumentCaptor.forClass(UnSuccessfulTask.class);
        verify(unSuccessfulTaskRepository).save(unSuccessfulTaskArgumentCaptor.capture());

        UnSuccessfulTask capturedUnsuccessfulTask = unSuccessfulTaskArgumentCaptor.getValue();
        assertEquals(capturedUnsuccessfulTask.getQuestion(), unSuccessfulTask.getQuestion());
        assertEquals(capturedUnsuccessfulTask.getInflectedAdjective(), unSuccessfulTask.getInflectedAdjective());
        assertEquals(capturedUnsuccessfulTask.getInflectedArticle(), unSuccessfulTask.getInflectedArticle());
        assertEquals(capturedUnsuccessfulTask.getAdjectiveAnswerOptions(), unSuccessfulTask.getArticleAnswerOptions());
        assertEquals(capturedUnsuccessfulTask.getAdjectiveAnswerOptions(), unSuccessfulTask.getAdjectiveAnswerOptions());
    }

    @Test
    void removeUnsuccessfulTask() {
        unSuccessfulTaskService.removeUnsuccessfulTask(unSuccessfulTask);

        ArgumentCaptor<UnSuccessfulTask> unSuccessfulTaskArgumentCaptor =
                ArgumentCaptor.forClass(UnSuccessfulTask.class);

        verify(unSuccessfulTaskRepository).delete(unSuccessfulTaskArgumentCaptor.capture());
        UnSuccessfulTask capturedUnSuccessfulTask = unSuccessfulTaskArgumentCaptor.getValue();

        assertEquals(capturedUnSuccessfulTask,unSuccessfulTask);
    }
}
