package src;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileProcessor {
    String filename;
    public FileProcessor(String input) {
        filename = input;
    }

    public void writeToFile(HashMap<String, ArrayList<String>> occupied) {
        BufferedWriter wr;
        try {
            wr = new BufferedWriter(new FileWriter("output.txt"));
            for (Map.Entry<String, ArrayList<String>> pairs : occupied.entrySet()) {
                String str = pairs.getKey() + " " + pairs.getValue();
                //System.out.print(str + "\n");
                wr.write(str + "\n");
            }
            wr.close();
        } catch (IOException e1) {
            //  Auto-generated catch block
            e1.printStackTrace();
        }
        //System.out.println(occupied);
        Path path = Paths.get("output.txt");
        System.out.println(path.toAbsolutePath() );
    }
}
