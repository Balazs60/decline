package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.TaskDto;
import com.github.balazs60.decline.model.adjective.Adjective;
import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.model.Task;
import com.github.balazs60.decline.model.articles.Article;
import org.springframework.stereotype.Service;
import java.util.Comparator;


import java.util.*;

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
        String noun = task.getCorrectNounForm();
        String caseType = task.getCaseType().name();
        String articleByCaseAndGender = articleService.getCorrectArticleForm(task.getArticle(), Case.valueOf(caseType), task.isPlural());
        boolean hasTaskArticle;
        String adjective;
        String pluralOrSignature;
        TaskDto taskDto = new TaskDto();

        if (articleByCaseAndGender == null) {
            hasTaskArticle = false;
        } else {
            hasTaskArticle = true;
        }
        String endingOfTheInflectedAdjective = adjectiveService.getCorrectAdjectiveEnding(caseType,articleByCaseAndGender, task.getArticle(), hasTaskArticle, task.isPlural());
        String inflectedAdjective = task.getAdjective().getNormalForm() + endingOfTheInflectedAdjective;

        System.out.println("inflected adjective " + inflectedAdjective);

        adjective = task.getAdjective().getNormalForm() + "...";

        if (task.isPlural() == true) {
            pluralOrSignature = "(Plural)";
        } else {
            pluralOrSignature = "(Singular)";
        }

        createQuestion(articleByCaseAndGender, taskDto, task, adjective, noun, pluralOrSignature, caseType);

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

    public List<String> getArticleAnswerOptions(char firstCharOfArticle, boolean isPlural) {
        List<String> articleAnswerOptions = new ArrayList<>();

        if (firstCharOfArticle == 'D') {
            articleAnswerOptions = getPossibleFormsOfDefiniteArticles();
        } else if (firstCharOfArticle == 'E' || firstCharOfArticle == 'K') {
            articleAnswerOptions = getPossibleFormsOfIndefiniteArticles(isPlural);
        }
        return articleAnswerOptions;
    }

    public List<String> getPossibleFormsOfDefiniteArticles() {
        List<String> possibleFormsOfDefiniteArticles = new ArrayList<>();
        for (Article article : articleService.getDefiniteArticles()) {
            if (article.getCaseType().equals(Case.NOMINATIVE)){
                possibleFormsOfDefiniteArticles.add(article.getMasculine());
                possibleFormsOfDefiniteArticles.add(article.getFeminine());
                possibleFormsOfDefiniteArticles.add(article.getNeutral());
            } else {
                possibleFormsOfDefiniteArticles.add(article.getMasculine());
            }
        }
        return possibleFormsOfDefiniteArticles;
    }

    public List<String> getPossibleFormsOfIndefiniteArticles(boolean isPlural) {
        List<String> possibleFormsOfInDefiniteArticles = new ArrayList<>();

        for (Article article : articleService.getInDefiniteArticles()) {
            if (isPlural) {
                boolean alreadyAdded = possibleFormsOfInDefiniteArticles.stream()
                        .anyMatch(option -> option.equals(article.getPlural()));

                if (!alreadyAdded) {
                    possibleFormsOfInDefiniteArticles.add(article.getPlural());
                }
            } else {
                possibleFormsOfInDefiniteArticles.add(article.getMasculine());
                if(article.getCaseType().equals(Case.ACCUSATIVE) || article.getCaseType().equals(Case.GENITIVE)){
                    possibleFormsOfInDefiniteArticles.add(article.getFeminine());
                }
                sortIndefiniteArticles(possibleFormsOfInDefiniteArticles);
            }
        }
        return possibleFormsOfInDefiniteArticles;
    }

    public void sortIndefiniteArticles(List<String> possibleFormsOfInDefiniteArticles){
        Collections.sort(possibleFormsOfInDefiniteArticles, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                List<String> order = List.of("Ein", "Eine", "Einen", "Einem", "Einer", "Eines");
                return Integer.compare(order.indexOf(s1), order.indexOf(s2));
            }
        });
    }

    public void createQuestion(String articleByCaseAndGender,
                               TaskDto taskDto,
                               Task task,
                               String adjective,
                               String noun,
                               String pluralOrSignature,
                               String caseType) {
        if (articleByCaseAndGender != null) {
            char firstLetterOfArticle = articleByCaseAndGender.charAt(0);
            taskDto.setArticleAnswerOptions(getArticleAnswerOptions(firstLetterOfArticle, task.isPlural()));
            System.out.println("article by case and gender " + articleByCaseAndGender);
            taskDto.setQuestion(firstLetterOfArticle + "... " + " " + adjective + " " + noun + "." + " " + pluralOrSignature + " " + caseType);

        } else {
            taskDto.setQuestion(adjective + " " + noun + "." + " " + pluralOrSignature + " " + caseType);

        }
    }
}
