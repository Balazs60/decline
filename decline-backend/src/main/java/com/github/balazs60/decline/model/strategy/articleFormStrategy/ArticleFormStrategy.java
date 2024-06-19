package com.github.balazs60.decline.model.strategy.articleFormStrategy;

import com.github.balazs60.decline.model.articles.Article;

public interface ArticleFormStrategy {
    public String getArticle(String nominativeArticle, Article article);
}
