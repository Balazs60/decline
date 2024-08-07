package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.articles.Article;
import com.github.balazs60.decline.model.articles.DefiniteArticle;
import com.github.balazs60.decline.model.articles.IndefiniteArticle;
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
    private Random random;

    public ArticleService(DefiniteArticleRepository definiteArticleRepository, IndefiniteArticleRepository indefiniteArticleRepository,Random random) {
        this.definiteArticleRepository = definiteArticleRepository;
        this.indefiniteArticleRepository = indefiniteArticleRepository;
        this.random = random;
    }

    public List<IndefiniteArticle> getInDefiniteArticles() {return indefiniteArticleRepository.findAll();}
    public List<DefiniteArticle> getDefiniteArticles() {
        return definiteArticleRepository.findAll();
    }

    public String getCorrectArticleForm(String nominativeArticle, Case caseType, boolean nounIsPlural) {
        List<Article> randomArticles = getRandomArticles();

        if(randomArticles.size() > 0) {

            String correctArticle = "";

            for (Article randomArticle : randomArticles) {
                String actualArticle = randomArticle.getCorrectArticleByCaseAndGender(nominativeArticle, caseType, nounIsPlural);

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
                break;
        }

        return randomArticles;
    }
}
