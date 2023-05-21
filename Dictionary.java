package test;

import java.io.IOException;

public class Dictionary {
    // data members
    CacheManager cache1;
    CacheManager cache2;
    BloomFilter bf;
    String[] fileNamesArray;

    public Dictionary(String... fileNames) {
        cache1 = new CacheManager(400, new LRU());
        cache2 = new CacheManager(100, new LFU());
        bf = new BloomFilter(256, "MD5", "SHA1");
        fileNamesArray = fileNames.clone();
        for (String fileName : fileNamesArray) {
            bf.add(fileName);
        }
    }

    public boolean query(String word) {
        if (cache1.query(word))
            return true;
        else if (cache2.query(word))
            return false;
        else if (bf.contains(word))
            cache1.add(word);
        cache2.add(word);
        return true;
    }

    public boolean challenge(String word) {
        try {
            if (IOSearcher.search(word, fileNamesArray)) {
                cache1.add(word);
                return true;
            }
            cache2.add(word);
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}