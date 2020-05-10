package part1.lesson06.task01;

import java.util.TreeSet;

/**
 * Задание 1. Написать программу, читающую текстовый файл.
 * Программа должна составлять отсортированный по алфавиту список слов, найденных в файле и сохранять его в файл-результат.
 * Найденные слова не должны повторяться, регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 */
public class Main {

    public static void main(String[] args) {

        String fileSource = "C:/Users/Admin/Desktop/source.txt";
        String fileDestination = "C:/Users/Admin/Desktop/destination.txt";
        FileReadService service = new FileReadService(fileSource, fileDestination);
        TreeSet<String> words = service.readWords();

        service.writeWords(words);

    }

}
