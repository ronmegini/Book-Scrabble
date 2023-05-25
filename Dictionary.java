package test;

import java.io.IOException;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dictionary {
    CacheManager LRU;
    CacheManager LFU;
    String[] fileNames;
    BloomFilter bloomFilter;

    public Dictionary(String... givenFilesNames) {
        fileNames = givenFilesNames;
        LRU = new CacheManager(400, new LRU());
        LFU = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        for (String a : fileNames) {
            loadFile(a);
        }
    }

    public void loadFile(String fileName) {
        try {
            Stream<String> file = Files.lines(Paths.get(fileName));
            file.forEach(line -> {
                // insert each word to bloomFilter
                Stream.of(line.split("\\s+")).forEach(word -> bloomFilter.add(word));
            });
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean query(String word) {
        if (LRU.query(word) == true)
            return true;
        else if (LFU.query(word) == true)
            return false;
        else if (bloomFilter.contains(word)) {
            LRU.add(word);
            return true;
        } else {
            LFU.add(word);
            return false;
        }
    }

    public boolean challenge(String word) {
        if (IOSearcher.search(word, fileNames)) {
            LRU.add(word);
            return true;
        }
        LFU.add(word);
        return false;
    }
}