package test;

import java.util.LinkedHashSet;

public class LRU implements CacheReplacementPolicy {
    // Class member
    private LinkedHashSet<String> wordStock = new LinkedHashSet<>();

    public void add(String word) {
        if (wordStock.contains(word)) { // if the word is already in stock
            wordStock.remove(word);
            wordStock.add(word);
        } else { // if the word is not in stock
            wordStock.add(word);
        }
    }

    public String remove() {
        String to_be_deleted = wordStock.iterator().next(); // save the word that we want to remove
        wordStock.remove(wordStock.iterator().next()); // remove the word from the set
        return to_be_deleted;
    }
}