package test;

import java.util.LinkedHashSet;

public class CacheManager {
    LinkedHashSet<String> set;
    CacheReplacementPolicy policy;
    int cacheSize;
    int current;

    public CacheManager(int size, CacheReplacementPolicy p) {
        cacheSize = size;
        current = 0;
        set = new LinkedHashSet<>();
        policy = p;
    }

    public boolean query(String word) {
        return set.contains(word);
    }

    public void add(String word) {
        if (current == cacheSize) {
            String removed = policy.remove();
            policy.add(word);
            set.add(word);
            set.remove(removed);
        } else {
            policy.add(word);
            set.add(word);
            current++;
        }
    }
}