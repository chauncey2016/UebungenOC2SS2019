package Optimierung.GeneticAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    public static void write(String path, String[] txt) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for(String line: txt)
                writer.write(line + '\n');
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
