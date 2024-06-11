package com.github.balazs60.decline.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private String task;
    private String inflectedAdjective;
    private String inflectedArticle;
}
