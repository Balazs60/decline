package com.github.balazs60.decline.model.articles;

import com.github.balazs60.decline.model.Case;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Article {

    private Case caseType;
    private String masculine;
    private String feminine;
    private String neutral;
    private String plural;

}
