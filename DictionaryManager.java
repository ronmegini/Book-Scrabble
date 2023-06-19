package test;

import java.util.HashMap;

public class DictionaryManager {

    // <book's name , book>
    HashMap<String, Dictionary> books = new HashMap<String, Dictionary>();

    // Singlton - DictionaryManager.
    private static DictionaryManager myDictionaryManager = null;

    public static DictionaryManager get() {
        if (myDictionaryManager == null)
            myDictionaryManager = new DictionaryManager();
        return myDictionaryManager;
    }

    // Add new books to the map.
    private void addBooks(String... args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (!books.containsKey(args[i]))
                books.put(args[i], new Dictionary(args[i]));
        }
    }

    // Use the Dictionary class Query method.
    public boolean query(String... args) {
        boolean flag = false;
        addBooks(args);
        for (int i = 0; i < args.length - 1; i++) {
            if (books.get(args[i]).query(args[args.length - 1]))
                flag = true;
        }
        return flag;
    }

    // Use the Dictionary class Challenge method
    public boolean challenge(String... args) {
        boolean flag = false;
        addBooks(args);
        for (int i = 0; i < args.length - 1; i++) {
            if (books.get(args[i]).challenge(args[args.length - 1]))
                flag = true;
        }
        return flag;
    }

    // Returns the number of books in the map.
    public int getSize() {
        return books.size();
    }
}