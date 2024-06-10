package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.articles.Article;
import com.github.balazs60.decline.model.articles.DefiniteArticle;
import com.github.balazs60.decline.repositories.DefiniteArticleRepository;
import com.github.balazs60.decline.repositories.IndefiniteArticleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<Article> randomArticles = new ArrayList<>();
        randomArticles = getRandomArticles();


        if(randomArticles.size() > 0) {

            String correctArticle = "";

            for (Article randomArticle : randomArticles) {
                String actualArticle = randomArticle.getCorrectArticleByCaseAndGender(article, caseType, nounIsPlural);

                if (actualArticle != null) {
                    correctArticle = actualArticle;
                }
            }
            return correctArticle;
        } else {

            return null;
        }
    }

    public List<Article> getRandomArticles(){
        List<Article> randomArticles = new ArrayList<>();

        Random random = new Random();
        int randomNumber = random.nextInt(3);

        switch (randomNumber) {
            case 0:
                randomArticles.addAll(definiteArticleRepository.findAll());
                break;
            case 1:
                randomArticles.addAll(indefiniteArticleRepository.findAll());
                break;
            case 2:
            default:
                // Do nothing, return an empty list or null
                break;
        }

        return randomArticles;
    }
}
