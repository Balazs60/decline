package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.repositories.NounRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class NounServiceTest {

    @Mock
    private NounRepository nounRepository;
    private NounService nounService;
    private Noun noun1;
    private Noun noun2;
    AutoCloseable autoCloseable;
    Random random;


    @BeforeEach
    public void SetUp() {
        random = mock(Random.class);
        autoCloseable = MockitoAnnotations.openMocks(this);
        nounService = new NounService(nounRepository, random);
        noun1 = new Noun();
        noun2 = new Noun();
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllNoun() {
        nounService.getAllNoun();
        Mockito.verify(nounRepository).findAll();
    }

    @Test
    void getRandomNoun() {
        List<Noun> nouns = new ArrayList<>();
        nouns.add(noun1);
        nouns.add(noun2);

        when(nounRepository.findAll()).thenReturn(nouns);
        when(random.nextInt(nouns.size())).thenReturn(1);

        Noun randomNoun = nounService.getRandomNoun();
        assertEquals(randomNoun, nouns.get(1));
    }
}
