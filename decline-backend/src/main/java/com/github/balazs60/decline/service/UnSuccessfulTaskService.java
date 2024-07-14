package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.AnswerDataDto;
import com.github.balazs60.decline.model.UnSuccessfulTask;
import com.github.balazs60.decline.model.members.Member;
import com.github.balazs60.decline.repositories.UnSuccessfulTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class UnSuccessfulTaskService {

    private UnSuccessfulTaskRepository unSuccessfulTaskRepository;

    public UnSuccessfulTaskService(UnSuccessfulTaskRepository unSuccessfulTaskRepository) {
        this.unSuccessfulTaskRepository = unSuccessfulTaskRepository;
    }

    public void addUnsuccessfulTask(AnswerDataDto answerDataDto, Member member){
        UnSuccessfulTask unSuccessfulTask = new UnSuccessfulTask();
        unSuccessfulTask.setQuestion(answerDataDto.getUnSuccessfulTask().getQuestion());
        unSuccessfulTask.setInflectedAdjective(answerDataDto.getUnSuccessfulTask().getInflectedAdjective());
        unSuccessfulTask.setInflectedArticle(answerDataDto.getUnSuccessfulTask().getInflectedArticle());
        unSuccessfulTask.setArticleAnswerOptions(answerDataDto.getUnSuccessfulTask().getArticleAnswerOptions());
        unSuccessfulTask.setAdjectiveAnswerOptions(answerDataDto.getUnSuccessfulTask().getAdjectiveAnswerOptions());
        unSuccessfulTask.setMember(member);
        unSuccessfulTaskRepository.save(unSuccessfulTask);
    }
}
