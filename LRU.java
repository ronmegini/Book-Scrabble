package test;

import java.util.LinkedHashSet;

public class LRU implements CacheReplacementPolicy {

    private LinkedHashSet<String> wordSet = new LinkedHashSet<>();

    public void add(String word) {
        if (wordSet.contains(word)) {
            wordSet.remove(word);
            wordSet.add(word);
        } else {
            wordSet.add(word);
        }
    }

    public String remove() {
        String leastRecentlyUsed = wordSet.iterator().next();
        wordSet.remove(leastRecentlyUsed);
        return leastRecentlyUsed;
    }
}