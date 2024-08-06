package com.github.balazs60.decline.service;

import com.github.balazs60.decline.dto.TaskDto;
import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.model.Task;
import com.github.balazs60.decline.model.adjective.Adjective;
import com.github.balazs60.decline.model.articles.Article;
import com.github.balazs60.decline.model.articles.DefiniteArticle;
import com.github.balazs60.decline.model.articles.IndefiniteArticle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private NounService nounService;
    @Mock
    private AdjectiveService adjectiveService;
    @Mock
    private ArticleService articleService;
    @Mock
    private TaskService mockedTaskservice;
    private TaskService taskService;
    private Case[] caseTypes;
    private Adjective adjective;
    private Noun noun;
    AutoCloseable autoCloseable;
    Random random;

    @BeforeEach
    public void setUp() {
        random = mock(Random.class);
        autoCloseable = MockitoAnnotations.openMocks(this);
        taskService = new TaskService(nounService, adjectiveService, articleService, random);
        noun = new Noun();
        noun.setArticle("Der");
        adjective = new Adjective();
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createTask() {
        noun.setArticle("Der");
        caseTypes = Case.values();
        when(random.nextInt(caseTypes.length)).thenReturn(1);
        when(adjectiveService.getRandomAdjective()).thenReturn(adjective);
        when(nounService.getRandomNoun()).thenReturn(noun);
        when(random.nextBoolean()).thenReturn(false);

        Task task = taskService.createTask();
        assertEquals(task.getAdjective(), adjective);
        assertEquals(task.getNoun(), noun);
        assertEquals(task.getArticle(), noun.getArticle());
        assertEquals(task.getCaseType(), caseTypes[1]);
        assertEquals(task.isPlural(), false);
    }

    @Test
    void getTask() {
        Task mockedTask = mock(Task.class);
        String mockedNoun = "Mann";
        String mockedCaseType = "NOMINATIVE";
        String mockedArticleByCaseAndGender = "Der";
        String mockedAdjective = "schön";
        Case mockedCase = Case.NOMINATIVE; // or any other Case value you want to test
        boolean isPlural = false;
        String correctAdjectiveEnding = "e";
        List<String> adjectiveAnswerOptions = new ArrayList<>();
        adjectiveAnswerOptions.add("schön");
        Adjective mockedAdjective2 = mock(Adjective.class);


        when(mockedTaskservice.createTask()).thenReturn(mockedTask);
        when(mockedTask.getCorrectNounForm()).thenReturn(mockedNoun);
        when(mockedTask.getCaseType()).thenReturn(mockedCase);
        when(mockedTask.getArticle()).thenReturn("Der");
        when(articleService.getCorrectArticleForm(mockedTask.getArticle(), Case.valueOf(mockedCaseType), mockedTask.isPlural())).thenReturn(mockedArticleByCaseAndGender);
        when(adjectiveService.getRandomAdjective()).thenReturn(mockedAdjective2);
        when(adjectiveService.getCorrectAdjectiveEnding(mockedCaseType, mockedArticleByCaseAndGender, mockedTask.getArticle(), true, false)).thenReturn(correctAdjectiveEnding);
        when(mockedTask.getAdjective()).thenReturn(mockedAdjective2);
        when(mockedAdjective2.getNormalForm()).thenReturn(mockedAdjective);
        when(mockedTask.isPlural()).thenReturn(isPlural);
        when(nounService.getRandomNoun()).thenReturn(noun);

        System.out.println("mokedarticle " + mockedTask.getArticle());
        TaskDto taskDto = taskService.getTask();

        assertEquals(mockedArticleByCaseAndGender,taskDto.getInflectedArticle());
        assertEquals(mockedAdjective + correctAdjectiveEnding,taskDto.getInflectedAdjective());
    }

    @Test
    void getAdjectiveAllForm() {
        adjective.setEForm("kleine");
        adjective.setMForm("kleinem");
        adjective.setNForm("kleinen");
        adjective.setNormalForm("klein");
        adjective.setRForm("kleiner");
        adjective.setSForm("kleines");

        List<String> adjectiveAllForm = taskService.getAdjectiveAllForm(adjective);

        assertTrue(adjectiveAllForm.contains(adjective.getNormalForm()));
        assertTrue(adjectiveAllForm.contains(adjective.getEForm()));
        assertTrue(adjectiveAllForm.contains(adjective.getMForm()));
        assertTrue(adjectiveAllForm.contains(adjective.getNForm()));
        assertTrue(adjectiveAllForm.contains(adjective.getRForm()));
        assertTrue(adjectiveAllForm.contains(adjective.getSForm()));
    }

    @Test
    void getArticleAnswerOptionsWhenArticleFirstCharIsD() {
        DefiniteArticle article = new DefiniteArticle();
        article.setCaseType(Case.NOMINATIVE);
        article.setFeminine("Die");
        article.setMasculine("Der");
        article.setNeutral("Das");
        article.setPlural("Die");
        DefiniteArticle article2 = new DefiniteArticle();
        article2.setCaseType(Case.ACCUSATIVE);
        article2.setFeminine("Die");
        article2.setMasculine("Den");
        article2.setNeutral("Das");
        article2.setPlural("Die");
        List<DefiniteArticle> definiteArticles = new ArrayList<>();
        definiteArticles.add(article);
        definiteArticles.add(article2);

        when(articleService.getDefiniteArticles()).thenReturn(definiteArticles);
        List<String> articles = taskService.getArticleAnswerOptions('D', false);

        assertEquals(articles, List.of("Der", "Die", "Das", "Den"));
    }

    @Test
    void getPossibleFormsOfDefiniteArticlesIfCaseTypeNominative() {
        List<DefiniteArticle> definiteArticles = new ArrayList<>();
        DefiniteArticle nominativeDefiniteArticle = new DefiniteArticle();
        nominativeDefiniteArticle.setCaseType(Case.NOMINATIVE);
        nominativeDefiniteArticle.setFeminine("Die");
        nominativeDefiniteArticle.setMasculine("Der");
        nominativeDefiniteArticle.setNeutral("Das");
        nominativeDefiniteArticle.setNeutral("Die");
        DefiniteArticle accusativeDefiniteArticle = new DefiniteArticle();
        accusativeDefiniteArticle.setCaseType(Case.ACCUSATIVE);
        accusativeDefiniteArticle.setFeminine("Die");
        accusativeDefiniteArticle.setMasculine("Den");
        accusativeDefiniteArticle.setNeutral("Das");
        accusativeDefiniteArticle.setPlural("Die");
        definiteArticles.add(nominativeDefiniteArticle);
        definiteArticles.add(accusativeDefiniteArticle);

        when(articleService.getDefiniteArticles()).thenReturn(definiteArticles);
        List<String> possibleArticles = taskService.getPossibleFormsOfDefiniteArticles();

        assertTrue(possibleArticles.contains(nominativeDefiniteArticle.getMasculine()));
        assertTrue(possibleArticles.contains(nominativeDefiniteArticle.getFeminine()));
        assertTrue(possibleArticles.contains(nominativeDefiniteArticle.getNeutral()));
        assertTrue(possibleArticles.contains(accusativeDefiniteArticle.getMasculine()));
    }

    @Test
    void getPossibleFormsOfIndefiniteArticlesIfItsNotPlural() {
        List<IndefiniteArticle> indefiniteArticleList = new ArrayList<>();
        IndefiniteArticle nominativeIndefiniteArticle = new IndefiniteArticle();
        nominativeIndefiniteArticle.setCaseType(Case.NOMINATIVE);
        nominativeIndefiniteArticle.setMasculine("Ein");
        nominativeIndefiniteArticle.setFeminine("Eine");
        nominativeIndefiniteArticle.setNeutral("Ein");
        nominativeIndefiniteArticle.setPlural("Keine");
        IndefiniteArticle accusativeIndefiniteArticle = new IndefiniteArticle();
        accusativeIndefiniteArticle.setCaseType(Case.ACCUSATIVE);
        accusativeIndefiniteArticle.setFeminine("Eine");
        accusativeIndefiniteArticle.setMasculine("Einen");
        accusativeIndefiniteArticle.setNeutral("Ein");
        accusativeIndefiniteArticle.setPlural("Keine");
        IndefiniteArticle genitiveIndefiniteArticle = new IndefiniteArticle();
        genitiveIndefiniteArticle.setCaseType(Case.GENITIVE);
        genitiveIndefiniteArticle.setFeminine("Einer");
        genitiveIndefiniteArticle.setMasculine("Eines");
        genitiveIndefiniteArticle.setNeutral("Eines");
        genitiveIndefiniteArticle.setPlural("Keine");
        indefiniteArticleList.add(nominativeIndefiniteArticle);
        indefiniteArticleList.add(accusativeIndefiniteArticle);
        indefiniteArticleList.add(genitiveIndefiniteArticle);

        when(articleService.getInDefiniteArticles()).thenReturn(indefiniteArticleList);
        List<String> fakePossibleIndefiniteArticleList = new ArrayList<>();
        doNothing().when(mockedTaskservice).sortIndefiniteArticles(fakePossibleIndefiniteArticleList);
        List<String> possibleFormsOfArticles = taskService.getPossibleFormsOfIndefiniteArticles(false);

        assertEquals(possibleFormsOfArticles.size(), 5);
        assertTrue(possibleFormsOfArticles.contains("Ein"));
        assertTrue(possibleFormsOfArticles.contains("Eine"));
        assertTrue(possibleFormsOfArticles.contains("Einen"));
        assertTrue(possibleFormsOfArticles.contains("Einer"));
        assertTrue(possibleFormsOfArticles.contains("Eines"));
    }

    @Test
    void getPossibleFormsOfIndefiniteArticlesIfItsPlural() {
        List<IndefiniteArticle> indefiniteArticleList = new ArrayList<>();
        IndefiniteArticle nominativeIndefiniteArticle = new IndefiniteArticle();
        nominativeIndefiniteArticle.setCaseType(Case.NOMINATIVE);
        nominativeIndefiniteArticle.setMasculine("Ein");
        nominativeIndefiniteArticle.setFeminine("Eine");
        nominativeIndefiniteArticle.setNeutral("Ein");
        nominativeIndefiniteArticle.setPlural("Keine");
        IndefiniteArticle accusativeIndefiniteArticle = new IndefiniteArticle();
        accusativeIndefiniteArticle.setCaseType(Case.ACCUSATIVE);
        accusativeIndefiniteArticle.setFeminine("Eine");
        accusativeIndefiniteArticle.setMasculine("Einen");
        accusativeIndefiniteArticle.setNeutral("Ein");
        accusativeIndefiniteArticle.setPlural("Keine");
        IndefiniteArticle genitiveIndefiniteArticle = new IndefiniteArticle();
        genitiveIndefiniteArticle.setCaseType(Case.GENITIVE);
        genitiveIndefiniteArticle.setFeminine("Einer");
        genitiveIndefiniteArticle.setMasculine("Eines");
        genitiveIndefiniteArticle.setNeutral("Eines");
        genitiveIndefiniteArticle.setPlural("Keiner");
        indefiniteArticleList.add(nominativeIndefiniteArticle);
        indefiniteArticleList.add(accusativeIndefiniteArticle);
        indefiniteArticleList.add(genitiveIndefiniteArticle);

        when(articleService.getInDefiniteArticles()).thenReturn(indefiniteArticleList);
        List<String> fakePossibleIndefiniteArticleList = new ArrayList<>();
        doNothing().when(mockedTaskservice).sortIndefiniteArticles(fakePossibleIndefiniteArticleList);
        List<String> possibleFormsOfArticles = taskService.getPossibleFormsOfIndefiniteArticles(true);

        assertEquals(possibleFormsOfArticles.size(), 2);
        assertTrue(possibleFormsOfArticles.contains("Keine"));
        assertTrue(possibleFormsOfArticles.contains("Keiner"));
    }

    @Test
    void sortIndefiniteArticles() {
        List<String> possibleFormsOfInDefiniteArticles = new ArrayList<>();
        possibleFormsOfInDefiniteArticles.add("Einen");
        possibleFormsOfInDefiniteArticles.add("Ein");
        possibleFormsOfInDefiniteArticles.add("Eines");
        possibleFormsOfInDefiniteArticles.add("Eine");
        possibleFormsOfInDefiniteArticles.add("Einem");
        possibleFormsOfInDefiniteArticles.add("Einer");

        taskService.sortIndefiniteArticles(possibleFormsOfInDefiniteArticles);

        assertEquals(possibleFormsOfInDefiniteArticles.get(0), "Ein");
        assertEquals(possibleFormsOfInDefiniteArticles.get(1), "Eine");
        assertEquals(possibleFormsOfInDefiniteArticles.get(2), "Einen");
        assertEquals(possibleFormsOfInDefiniteArticles.get(3), "Einem");
        assertEquals(possibleFormsOfInDefiniteArticles.get(4), "Einer");
        assertEquals(possibleFormsOfInDefiniteArticles.get(5), "Eines");
    }

    @Test
    void createQuestionIfArticleIsNull() {
        String articleByCaseAndGender = null;
        TaskDto taskDto = new TaskDto();
        Task task = new Task();
        String stringAdjective = "groß";
        String stringNoun = "Mann";
        String pluralOrSignature = "plural";
        String caseType = "nominative";

        taskService.createQuestion(articleByCaseAndGender, taskDto, task, stringAdjective, stringNoun, pluralOrSignature, caseType);

        assertEquals(taskDto.getQuestion(), stringAdjective + " " + stringNoun + "." + " " + pluralOrSignature + " " + caseType);
    }

    @Test
    void createQuestionIfArticleIsNotNull() {
        String articleByCaseAndGender = "Der";
        TaskDto taskDto = new TaskDto();
        Task task = new Task();
        String stringAdjective = "groß";
        String stringNoun = "Mann";
        String pluralOrSignature = "singular";
        String caseType = "nominative";

        taskService.createQuestion(articleByCaseAndGender, taskDto, task, stringAdjective, stringNoun, pluralOrSignature, caseType);

        assertEquals(taskDto.getQuestion(), "D" + "... " + " " + stringAdjective + " " + stringNoun + "." + " " + pluralOrSignature + " " + caseType);
    }
}
