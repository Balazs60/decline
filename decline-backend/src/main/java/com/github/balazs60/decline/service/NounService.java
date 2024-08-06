package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.repositories.NounRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class NounService {

    private NounRepository nounRepository;
    private Random random;

    public NounService(NounRepository nounRepository,Random random) {
        this.nounRepository = nounRepository;
        this.random = random;
    }

    public List<Noun> getAllNoun(){
        return nounRepository.findAll();
    }

    public Noun getRandomNoun() {
        List<Noun>nouns = getAllNoun();

        int randomIndex = random.nextInt(nouns.size());
        Noun noun = nouns.get(randomIndex);
        return noun;
    }
}
