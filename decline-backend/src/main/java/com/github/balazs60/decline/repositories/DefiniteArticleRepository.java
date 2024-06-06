package com.github.balazs60.decline.repositories;

import com.github.balazs60.decline.model.articles.DefiniteArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefiniteArticleRepository extends JpaRepository<DefiniteArticle, Long> {
}
