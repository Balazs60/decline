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
@Table(name = "weak_adjective_declension" )
public class WeakAdjectiveDeclensionEndings extends AdjectiveDeclensionEndings {
    @Id
    @GeneratedValue
    private Long id;
}
