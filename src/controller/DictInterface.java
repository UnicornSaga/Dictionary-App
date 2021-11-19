package controller;

import model.Word;
import javafx.collections.ObservableList;

public interface DictInterface {

    Word searchWord(String other);

    ObservableList<String> recommendWord(String other);

    void insertWord(Word other);

    void deleteWord(String other);

    void updateWord(Word word, Word newWord);

    int getNewId();
}