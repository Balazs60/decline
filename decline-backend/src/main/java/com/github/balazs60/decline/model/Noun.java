package com.github.balazs60.decline.model;

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
@Table(name = "noun" )
public class Noun {

    @Id
    @GeneratedValue
    private Long id;

    private String article;
    private String normalNounForm;
    private String nounFormWithEEnd;
    private String nounFormWithNEnd;
    private String nounFormSEnd;
}
