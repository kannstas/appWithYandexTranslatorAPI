package nastya.ru.util;

import nastya.ru.util.Translation;

import java.util.ArrayList;
import java.util.List;

public class YandexTranslatorResponse {
    private List<Translation> translations = new ArrayList<>();

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}