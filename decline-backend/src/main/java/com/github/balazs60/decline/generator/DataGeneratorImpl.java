package com.github.balazs60.decline.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balazs60.decline.model.Adjective;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.repositories.AdjectiveRepository;
import com.github.balazs60.decline.repositories.NounRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Component
public class DataGeneratorImpl implements DataGenerator {

    private ObjectMapper mapper;
    private NounRepository nounRepository;
    private AdjectiveRepository adjectiveRepository;

    public DataGeneratorImpl(
            ObjectMapper mapper,
            NounRepository nounRepository,
            AdjectiveRepository adjectiveRepository) {
        this.mapper = mapper;
        this.nounRepository = nounRepository;
        this.adjectiveRepository = adjectiveRepository;
    }

    @Override
    public void seedNouns() {
        List<Noun> nounsList = new ArrayList<>();
        try {
            InputStream inputStream = new ClassPathResource("nouns.json").getInputStream();

            List<List<String>> nouns = mapper.readValue(inputStream, new TypeReference<List<List<String>>>() {
            });

            for (List<String> noun : nouns) {
                Noun newNoun = new Noun();
                newNoun.setArticle(noun.get(0));
                newNoun.setNormalNounForm(noun.get(1));
                newNoun.setNounFormWithEEnd(noun.get(2));
                newNoun.setNounFormWithNEnd(noun.get(3));
                newNoun.setNounFormSEnd(noun.get(4));
                nounsList.add(newNoun);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        nounRepository.saveAll(nounsList);
    }


    @Override
    public void seedAdjectives() {
        List<Adjective> adjectiveList = new ArrayList<>();
        try {
            InputStream inputStream = new ClassPathResource("adjectives.json").getInputStream();

            List<List<String>> adjectives = mapper.readValue(inputStream, new TypeReference<List<List<String>>>() {
            });

            for (List<String> adjective : adjectives) {
                Adjective newAdjective = new Adjective();
                newAdjective.setNormalAdjectiveForm(adjective.get(0));
                newAdjective.setAdjectiveFormWithEEnd(adjective.get(1));
                newAdjective.setAdjectiveFormWithREnd(adjective.get(2));
                newAdjective.setAdjectiveFormWithMEnd(adjective.get(3));
                newAdjective.setAdjectiveFormWithNEnd(adjective.get(4));
                newAdjective.setAdjectiveFormWithSEnd(adjective.get(5));
                adjectiveList.add(newAdjective);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        adjectiveRepository.saveAll(adjectiveList);
    }

    @Override
    public void seedDefiniteArticles() {

    }

    @Override
    public void seedIndefiniteArticles() {

    }
}
