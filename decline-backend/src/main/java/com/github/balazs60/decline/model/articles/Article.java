package com.github.balazs60.decline.model.articles;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.strategy.articleFormStrategy.ArticleFormStrategy;
import com.github.balazs60.decline.model.strategy.articleFormStrategy.FeminineArticleStrategy;
import com.github.balazs60.decline.model.strategy.articleFormStrategy.MasculineArticleStrategy;
import com.github.balazs60.decline.model.strategy.articleFormStrategy.NeutralArticleStrategy;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
public abstract class Article {

    private Case caseType;
    private String masculine;
    private String feminine;
    private String neutral;
    private String plural;
    @Transient
    private List<ArticleFormStrategy> articleFormStrategies;

    public Article() {
        this.articleFormStrategies = new ArrayList<>();
        this.articleFormStrategies.add(new MasculineArticleStrategy());
        this.articleFormStrategies.add(new FeminineArticleStrategy());
        this.articleFormStrategies.add(new NeutralArticleStrategy());
    }

    public String getCorrectArticleByCaseAndGender(String nominativeArticle, Case caseType, boolean nounIsPlural) {
        String correctArticle = null;
        if (this.caseType == caseType) {
            if (nounIsPlural) {
                return this.plural;
            } else {
                correctArticle = this.getCorrectArticleByGender(nominativeArticle);

            }
        }
        return correctArticle;
    }


    public String getCorrectArticleByGender(String nominativeArticle){
        for(ArticleFormStrategy articleFormStrategy : articleFormStrategies){
            String article = articleFormStrategy.getArticle(nominativeArticle,this);
            if(article != null){
                return article;
            }
        }
        return null;
    }

}
