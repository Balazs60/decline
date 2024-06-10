package com.github.balazs60.decline.generator;

public interface DataGenerator {
    public void seedNouns();
    public void seedAdjectives();
    public void seedDefiniteArticles();
    public void seedIndefiniteArticles();
    public void seedStrongAdjectiveDeclensionEndings();
    public void seedWeakAdjectiveDeclensionEndings();
    public void seedMixedAdjectiveDeclensionEndings();
}
