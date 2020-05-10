package part1.lesson06.task01;

import lombok.AllArgsConstructor;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * Class for read and write files
 */
@AllArgsConstructor
public class FileReadService {

    private String fileSource;
    private String fileDestination;

    public TreeSet<String> readWords() {
        TreeSet<String> wordsSet = new TreeSet<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileSource))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> words = Arrays.asList(line.split("\\W"));
                words.stream().forEach(w -> w.trim());
                wordsSet.addAll(words);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsSet;
    }

    public void writeWords(TreeSet<String> words) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileDestination))) {
            words.stream().forEach(w -> {
                try {
                    if (w.matches("\\w+")) {
                        writer.write(w);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
