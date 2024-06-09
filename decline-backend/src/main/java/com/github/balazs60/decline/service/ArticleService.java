package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.articles.DefiniteArticle;
import com.github.balazs60.decline.repositories.DefiniteArticleRepository;
import com.github.balazs60.decline.repositories.IndefiniteArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private DefiniteArticleRepository definiteArticleRepository;
    private IndefiniteArticleRepository indefiniteArticleRepository;

    public ArticleService(DefiniteArticleRepository definiteArticleRepository, IndefiniteArticleRepository indefiniteArticleRepository) {
        this.definiteArticleRepository = definiteArticleRepository;
        this.indefiniteArticleRepository = indefiniteArticleRepository;
    }

    public List<DefiniteArticle> getDefiniteArticles() {
        return definiteArticleRepository.findAll();
    }

    public String getCorrectDefiniteArticle(String article, Case caseType, boolean nounIsPlural) {
        List<DefiniteArticle> definiteArticleList = new ArrayList<>();
        definiteArticleList = getDefiniteArticles();

        String correctArticle = "";

        for (DefiniteArticle definiteArticle : definiteArticleList) {
            String actualArticle = definiteArticle.getCorrectArticleByCaseAndGender(article, caseType, nounIsPlural);

            if (actualArticle != null) {
                correctArticle = actualArticle;
            }
        }
        return correctArticle;
    }
}
