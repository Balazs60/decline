package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.Adjective;
import com.github.balazs60.decline.repositories.AdjectiveRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AdjectiveService {

    private AdjectiveRepository adjectiveRepository;
    private Random random;

    public AdjectiveService(AdjectiveRepository adjectiveRepository) {
        this.adjectiveRepository = adjectiveRepository;
        this.random = new Random();
    }

    public List<Adjective> getAllAdjective(){
        return adjectiveRepository.findAll();
    }

    public Adjective getRandomAdjective(){
        List<Adjective> adjectiveList = getAllAdjective();

        int randomIndex = random.nextInt(adjectiveList.size());
        Adjective adjective = adjectiveList.get(randomIndex);
        return adjective;
    }
}
