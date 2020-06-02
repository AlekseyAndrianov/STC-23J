package part1.lesson12.task01;

import java.util.*;

/**
 * Задание 1.
 * <p>
 * Необходимо создать программу, которая продемонстрирует утечку памяти в Java.
 * При этом объекты должны не только создаваться,
 * но и периодически частично удаляться, чтобы GC имел возможность очищать часть памяти.
 * Через некоторое время программа должна завершиться с ошибкой OutOfMemoryError
 * c пометкой Java Heap Space.
 */
public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i > 0 && i % 500 == 0) {
                list.remove(0);
                list.add(0, i);
            } else
                list.add(i);
        }
    }
}
