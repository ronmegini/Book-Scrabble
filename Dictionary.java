package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Dictionary {
    CacheManager LRU;
    CacheManager LFU;
    String[] fileNames;
    BloomFilter bf;

    public Dictionary(String... fNames) {
        fileNames = fNames;
        LRU = new CacheManager(400, new LRU());
        LFU = new CacheManager(100, new LFU());
        bf = new BloomFilter(256, "MD5", "SHA1");
        for (String a : fNames) {
            loadFile(a);
        }
    }

    public void loadFile(String fileName) {
        try {
            Stream<String> s = Files.lines(Paths.get(fileName));
            // every line into out stream add(smart) into our dataStruck and count the size.
            s.forEach(line -> {
                // over every word and put that word into our Milon, ledaleg al kol whiteSpace
                Stream.of(line.split("\\s+")).forEach(word -> bf.add(word));
            });
            s.close();
        } catch (IOException a) {
            throw new RuntimeException(a);
        }
    }

    public boolean query(String word) {
        if (LRU.query(word))
            return true;
        if (LFU.query(word))
            return false;
        boolean test = bf.contains(word);
        if (test)
            LRU.add(word);
        else
            LFU.add(word);

        return test;
    }

    public boolean challenge(String word) {
        boolean test;
        try {
            test = IOSearcher.search(word, fileNames);
        } catch (RuntimeException e) {
            return false;
        }

        if (test)
            LRU.add(word);
        else
            LFU.add(word);

        return test;
    }
}