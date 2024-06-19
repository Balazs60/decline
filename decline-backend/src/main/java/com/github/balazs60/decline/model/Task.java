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
//    public String getCorrectNounForm() {
//
//        if (this.getCaseType().equals(Case.GENITIVE) && !this.isPlural()) {
//            return this.getNoun().getSingularGen();
//        } else if (this.isPlural() && !this.getCaseType().equals(Case.DATIVE)) {
//            return this.getNoun().getPluralNom();
//        } else if (this.isPlural() && this.getCaseType().equals(Case.DATIVE)) {
//            return this.getNoun().getPluralDat();
//        } else {
//            return this.getNoun().getSingularNom();
//        }
//    }

    public String getCorrectNounForm() {
        for (NounFormStrategy strategy : strategies) {
            String form = strategy.getForm(noun, caseType, isPlural);
            if (form != null) {
                return form;
            }
        }
        return null;
    }

    public String getFirstCharOfTheArticle() {
        if (this.getArticle().charAt(0) == 'd') {
            return "D...";
        } else if (this.getArticle().charAt(0) == 'e') {
            return "E...";
        } else {
            return "K...";
        }
    }

}
