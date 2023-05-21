package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOSearcher {

    public static boolean search(String word, String... fileNames) {
        Set<String> set = null;
        Stream<String> s;
        for (String fileName : fileNames) {
            try {
                s = Files.lines(Paths.get(fileName));
                set = s.filter(line -> line.contains(word)).collect(Collectors.toSet());
                s.close(); // close Stream + file
                if (!set.isEmpty()) {
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }
}