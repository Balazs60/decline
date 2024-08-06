package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.adjective.*;
import com.github.balazs60.decline.repositories.AdjectiveRepository;
import com.github.balazs60.decline.repositories.MixedAdjectiveDeclensionEndingsRepository;
import com.github.balazs60.decline.repositories.StrongAdjectiveDeclensionEndingsRepository;
import com.github.balazs60.decline.repositories.WeakAdjectiveDeclensionEndingsRepository;
import org.aspectj.lang.annotation.After;
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

class AdjectiveServiceTest {
    @Mock
    private AdjectiveRepository adjectiveRepository;
    @Mock
    private StrongAdjectiveDeclensionEndingsRepository strongAdjectiveDeclensionEndingsRepository;
    @Mock
    private WeakAdjectiveDeclensionEndingsRepository weakAdjectiveDeclensionEndingsRepository;
    @Mock
    private MixedAdjectiveDeclensionEndingsRepository mixedAdjectiveDeclensionEndingsRepository;

    private AdjectiveService adjectiveService;
    private Adjective adjective;
    private Adjective adjective2;
    Random random;
    AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        random = mock(Random.class);
        autoCloseable = MockitoAnnotations.openMocks(this);
        adjectiveService = new AdjectiveService(adjectiveRepository, strongAdjectiveDeclensionEndingsRepository, weakAdjectiveDeclensionEndingsRepository, mixedAdjectiveDeclensionEndingsRepository,random);
        adjective = new Adjective();
        adjective2 = new Adjective();
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllAdjective() {
        adjectiveService.getAllAdjective();
        verify(adjectiveRepository).findAll();
    }

    @Test
    void getRandomAdjective() {
        List<Adjective> adjectiveList = new ArrayList<>();
        adjectiveList.add(adjective);
        adjectiveList.add(adjective2);

        when(adjectiveRepository.findAll()).thenReturn(adjectiveList);

        when(random.nextInt(adjectiveList.size())).thenReturn(1);

        Adjective randomAdjective = adjectiveService.getRandomAdjective();
        assertEquals(adjectiveList.get(1), randomAdjective);
    }

    @Test
    void getCorrectAdjectiveEnding() {
        List<AdjectiveDeclensionEndings> adjectiveDeclensionEndings = new ArrayList<>();
        WeakAdjectiveDeclensionEndings mockedEnding = mock(WeakAdjectiveDeclensionEndings.class);
        adjectiveDeclensionEndings.add(mockedEnding);
        String originalArticle = "Der";
        String inflectedArticle = "Der";
        String caseType = "Nominative";
        boolean hasTaskArticle = true;
        boolean nounIsPlural = false;

        when(adjectiveService.getPossibleAdjectiveEndings(inflectedArticle,hasTaskArticle)).thenReturn(adjectiveDeclensionEndings);
        when(mockedEnding.getCorrectEndingOfAdjective(caseType,originalArticle,nounIsPlural)).thenReturn("e");

       String correctArticle = adjectiveService.getCorrectAdjectiveEnding(caseType,inflectedArticle,originalArticle,hasTaskArticle,nounIsPlural);
       assertEquals("e",correctArticle);
    }

    @Test
    void getPossibleAdjectiveEndingsWhenTaskHasNotArticle() {
        List<StrongAdjectiveDeclensionEndings> strongAdjectiveDeclensionEndingsList = new ArrayList<>();
        StrongAdjectiveDeclensionEndings strongAdjectiveDeclensionEndings = new StrongAdjectiveDeclensionEndings();
        strongAdjectiveDeclensionEndingsList.add(strongAdjectiveDeclensionEndings);
        when(strongAdjectiveDeclensionEndingsRepository.findAll()).thenReturn(strongAdjectiveDeclensionEndingsList);
        List<AdjectiveDeclensionEndings> adjectiveDeclensionEndings = adjectiveService.getPossibleAdjectiveEndings(null, false);

        assertEquals(adjectiveDeclensionEndings, strongAdjectiveDeclensionEndingsList);
        verify(weakAdjectiveDeclensionEndingsRepository,never()).findAll();
        verify(mixedAdjectiveDeclensionEndingsRepository,never()).findAll();
    }

    @Test
    void getPossibleAdjectiveEndingsWithDefiniteArticle() {
        List<WeakAdjectiveDeclensionEndings> weakAdjectiveDeclenionEndingsList = new ArrayList<>();
        WeakAdjectiveDeclensionEndings weakAdjectiveDeclensionEndings = new WeakAdjectiveDeclensionEndings();
        weakAdjectiveDeclenionEndingsList.add(weakAdjectiveDeclensionEndings);
        when(weakAdjectiveDeclensionEndingsRepository.findAll()).thenReturn(weakAdjectiveDeclenionEndingsList);
        List<AdjectiveDeclensionEndings> adjectiveDeclensionEndings = adjectiveService.getPossibleAdjectiveEndings("Der", true);

        assertEquals(adjectiveDeclensionEndings, weakAdjectiveDeclenionEndingsList);
        verify(strongAdjectiveDeclensionEndingsRepository,never()).findAll();
        verify(mixedAdjectiveDeclensionEndingsRepository,never()).findAll();
    }
    @Test
    void getPossibleAdjectiveEndingsWithInDefiniteArticle() {
        List<MixedAdjectiveDeclensionEndings> mixedAdjectiveDeclensionEndingsList = new ArrayList<>();
        MixedAdjectiveDeclensionEndings mixedAdjectiveDeclensionEndings = new MixedAdjectiveDeclensionEndings();
        mixedAdjectiveDeclensionEndingsList.add(mixedAdjectiveDeclensionEndings);
        when(mixedAdjectiveDeclensionEndingsRepository.findAll()).thenReturn(mixedAdjectiveDeclensionEndingsList);
        List<AdjectiveDeclensionEndings> adjectiveDeclensionEndings = adjectiveService.getPossibleAdjectiveEndings("Ein", true);

        assertEquals(adjectiveDeclensionEndings, mixedAdjectiveDeclensionEndingsList);
        verify(strongAdjectiveDeclensionEndingsRepository,never()).findAll();
        verify(weakAdjectiveDeclensionEndingsRepository,never()).findAll();
    }
}
