package test;

import java.io.OutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {

    DictionaryManager dm;
    PrintWriter out;
    Scanner in;

    @Override
    public void handleClient(InputStream fromClient, OutputStream toClient) {
        out = new PrintWriter(toClient);
        in = new Scanner(fromClient);

        boolean wordAlreadyExists = false;
        String[] input = in.next().split(",");

        if (input[0].equals("Q")) {
            wordAlreadyExists = DictionaryManager.get().query(Arrays.copyOfRange(input, 1, input.length));
        } else if (input[0].equals("C")) {
            wordAlreadyExists = DictionaryManager.get().challenge(Arrays.copyOfRange(input, 1, input.length));
        }

        out.println(wordAlreadyExists ? "true" : "false");
        out.flush();
    }

    @Override
    public void close() {
        out.close();
        in.close();
    }
}
