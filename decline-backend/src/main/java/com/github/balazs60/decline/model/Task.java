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
    private String article;
    private Case caseType;
    private Adjective adjective;
    private boolean isPlural;

    public String getCorrectNounForm() {

        if (this.getCaseType().equals(Case.GENITIVE) && !this.isPlural()) {
            return this.getNoun().getSingularGen();
        } else if (this.isPlural() && !this.getCaseType().equals(Case.DATIVE)) {
            return this.getNoun().getPluralNom();
        } else if (this.isPlural() && this.getCaseType().equals(Case.DATIVE)) {
            return this.getNoun().getPluralDat();
        } else {
            return this.getNoun().getSingularNom();
        }
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
