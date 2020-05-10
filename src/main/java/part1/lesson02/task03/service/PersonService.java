package part1.lesson02.task03.service;

import part1.lesson02.task03.Person;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PersonService {

    static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public static Person[] createArrayPersons(int length) {
        Integer lettersLength = letters.length();
        Person[] people = new Person[length];

        ThreadCreator thread1 = new ThreadCreator(0, length / 4, lettersLength, people);
        ThreadCreator thread2 = new ThreadCreator(length / 4, length / 2, lettersLength, people);
        ThreadCreator thread3 = new ThreadCreator(length / 2, length / 4 * 3, lettersLength, people);
        ThreadCreator thread4 = new ThreadCreator(length / 4 * 3, length, lettersLength, people);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<Person> set = new HashSet<Person>(Arrays.asList(people));
        if (set.size() < people.length)
            throw new IllegalStateException("Array contains several equals Person");
        return people;
    }


    public static void print(Person[] people) {
        for (Person person : people) {
            System.out.println(person);
        }
    }
}
