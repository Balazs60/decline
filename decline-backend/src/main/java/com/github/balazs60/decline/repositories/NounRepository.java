package com.github.balazs60.decline.repositories;

import com.github.balazs60.decline.model.Noun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NounRepository extends JpaRepository<Noun, Long> {
}
