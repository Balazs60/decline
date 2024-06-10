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
}
