package com.github.balazs60.decline.model.strategy.nounFormStrategy;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.model.strategy.nounFormStrategy.NounFormStrategy;

public class PluralNonDativeStrategy implements NounFormStrategy {
    @Override
    public String getForm(Noun noun, Case caseType, boolean isPlural) {
        if (isPlural && !caseType.equals(Case.DATIVE)) {
            return noun.getPluralNom();
        }
        return null;
    }
}
