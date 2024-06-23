package com.github.balazs60.decline.model.strategy.nounFormStrategy;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;

public class GenitiveSingularStrategy implements NounFormStrategy {
    @Override
    public String getForm(Noun noun, Case caseType, boolean isPlural) {
        if (caseType.equals(Case.GENITIVE) && !isPlural) {
            return noun.getSingularGen();
        }
        return null;
    }
}
