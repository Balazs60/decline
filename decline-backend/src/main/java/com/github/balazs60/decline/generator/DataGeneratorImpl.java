package com.github.balazs60.decline.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balazs60.decline.model.Adjective;
import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.model.articles.DefiniteArticle;
import com.github.balazs60.decline.model.articles.IndefiniteArticle;
import com.github.balazs60.decline.repositories.AdjectiveRepository;
import com.github.balazs60.decline.repositories.DefiniteArticleRepository;
import com.github.balazs60.decline.repositories.IndefiniteArticleRepository;
import com.github.balazs60.decline.repositories.NounRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
    private DefiniteArticleRepository definiteArticleRepository;
    private IndefiniteArticleRepository indefiniteArticleRepository;

    public DataGeneratorImpl(
            ObjectMapper mapper,
            NounRepository nounRepository,
            AdjectiveRepository adjectiveRepository,
            DefiniteArticleRepository definiteArticleRepository,
            IndefiniteArticleRepository indefiniteArticleRepository) {
        this.mapper = mapper;
        this.nounRepository = nounRepository;
        this.adjectiveRepository = adjectiveRepository;
        this.definiteArticleRepository = definiteArticleRepository;
        this.indefiniteArticleRepository = indefiniteArticleRepository;
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
                newNoun.setSingularNom(noun.get(1));
                newNoun.setPluralNom(noun.get(2));
                newNoun.setPluralDat(noun.get(3));
                newNoun.setSingularGen(noun.get(4));
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
        List<DefiniteArticle> definiteArticleList = new ArrayList<>();

        DefiniteArticle nominativeDefiniteArticles = new DefiniteArticle();
        nominativeDefiniteArticles.setCaseType(Case.NOMINATIVE);
        nominativeDefiniteArticles.setMasculine("Der");
        nominativeDefiniteArticles.setFeminine("Die");
        nominativeDefiniteArticles.setNeutral("Das");
        nominativeDefiniteArticles.setPlural("Die");

        definiteArticleList.add(nominativeDefiniteArticles);

        DefiniteArticle accusativeDefiniteArticles = new DefiniteArticle();
        accusativeDefiniteArticles.setCaseType(Case.ACCUSATIVE);
        accusativeDefiniteArticles.setMasculine("Den");
        accusativeDefiniteArticles.setFeminine("Die");
        accusativeDefiniteArticles.setNeutral("Das");
        accusativeDefiniteArticles.setPlural("Die");

        definiteArticleList.add(accusativeDefiniteArticles);

        DefiniteArticle dativeDefiniteArticles = new DefiniteArticle();
        dativeDefiniteArticles.setCaseType(Case.DATIVE);
        dativeDefiniteArticles.setMasculine("Dem");
        dativeDefiniteArticles.setFeminine("Der");
        dativeDefiniteArticles.setNeutral("Dem");
        dativeDefiniteArticles.setPlural("Den");

        definiteArticleList.add(dativeDefiniteArticles);

        DefiniteArticle genitiveDefiniteArticles = new DefiniteArticle();
        genitiveDefiniteArticles.setCaseType(Case.GENITIVE);
        genitiveDefiniteArticles.setMasculine("Des");
        genitiveDefiniteArticles.setFeminine("Den");
        genitiveDefiniteArticles.setNeutral("Des");
        genitiveDefiniteArticles.setPlural("Der");

        definiteArticleList.add(genitiveDefiniteArticles);
        definiteArticleRepository.saveAll(definiteArticleList);
    }

    @Override
    public void seedIndefiniteArticles() {
        List<IndefiniteArticle> indefiniteArticleList = new ArrayList<>();

        IndefiniteArticle nominativeIndefiniteArticle = new IndefiniteArticle();
        nominativeIndefiniteArticle.setCaseType(Case.NOMINATIVE);
        nominativeIndefiniteArticle.setMasculine("Ein");
        nominativeIndefiniteArticle.setFeminine("Eine");
        nominativeIndefiniteArticle.setNeutral("Ein");
        nominativeIndefiniteArticle.setPlural("Keine");

        indefiniteArticleList.add(nominativeIndefiniteArticle);

        IndefiniteArticle accusativeIndefiniteArticle = new IndefiniteArticle();
        accusativeIndefiniteArticle.setCaseType(Case.ACCUSATIVE);
        accusativeIndefiniteArticle.setMasculine("Einen");
        accusativeIndefiniteArticle.setFeminine("Eine");
        accusativeIndefiniteArticle.setNeutral("Ein");
        accusativeIndefiniteArticle.setPlural("Keine");

        indefiniteArticleList.add(accusativeIndefiniteArticle);

        IndefiniteArticle dativeIndefiniteArticle = new IndefiniteArticle();
        dativeIndefiniteArticle.setCaseType(Case.DATIVE);
        dativeIndefiniteArticle.setMasculine("Einem");
        dativeIndefiniteArticle.setFeminine("Einen");
        dativeIndefiniteArticle.setNeutral("Einem");
        dativeIndefiniteArticle.setPlural("Keinen");

        indefiniteArticleList.add(dativeIndefiniteArticle);

        IndefiniteArticle genitiveIndefiniteArticle = new IndefiniteArticle();
        genitiveIndefiniteArticle.setCaseType(Case.GENITIVE);
        genitiveIndefiniteArticle.setMasculine("Eines");
        genitiveIndefiniteArticle.setFeminine("Einen");
        genitiveIndefiniteArticle.setNeutral("Eines");
        genitiveIndefiniteArticle.setPlural("Keinen");

        indefiniteArticleList.add(genitiveIndefiniteArticle);
        indefiniteArticleRepository.saveAll(indefiniteArticleList);
    }
}
