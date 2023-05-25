package test;

import java.util.LinkedHashSet;

public class CacheManager {
    LinkedHashSet<String> set;
    CacheReplacementPolicy crp;
    int cacheSize;
    int current;

    public CacheManager(int size, CacheReplacementPolicy givenPolicy) {
        cacheSize = size;
        current = 0;
        set = new LinkedHashSet<>();
        crp = givenPolicy;
    }

    public boolean query(String word) {
        return set.contains(word);
    }

    public void add(String word) {
        if (current >= cacheSize) {
            String removed = crp.remove();
            crp.add(word);
            set.add(word);
            set.remove(removed);
        } else {
            crp.add(word);
            set.add(word);
            current++;
        }
    }
}