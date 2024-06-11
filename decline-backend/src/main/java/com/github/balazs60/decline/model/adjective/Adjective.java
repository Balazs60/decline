package com.github.balazs60.decline.model.adjective;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adjective" )
public class Adjective {

    @Id
    @GeneratedValue
    private Long id;

    private String normalAdjectiveForm;
    private String adjectiveFormWithEEnd;
    private String adjectiveFormWithREnd;
    private String adjectiveFormWithMEnd;
    private String adjectiveFormWithNEnd;
    private String adjectiveFormWithSEnd;
}
