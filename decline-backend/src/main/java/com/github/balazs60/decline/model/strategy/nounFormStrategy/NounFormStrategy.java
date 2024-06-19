package com.github.balazs60.decline.model.strategy.nounFormStrategy;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;

public interface NounFormStrategy {
    String getForm(Noun noun, Case caseType, boolean isPlural);

}
