package part1.lesson12.task02;

import java.util.ArrayList;
import java.util.List;

/**
 * Задание 2.
 * Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace
 * /Permanent Generation
 */
public class Main {


    public static void main(String[] args) {

        List<Class> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Class clazz = list.getClass();
            if (i > 0 && i % 500 == 0) {
                list.remove(0);
                list.add(0, clazz);
            } else
                list.add(clazz);
        }
    }
}