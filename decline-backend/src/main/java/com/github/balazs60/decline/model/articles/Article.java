package com.github.balazs60.decline.model.articles;

import com.github.balazs60.decline.model.Case;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Article {

    private Case caseType;
    private String masculine;
    private String feminine;
    private String neutral;
    private String plural;

    public String getCorrectArticleByCaseAndGender(String nounOriginalArticle, Case caseType, boolean nounIsPlural) {
        String correctArticle = null;
        if (this.caseType == caseType) {
            if (nounIsPlural) {
                return this.plural;
            } else {
                correctArticle = this.getCorrectArticleByGender(nounOriginalArticle);

            }
        }
        return correctArticle;
    }

    public String getCorrectArticleByGender(String nounOriginalArticle) {
        System.out.println("article from get correct art...method " + nounOriginalArticle);
        if (nounOriginalArticle.equals("der")) {
            System.out.println("this. masculine article " + this.masculine);
            return this.masculine;
        } else if (nounOriginalArticle.equals("die")) {
            System.out.println("this. feminine article " + this.feminine);
            return this.feminine;
        } else {
            System.out.println("this. neutral article " + this.neutral);
            return this.neutral;
        }
    }

}
