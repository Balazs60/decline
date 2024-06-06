package com.github.balazs60.decline.repositories;

import com.github.balazs60.decline.model.articles.IndefiniteArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndefiniteArticleRepository extends JpaRepository<IndefiniteArticle, Long> {
}
