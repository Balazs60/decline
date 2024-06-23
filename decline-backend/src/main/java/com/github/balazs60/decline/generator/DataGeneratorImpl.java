package com.github.balazs60.decline.generator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.balazs60.decline.model.adjective.Adjective;
import com.github.balazs60.decline.model.Case;
import com.github.balazs60.decline.model.Noun;
import com.github.balazs60.decline.model.adjective.MixedAdjectiveDeclensionEndings;
import com.github.balazs60.decline.model.adjective.StrongAdjectiveDeclensionEndings;
import com.github.balazs60.decline.model.adjective.WeakAdjectiveDeclensionEndings;
import com.github.balazs60.decline.model.articles.DefiniteArticle;
import com.github.balazs60.decline.model.articles.IndefiniteArticle;
import com.github.balazs60.decline.repositories.*;
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
    private StrongAdjectiveDeclensionEndingsRepository strongAdjectiveDeclensionEndingsRepository;
    private WeakAdjectiveDeclensionEndingsRepository weakAdjectiveDeclensionEndingsRepository;
    private MixedAdjectiveDeclensionEndingsRepository mixedAdjectiveDeclensionEndingsRepository;

    public DataGeneratorImpl(ObjectMapper mapper,
                             NounRepository nounRepository,
                             AdjectiveRepository adjectiveRepository,
                             DefiniteArticleRepository definiteArticleRepository,
                             IndefiniteArticleRepository indefiniteArticleRepository,
                             StrongAdjectiveDeclensionEndingsRepository strongAdjectiveDeclensionEndingsRepository,
                             WeakAdjectiveDeclensionEndingsRepository weakAdjectiveDeclensionEndingsRepository,
                             MixedAdjectiveDeclensionEndingsRepository mixedAdjectiveDeclensionEndingsRepository) {
        this.mapper = mapper;
        this.nounRepository = nounRepository;
        this.adjectiveRepository = adjectiveRepository;
        this.definiteArticleRepository = definiteArticleRepository;
        this.indefiniteArticleRepository = indefiniteArticleRepository;
        this.strongAdjectiveDeclensionEndingsRepository = strongAdjectiveDeclensionEndingsRepository;
        this.weakAdjectiveDeclensionEndingsRepository = weakAdjectiveDeclensionEndingsRepository;
        this.mixedAdjectiveDeclensionEndingsRepository = mixedAdjectiveDeclensionEndingsRepository;
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
                newAdjective.setNormalForm(adjective.get(0));
                newAdjective.setEForm(adjective.get(1));
                newAdjective.setRForm(adjective.get(2));
                newAdjective.setMForm(adjective.get(3));
                newAdjective.setNForm(adjective.get(4));
                newAdjective.setSForm(adjective.get(5));
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
        genitiveDefiniteArticles.setFeminine("Der");
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
        dativeIndefiniteArticle.setFeminine("Einer");
        dativeIndefiniteArticle.setNeutral("Einem");
        dativeIndefiniteArticle.setPlural("Keinen");

        indefiniteArticleList.add(dativeIndefiniteArticle);

        IndefiniteArticle genitiveIndefiniteArticle = new IndefiniteArticle();
        genitiveIndefiniteArticle.setCaseType(Case.GENITIVE);
        genitiveIndefiniteArticle.setMasculine("Eines");
        genitiveIndefiniteArticle.setFeminine("Einer");
        genitiveIndefiniteArticle.setNeutral("Eines");
        genitiveIndefiniteArticle.setPlural("Keiner");

        indefiniteArticleList.add(genitiveIndefiniteArticle);
        indefiniteArticleRepository.saveAll(indefiniteArticleList);
    }

    public void seedStrongAdjectiveDeclensionEndings() {
        List<StrongAdjectiveDeclensionEndings> strongAdjectiveDeclensionList = new ArrayList<>();

        StrongAdjectiveDeclensionEndings nominativeAdjectiveDeclension = new StrongAdjectiveDeclensionEndings();
        nominativeAdjectiveDeclension.setCaseType(Case.NOMINATIVE);
        nominativeAdjectiveDeclension.setMasculineEnding("er");
        nominativeAdjectiveDeclension.setFeminineEnding("e");
        nominativeAdjectiveDeclension.setNeutralEnding("es");
        nominativeAdjectiveDeclension.setPluralEnding("e");

        strongAdjectiveDeclensionList.add(nominativeAdjectiveDeclension);

        StrongAdjectiveDeclensionEndings accusativeAdjectiveDeclension = new StrongAdjectiveDeclensionEndings();
        accusativeAdjectiveDeclension.setCaseType(Case.ACCUSATIVE);
        accusativeAdjectiveDeclension.setMasculineEnding("en");
        accusativeAdjectiveDeclension.setFeminineEnding("e");
        accusativeAdjectiveDeclension.setNeutralEnding("es");
        accusativeAdjectiveDeclension.setPluralEnding("e");

        strongAdjectiveDeclensionList.add(accusativeAdjectiveDeclension);

        StrongAdjectiveDeclensionEndings dativeAdjectiveDeclension = new StrongAdjectiveDeclensionEndings();
        dativeAdjectiveDeclension.setCaseType(Case.DATIVE);
        dativeAdjectiveDeclension.setMasculineEnding("em");
        dativeAdjectiveDeclension.setFeminineEnding("er");
        dativeAdjectiveDeclension.setNeutralEnding("em");
        dativeAdjectiveDeclension.setPluralEnding("en");

        strongAdjectiveDeclensionList.add(dativeAdjectiveDeclension);

        StrongAdjectiveDeclensionEndings genitiveAdjectiveDeclension = new StrongAdjectiveDeclensionEndings();
        genitiveAdjectiveDeclension.setCaseType(Case.GENITIVE);
        genitiveAdjectiveDeclension.setMasculineEnding("en");
        genitiveAdjectiveDeclension.setFeminineEnding("er");
        genitiveAdjectiveDeclension.setNeutralEnding("en");
        genitiveAdjectiveDeclension.setPluralEnding("er");

        strongAdjectiveDeclensionList.add(genitiveAdjectiveDeclension);

        strongAdjectiveDeclensionEndingsRepository.saveAll(strongAdjectiveDeclensionList);
    }

    public void seedWeakAdjectiveDeclensionEndings() {
        List<WeakAdjectiveDeclensionEndings> weakAdjectiveDeclensionList = new ArrayList<>();

        WeakAdjectiveDeclensionEndings nominativeAdjectiveDeclension = new WeakAdjectiveDeclensionEndings();
        nominativeAdjectiveDeclension.setCaseType(Case.NOMINATIVE);
        nominativeAdjectiveDeclension.setMasculineEnding("e");
        nominativeAdjectiveDeclension.setFeminineEnding("e");
        nominativeAdjectiveDeclension.setNeutralEnding("e");
        nominativeAdjectiveDeclension.setPluralEnding("en");

        weakAdjectiveDeclensionList.add(nominativeAdjectiveDeclension);

        WeakAdjectiveDeclensionEndings accusativeAdjectiveDeclension = new WeakAdjectiveDeclensionEndings();
        accusativeAdjectiveDeclension.setCaseType(Case.ACCUSATIVE);
        accusativeAdjectiveDeclension.setMasculineEnding("en");
        accusativeAdjectiveDeclension.setFeminineEnding("e");
        accusativeAdjectiveDeclension.setNeutralEnding("e");
        accusativeAdjectiveDeclension.setPluralEnding("en");

        weakAdjectiveDeclensionList.add(accusativeAdjectiveDeclension);

        WeakAdjectiveDeclensionEndings dativeAdjectiveDeclension = new WeakAdjectiveDeclensionEndings();
        dativeAdjectiveDeclension.setCaseType(Case.DATIVE);
        dativeAdjectiveDeclension.setMasculineEnding("en");
        dativeAdjectiveDeclension.setFeminineEnding("en");
        dativeAdjectiveDeclension.setNeutralEnding("en");
        dativeAdjectiveDeclension.setPluralEnding("en");

        weakAdjectiveDeclensionList.add(dativeAdjectiveDeclension);

        WeakAdjectiveDeclensionEndings genitiveAdjectiveDeclension = new WeakAdjectiveDeclensionEndings();
        genitiveAdjectiveDeclension.setCaseType(Case.GENITIVE);
        genitiveAdjectiveDeclension.setMasculineEnding("en");
        genitiveAdjectiveDeclension.setFeminineEnding("en");
        genitiveAdjectiveDeclension.setNeutralEnding("en");
        genitiveAdjectiveDeclension.setPluralEnding("en");

        weakAdjectiveDeclensionList.add(genitiveAdjectiveDeclension);

        weakAdjectiveDeclensionEndingsRepository.saveAll(weakAdjectiveDeclensionList);
    }

    public void seedMixedAdjectiveDeclensionEndings() {
        List<MixedAdjectiveDeclensionEndings> mixedAdjectiveDeclensionList = new ArrayList<>();


        MixedAdjectiveDeclensionEndings nominativeAdjectiveDeclension = new MixedAdjectiveDeclensionEndings();
        nominativeAdjectiveDeclension.setCaseType(Case.NOMINATIVE);
        nominativeAdjectiveDeclension.setMasculineEnding("er");
        nominativeAdjectiveDeclension.setFeminineEnding("e");
        nominativeAdjectiveDeclension.setNeutralEnding("es");
        nominativeAdjectiveDeclension.setPluralEnding("en");

        mixedAdjectiveDeclensionList.add(nominativeAdjectiveDeclension);

        MixedAdjectiveDeclensionEndings accusativeAdjectiveDeclension = new MixedAdjectiveDeclensionEndings();
        accusativeAdjectiveDeclension.setCaseType(Case.ACCUSATIVE);
        accusativeAdjectiveDeclension.setMasculineEnding("en");
        accusativeAdjectiveDeclension.setFeminineEnding("e");
        accusativeAdjectiveDeclension.setNeutralEnding("es");
        accusativeAdjectiveDeclension.setPluralEnding("en");

        mixedAdjectiveDeclensionList.add(accusativeAdjectiveDeclension);

        MixedAdjectiveDeclensionEndings dativeAdjectiveDeclension = new MixedAdjectiveDeclensionEndings();
        dativeAdjectiveDeclension.setCaseType(Case.DATIVE);
        dativeAdjectiveDeclension.setMasculineEnding("en");
        dativeAdjectiveDeclension.setFeminineEnding("en");
        dativeAdjectiveDeclension.setNeutralEnding("en");
        dativeAdjectiveDeclension.setPluralEnding("en");

        mixedAdjectiveDeclensionList.add(dativeAdjectiveDeclension);

        MixedAdjectiveDeclensionEndings genitiveAdjectiveDeclension = new MixedAdjectiveDeclensionEndings();
        genitiveAdjectiveDeclension.setCaseType(Case.GENITIVE);
        genitiveAdjectiveDeclension.setMasculineEnding("en");
        genitiveAdjectiveDeclension.setFeminineEnding("en");
        genitiveAdjectiveDeclension.setNeutralEnding("en");
        genitiveAdjectiveDeclension.setPluralEnding("en");

        mixedAdjectiveDeclensionList.add(genitiveAdjectiveDeclension);

        mixedAdjectiveDeclensionEndingsRepository.saveAll(mixedAdjectiveDeclensionList);

    }
}
