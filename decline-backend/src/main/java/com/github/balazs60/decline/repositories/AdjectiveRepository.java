package com.github.balazs60.decline.repositories;

import com.github.balazs60.decline.model.adjective.Adjective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjectiveRepository extends JpaRepository<Adjective, Long> {
}
