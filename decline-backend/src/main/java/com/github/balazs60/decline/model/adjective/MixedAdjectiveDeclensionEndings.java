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
@Table(name = "mixed_adjective_declension" )
public class MixedAdjectiveDeclensionEndings extends AdjectiveDeclensionEndings {
    @Id
    @GeneratedValue
    private Long id;
}
