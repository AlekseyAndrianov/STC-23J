package part1.lesson12.task01;

import java.util.*;

/**
 * Задание 2.
 * <p>
 * Доработать программу так, чтобы ошибка OutOfMemoryError возникала
 * в Metaspace /Permanent Generation
 */
public class Main {

    public static void main(String[] args) {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            int[] arr = new int[10_000_000];
            list.add(arr);
            if ((i) % 10 == 0)
                list.remove(0);
        }
    }
}
