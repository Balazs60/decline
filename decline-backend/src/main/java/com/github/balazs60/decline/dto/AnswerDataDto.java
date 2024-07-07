package com.github.balazs60.decline.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDataDto {
    @JsonProperty("isAnswerCorrect")
    private boolean isAnswerCorrect;
    private String question;
    private String memberName;
}
