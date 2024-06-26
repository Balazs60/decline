package com.github.balazs60.decline.model.strategy.articleFormStrategy;

import com.github.balazs60.decline.model.articles.Article;

public class NeutralArticleStrategy implements ArticleFormStrategy {
    @Override
    public String getArticle(String nominativeArticle, Article article) {
        if (nominativeArticle.equals("das")) {
            return article.getNeutral();
        }
        return null;
    }
}
