package com.github.balazs60.decline.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDataDto {
    private String answerType;
    private String question;
    private String memberName;
}
