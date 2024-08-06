package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.articles.Article;
import com.github.balazs60.decline.model.articles.DefiniteArticle;
import com.github.balazs60.decline.model.articles.IndefiniteArticle;
import com.github.balazs60.decline.repositories.DefiniteArticleRepository;
import com.github.balazs60.decline.repositories.IndefiniteArticleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    @Mock
    private IndefiniteArticleRepository indefiniteArticleRepository;
    @Mock
    private DefiniteArticleRepository definiteArticleRepository;
    @Mock
    private Article mockedArticle;
    private ArticleService articleService;
    AutoCloseable autoCloseable;
    Random random;


    @BeforeEach
    public void setUp() {
        random = mock(Random.class);
        autoCloseable = MockitoAnnotations.openMocks(this);
        articleService = new ArticleService(definiteArticleRepository, indefiniteArticleRepository, random);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getInDefiniteArticles() {
        articleService.getInDefiniteArticles();
        verify(indefiniteArticleRepository).findAll();
    }

    @Test
    void getDefiniteArticles() {
        articleService.getDefiniteArticles();
        verify(definiteArticleRepository).findAll();
    }

    @Test
    void getCorrectArticleForm() {
        Article mockArticle1 = mock(Article.class);
        Article mockArticle2 = mock(Article.class);

        when(mockArticle1.getCorrectArticleByCaseAndGender("Der", Case.NOMINATIVE, false)).thenReturn("Der");
        when(mockArticle2.getCorrectArticleByCaseAndGender("Der", Case.NOMINATIVE, false)).thenReturn(null);
        List<Article> mockArticles = Arrays.asList(mockArticle1, mockArticle2);
        when(articleService.getRandomArticles()).thenReturn(mockArticles);

        String result = articleService.getCorrectArticleForm("Der", Case.NOMINATIVE, false);

        assertEquals("Der", result);
    }

    @Test
    void getCorrectArticleFormWhenNoArticle() {
        List<Article> mockArticles = new ArrayList<>();
        when(articleService.getRandomArticles()).thenReturn(mockArticles);

        String result = articleService.getCorrectArticleForm("Der", Case.NOMINATIVE, false);

        assertEquals(null, result);
    }

    @Test
    void getRandomArticlesWhenRandomNumberIs0() {
        when(random.nextInt(3)).thenReturn(0);
        List<DefiniteArticle> definiteArticles = new ArrayList<>();
        definiteArticles.add(new DefiniteArticle());
        when(definiteArticleRepository.findAll()).thenReturn(definiteArticles);

        List<Article> actualArticles = articleService.getRandomArticles();

        assertEquals(definiteArticles.size(), actualArticles.size());
        assertTrue(actualArticles.containsAll(definiteArticles));
        verify(definiteArticleRepository).findAll();
        verify(indefiniteArticleRepository, never()).findAll();
    }

    @Test
    void getRandomArticlesWhenRandomNumberIs1() {
        when(random.nextInt(3)).thenReturn(1);
        List<IndefiniteArticle> indefiniteArticles = new ArrayList<>();
        indefiniteArticles.add(new IndefiniteArticle());
        when(indefiniteArticleRepository.findAll()).thenReturn(indefiniteArticles);

        List<Article> actualArticles = articleService.getRandomArticles();

        assertEquals(indefiniteArticles.size(), actualArticles.size());
        assertTrue(actualArticles.containsAll(indefiniteArticles));
        verify(indefiniteArticleRepository).findAll();
        verify(definiteArticleRepository, never()).findAll();
    }

    @Test
    void getRandomArticlesWhenRandomNumberIs2() {
        when(random.nextInt(3)).thenReturn(2);

        List<Article> actualArticles = articleService.getRandomArticles();

        assertEquals(0, actualArticles.size());
        verify(indefiniteArticleRepository, never()).findAll();
        verify(definiteArticleRepository, never()).findAll();
    }
}
