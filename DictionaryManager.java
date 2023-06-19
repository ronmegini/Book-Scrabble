package test;

import java.util.HashMap;

public class DictionaryManager {

    HashMap<String, Dictionary> books = new HashMap<String, Dictionary>();

    // Implement Singlton
    private static DictionaryManager dm = null;

    public static DictionaryManager get() {
        if (dm == null)
            dm = new DictionaryManager();
        return dm;
    }

    public int getSize() {
        int size = books.size();
        return size;
    }

    public boolean query(String... args) {
        boolean found = false;

        for (int i = 0; i < args.length - 1; i++) {
            if (books.containsKey(args[i]) == false)
                books.put(args[i], new Dictionary(args[i]));
        }

        for (int i = 0; i < args.length - 1; i++) {
            if (books.get(args[i]).query(args[args.length - 1]))
                found = true;
        }
        return found;
    }

    public boolean challenge(String... args) {
        boolean found = false;

        for (int i = 0; i < args.length - 1; i++) {
            if (books.containsKey(args[i]) == false)
                books.put(args[i], new Dictionary(args[i]));
        }

        for (int i = 0; i < args.length - 1; i++) {
            if (books.get(args[i]).challenge(args[args.length - 1]))
                found = true;
        }
        return found;
    }

}