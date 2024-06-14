package com.github.balazs60.decline.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskDto {
    private String task;
    private String inflectedAdjective;
    private String inflectedArticle;
    private List<String> articleAnswerOptions;
    private List<String> adjectiveAnswerOptions;
}
