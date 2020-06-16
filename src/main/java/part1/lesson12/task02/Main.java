package part1.lesson12.task02;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Задание 2.
 * Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace
 * /Permanent Generation
 */
public class Main {


    public static void main(String[] args) {
        List<Class[]> list = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            Class[] arr = new Class[10_000_000];
            for (int j = 0; j < 10_000_000; j++) {
                ClassLoader cl = new CustomClassLoader();
                arr[j] = Proxy.getProxyClass(cl, Main.class.getInterfaces());
            }
            list.add(arr);
            System.out.println(list.size());
            if ((i) % 100 == 0)
                list.remove(0);
        }
    }
}