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
        String article = task.getFirstCharOfTheArticle();
        String adjective;
        String noun = task.getCorrectNounForm();
        String isPlural;
        String caseType = task.getCaseType().name();

        adjective = task.getAdjective().getNormalAdjectiveForm() + "...";

        if(task.isPlural() == true){
            isPlural = "(Plural)";
        } else {
            isPlural = "(Singular)";
        }

        return article + " " + adjective + " " + noun +  "." + " " + isPlural + " " + caseType;
    }
}
