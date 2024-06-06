package com.github.balazs60.decline.model;

import com.github.balazs60.decline.model.articles.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Noun noun;
    private Article article;
    private Case caseType;
    private Adjective adjective;
    private boolean isPlural;


}
