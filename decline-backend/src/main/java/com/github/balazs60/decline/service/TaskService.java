package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Adjective;
import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.model.Task;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TaskService {

    private NounService nounService;
    private AdjectiveService adjectiveService;

    private Random random;

    public TaskService(NounService nounService, AdjectiveService adjectiveService) {
        this.nounService = nounService;
        this.adjectiveService = adjectiveService;
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

    public String getTaskInStringFormat() {
        Task task = createTask();
        String article;
        String adjective;
        String noun;
        String isPlural;
        String caseType = task.getCaseType().name();

        System.out.println("article first char " + task.getArticle().charAt(0));

        if (task.getArticle().charAt(0) == 'd') {
            article = "D...";
        } else if (task.getArticle().charAt(0) == 'e'){
            article= "E...";
        } else {
            article="K...";
        }

        adjective = task.getAdjective().getNormalAdjectiveForm() + "...";

        if(task.getCaseType().equals(Case.GENITIVE) && !task.isPlural()){
            noun = task.getNoun().getSingularGen();
        } else if (task.isPlural() && !task.getCaseType().equals(Case.DATIVE)){
            noun = task.getNoun().getPluralNom();
        } else if (task.isPlural() && task.getCaseType().equals(Case.DATIVE)){
            noun = task.getNoun().getPluralDat();
        } else {
            noun = task.getNoun().getSingularNom();
        }

        if(task.isPlural() == true){
            isPlural = "(Plural)";
        } else {
            isPlural = "(Singular)";
        }

        return article + " " + adjective + " " + noun +  "." + " " + isPlural + " " + caseType;
    }
}
