package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.TaskDto;
import com.github.balazs60.decline.model.adjective.Adjective;
import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.model.Task;
import com.github.balazs60.decline.model.articles.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TaskService {

    private NounService nounService;
    private AdjectiveService adjectiveService;
    private ArticleService articleService;

    private Random random;

    public TaskService(NounService nounService,
                       AdjectiveService adjectiveService,
                       ArticleService articleService) {
        this.nounService = nounService;
        this.adjectiveService = adjectiveService;
        this.articleService = articleService;
        this.random = new Random();
    }

    public Task createTask() {
        Case[] caseTypes = Case.values();
        int randomIndex = random.nextInt(caseTypes.length);

        Noun noun = nounService.getRandomNoun();
        Adjective adjective = adjectiveService.getRandomAdjective();
        Case caseType = caseTypes[randomIndex];
        Boolean isPlural = random.nextBoolean();

        Task task = new Task();
        task.setAdjective(adjective);
        task.setNoun(noun);
        task.setArticle(noun.getArticle());
        task.setCaseType(caseType);
        task.setPlural(isPlural);

        return task;
    }

    public TaskDto getTask() {
        Task task = createTask();
        String adjective;
        String noun = task.getCorrectNounForm();
        String isPlural;
        String caseType = task.getCaseType().name();
        String articleByCaseAndGender = articleService.getCorrectArticleForm(task.getArticle(), Case.valueOf(caseType), task.isPlural());
        boolean hasTaskArticle;
        TaskDto taskDto = new TaskDto();

        if (articleByCaseAndGender == null) {
            hasTaskArticle = false;
        } else {
            hasTaskArticle = true;
        }
        String endingOfTheInflectedAdjective = adjectiveService.getCorrectAdjectiveEnding(caseType, task.getArticle(), hasTaskArticle, task.isPlural());
        String inflectedAdjective = task.getAdjective().getNormalForm() + endingOfTheInflectedAdjective;

        System.out.println("inflected adjective " + inflectedAdjective);

        adjective = task.getAdjective().getNormalForm() + "...";

        if (task.isPlural() == true) {
            isPlural = "(Plural)";
        } else {
            isPlural = "(Singular)";
        }

        if (articleByCaseAndGender != null) {
            char firstLetterOfArticle = articleByCaseAndGender.charAt(0);
            taskDto.setArticleAnswerOptions(getArticleAllForm(firstLetterOfArticle, task.isPlural()));
            System.out.println("article by case and gender " + articleByCaseAndGender);
            taskDto.setQuestion(firstLetterOfArticle + "... " + " " + adjective + " " + noun + "." + " " + isPlural + " " + caseType);

        } else {
            taskDto.setQuestion(adjective + " " + noun + "." + " " + isPlural + " " + caseType);

        }
        taskDto.setInflectedArticle(articleByCaseAndGender);
        taskDto.setInflectedAdjective(inflectedAdjective);
        taskDto.setAdjectiveAnswerOptions(getAdjectiveAllForm(task.getAdjective()));
        System.out.println("articles answer options " + taskDto.getArticleAnswerOptions());
        return taskDto;
    }

    public List<String> getAdjectiveAllForm(Adjective adjective) {
        List<String> adjectiveAllForm = new ArrayList<>();

        adjectiveAllForm.add(adjective.getNormalForm());
        adjectiveAllForm.add(adjective.getEForm());
        adjectiveAllForm.add(adjective.getMForm());
        adjectiveAllForm.add(adjective.getNForm());
        adjectiveAllForm.add(adjective.getRForm());
        adjectiveAllForm.add(adjective.getSForm());

        return adjectiveAllForm;
    }

    public List<String> getArticleAllForm(char firstCharOfArticle, boolean isPlural) {
        List<String> articleAnswerOptions = new ArrayList<>();

        if (firstCharOfArticle == 'D') {
            for (Article article : articleService.getDefiniteArticles()) {
                if (!article.getCaseType().equals(Case.NOMINATIVE)) {
                    articleAnswerOptions.add(article.getFeminine());
                    articleAnswerOptions.add(article.getNeutral());
                }

            }
        } else if (firstCharOfArticle == 'E' || firstCharOfArticle == 'K') {
            for (Article article : articleService.getInDefiniteArticles()) {
                if (isPlural) {
                    boolean alreadyAdded = articleAnswerOptions.stream()
                            .anyMatch(option -> option.equals(article.getPlural()));

                    if (!alreadyAdded) {
                        articleAnswerOptions.add(article.getPlural());
                    }
                } else {
                    if (!article.getCaseType().equals(Case.NOMINATIVE)) {
                        articleAnswerOptions.add(article.getFeminine());
                        articleAnswerOptions.add(article.getNeutral());
                    }
                }
            }
        }
        return articleAnswerOptions;
    }
}
