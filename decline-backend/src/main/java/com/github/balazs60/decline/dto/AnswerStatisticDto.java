package com.github.balazs60.decline.dto;

import com.github.balazs60.decline.model.UnSuccessfulTask;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerStatisticDto {
    private int numberOfWrongAnswers;
    private int numberOfGoodAnswers;
    private List<UnSuccessfulTask> wrongAnsweredQuestions;
}
