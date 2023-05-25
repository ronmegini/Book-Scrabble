package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class LFU implements CacheReplacementPolicy {
    // Class members:
    HashMap<String, Integer> wordSet = new HashMap<>();

    public String remove() {
        Collection<Integer> values = wordSet.values();
        int min = values.stream().min(Integer::compareTo).get(); // find the min value
        String leastRecentUsed = wordSet.entrySet().stream() // Find the
                .filter(entry -> entry.getValue() == min)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        wordSet.remove(min);
        return leastRecentUsed;
    }

    public void add(String word) {
        if (wordSet.containsKey(word)) {
            int count = wordSet.get(word) + 1;
            wordSet.put(word, count);
        } else {
            wordSet.put(word, 1);
        }
    }
}