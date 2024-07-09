package com.github.balazs60.decline.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerStatisticDto {
    private int numberOfWrongAnswers;
    private int numberOfGoodAnswers;
    private List<String> wrongAnsweredQuestions;
}
