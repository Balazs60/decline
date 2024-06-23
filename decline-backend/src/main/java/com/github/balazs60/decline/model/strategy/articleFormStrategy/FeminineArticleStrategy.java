package com.github.balazs60.decline.model.strategy.articleFormStrategy;

import com.github.balazs60.decline.model.articles.Article;

public class FeminineArticleStrategy implements ArticleFormStrategy {
    @Override
    public String getArticle(String nominativeArticle, Article article) {
        if (nominativeArticle.equals("die")) {
            return article.getFeminine();
        }
        return null;
    }
}
