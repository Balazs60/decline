package com.github.balazs60.decline.service;

import com.github.balazs60.decline.model.adjective.Adjective;
import com.github.balazs60.decline.model.adjective.AdjectiveDeclensionEndings;
import com.github.balazs60.decline.repositories.AdjectiveRepository;
import com.github.balazs60.decline.repositories.MixedAdjectiveDeclensionEndingsRepository;
import com.github.balazs60.decline.repositories.StrongAdjectiveDeclensionEndingsRepository;
import com.github.balazs60.decline.repositories.WeakAdjectiveDeclensionEndingsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AdjectiveService {

    private AdjectiveRepository adjectiveRepository;
    private StrongAdjectiveDeclensionEndingsRepository strongAdjectiveDeclensionEndingsRepository;
    private WeakAdjectiveDeclensionEndingsRepository weakAdjectiveDeclensionEndingsRepository;
    private MixedAdjectiveDeclensionEndingsRepository mixedAdjectiveDeclensionEndingsRepository;
    private Random random;

    public AdjectiveService(AdjectiveRepository adjectiveRepository,
                            StrongAdjectiveDeclensionEndingsRepository strongAdjectiveDeclensionEndingsRepository,
                            WeakAdjectiveDeclensionEndingsRepository weakAdjectiveDeclensionEndingsRepository,
                            MixedAdjectiveDeclensionEndingsRepository mixedAdjectiveDeclensionEndingsRepository,
                            Random random) {
        this.adjectiveRepository = adjectiveRepository;
        this.strongAdjectiveDeclensionEndingsRepository = strongAdjectiveDeclensionEndingsRepository;
        this.weakAdjectiveDeclensionEndingsRepository = weakAdjectiveDeclensionEndingsRepository;
        this.mixedAdjectiveDeclensionEndingsRepository = mixedAdjectiveDeclensionEndingsRepository;
        this.random = random;
    }

    public List<Adjective> getAllAdjective() {
        return adjectiveRepository.findAll();
    }

    public Adjective getRandomAdjective() {
        List<Adjective> adjectiveList = getAllAdjective();

        int randomIndex = random.nextInt(adjectiveList.size());
        Adjective adjective = adjectiveList.get(randomIndex);
        return adjective;
    }

    public String getCorrectAdjectiveEnding(String caseType,
                                            String inflectedArticle,
                                            String originalArticle,
                                            boolean hasTaskArticle,
                                            boolean nounIsPlural) {

        List<AdjectiveDeclensionEndings> endings = getPossibleAdjectiveEndings(inflectedArticle, hasTaskArticle);
        String correctEnding = null;

        for (AdjectiveDeclensionEndings adjectiveDeclensionEndings : endings) {
            String ending = adjectiveDeclensionEndings.getCorrectEndingOfAdjective(caseType, originalArticle, nounIsPlural);
            if (ending != null) {
                correctEnding = ending;
            }
        }
        return correctEnding;
    }

    public List<AdjectiveDeclensionEndings> getPossibleAdjectiveEndings(String inflectedArticle, boolean hasTaskArticle) {
        List<AdjectiveDeclensionEndings> possibleAdjectiveEndings = new ArrayList<>();

        if (hasTaskArticle == false) {
            possibleAdjectiveEndings.addAll(strongAdjectiveDeclensionEndingsRepository.findAll());

        } else {
            char articleFirstChar = inflectedArticle.charAt(0);
            if (articleFirstChar == 'D') {
                possibleAdjectiveEndings.addAll(weakAdjectiveDeclensionEndingsRepository.findAll());
            } else {
                possibleAdjectiveEndings.addAll(mixedAdjectiveDeclensionEndingsRepository.findAll());
            }
        }
        return possibleAdjectiveEndings;
    }
}
