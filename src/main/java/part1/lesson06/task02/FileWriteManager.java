package part1.lesson06.task02;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Задание 2. Создать генератор текстовых файлов, работающий по следующим правилам:
 * <p>
 * Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
 * Слово состоит из 1<=n2<=15 латинских букв
 * Слова разделены одним пробелом
 * Предложение начинается с заглавной буквы
 * Предложение заканчивается (.|!|?)+" "
 * Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
 * Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива в следующее предложение (1/probability).
 * Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability),
 * который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
 */
public class FileWriteManager {

    private static final char[] endLine = new char[]{'.', '|', '!', '|', '?', ' '};

    public void getFiles(String path, int n, int size, String[] words, int probability) {

        List<String> sequences = makeSentences(words, probability);
        sequences.stream().forEach(c -> System.out.println(c));

        List<String> sequencesPool = makeSequencesPool(sequences);
        System.out.println("-----------");
        sequencesPool.stream().forEach(c -> System.out.println(c));
        writeTestToFiles(path, n, size, sequencesPool);
    }


    private void writeTestToFiles(String path, int n, int size, List<String> sequencesPool) {
        Iterator<String> iterator = sequencesPool.iterator();
        String bufferForNextFile = null;
        for (int i = 0; i < n; i++) {
            File file = new File(path + "/" + i + ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                long poolSize = 0;
                while (iterator.hasNext()) {
                    String sequence = bufferForNextFile == null ? iterator.next() : bufferForNextFile;
                    bufferForNextFile = null;
                    long sizeInSequence = sequence.chars().count();
                    if (poolSize + sizeInSequence <= size) {
                        poolSize += sizeInSequence;
                        writer.write(sequence);
                    } else {
                        bufferForNextFile = sequence;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> makeSequencesPool(List<String> sequences) {
        List<String> stringList = new ArrayList<>();
        ListIterator<String> iterator = sequences.listIterator();
        StringBuilder sequencesPool = new StringBuilder();

        int wordCounterInSequence = (int) (Math.random() * 20) + 1;
        while (iterator.hasNext()) {
            if (wordCounterInSequence == 0) {
                wordCounterInSequence = (int) (Math.random() * 20) + 1;
                sequencesPool.append("\n\r");
                stringList.add(sequencesPool.toString());
                sequencesPool = new StringBuilder();
            }
            sequencesPool.append(iterator.next());
            if (iterator.hasNext())
                sequencesPool.append(" ");
            wordCounterInSequence--;
        }
        sequencesPool.append("\n\r");
        stringList.add(sequencesPool.toString());

        return stringList;
    }

    private List<String> makeSentences(String[] words, int probability) {
        List<String> stringList = new ArrayList<>(Arrays.asList(words));
        StringBuilder sequence = new StringBuilder();
        List<String> listSequences = new ArrayList<>();
        ListIterator<String> iterator = stringList.listIterator();

        int wordCounterInSequence = 0;
        boolean firstWord = true;
        while (iterator.hasNext() || iterator.hasPrevious()) {
            if (wordCounterInSequence == 0) {
                wordCounterInSequence = (int) (Math.random() * 15) + 1;
            }
            if (Math.random() <= 1.0 / probability) {
                String word = iterator.hasNext() ? iterator.next() : iterator.previous();
                if (firstWord) {
                    word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                    firstWord = false;
                }
                sequence.append(word);
                iterator.remove();
                wordCounterInSequence--;
                if (wordCounterInSequence == 0) {
                    sequence.append(endLine[(int) (Math.random() * endLine.length)]);
                    listSequences.add(sequence.toString());
                    sequence = new StringBuilder();
                    firstWord = true;

                    continue;
                }
                if (iterator.hasNext() || iterator.hasPrevious()) {
                    if (Math.random() < 0.5)
                        sequence.append(",");
                    sequence.append(" ");
                }
            }
        }
        sequence.append(endLine[(int) (Math.random() * endLine.length)]);
        listSequences.add(sequence.toString());

        return listSequences;

    }

}
