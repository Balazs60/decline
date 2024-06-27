package com.github.balazs60.decline.model.adjective;

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
public abstract class AdjectiveDeclensionEndings {

    private Case caseType;
    private String masculineEnding;
    private String feminineEnding;
    private String neutralEnding;
    private String pluralEnding;

    public String getCorrectEndingOfAdjective(String caseType, String article, boolean nounIsPlural){

        String correctEnding = null;
        if (this.caseType.name().equals(caseType)) {
            if (nounIsPlural) {
                return this.pluralEnding;
            } else {
                correctEnding = this.getCorrectEndingByGender(article);
            }
        }
        return correctEnding;
    }

    public String getCorrectEndingByGender(String article) {
        String correctEnding = null;
        if (article.equals("der")) {
            correctEnding = this.masculineEnding;
        } else if (article.equals("die")) {
            correctEnding = this.feminineEnding;
        } else {
            correctEnding = this.neutralEnding;
        }
        return correctEnding;
    }
}
