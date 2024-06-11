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
        if (article.equals("der")) {
            return this.masculineEnding;
        } else if (article.equals("die")) {
            return this.feminineEnding;
        } else {
            return this.neutralEnding;
        }
    }
}
