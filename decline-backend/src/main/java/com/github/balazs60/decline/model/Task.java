package com.github.balazs60.decline.model;

import com.github.balazs60.decline.model.adjective.Adjective;
import com.github.balazs60.decline.model.strategy.nounFormStrategy.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Task {

    private Noun noun;
    private String article;
    private Case caseType;
    private Adjective adjective;
    private boolean isPlural;
    private List<NounFormStrategy> strategies;

    public Task() {
        this.strategies = new ArrayList<>();
        this.strategies.add(new GenitiveSingularStrategy());
        this.strategies.add(new PluralNonDativeStrategy());
        this.strategies.add(new PluralDativeStrategy());
        this.strategies.add(new DefaultNounFormStrategy());
    }

    public String getCorrectNounForm() {
        for (NounFormStrategy strategy : strategies) {
            String form = strategy.getForm(noun, caseType, isPlural);
            if (form != null) {
                return form;
            }
        }
        return null;
    }


}
