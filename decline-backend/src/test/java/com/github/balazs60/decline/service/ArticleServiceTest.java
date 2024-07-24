package com.github.balazs60.decline.service;

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
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    @Mock
    private IndefiniteArticleRepository indefiniteArticleRepository;
    @Mock
    private DefiniteArticleRepository definiteArticleRepository;
    private ArticleService articleService;
    AutoCloseable autoCloseable;
    Random random;


    @BeforeEach
    public void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        articleService = new ArticleService(definiteArticleRepository,indefiniteArticleRepository);
        random = mock(Random.class);

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
}
