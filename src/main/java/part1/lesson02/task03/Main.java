package part1.lesson02.task03;

import part1.lesson02.task03.service.PersonService;
import part1.lesson02.task03.sort.FirstPersonSorter;
import part1.lesson02.task03.sort.PersonSorter;
import part1.lesson02.task03.sort.SecondPersonSorter;

/**
 * Задание 3. Дан массив объектов Person.
 * Создать два класса, методы которых будут реализовывать сортировку объектов.

 *
 * Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
 * Предусмотреть генерацию исходного массива (10000 элементов и более).
 * Если имена людей и возраст совпадают, выбрасывать в программе пользовательское исключение.
 */
public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Person[] people = PersonService.createArrayPersons(10000);
        long end = System.currentTimeMillis();
        System.out.println("Time for adding Person = " + (end - start));

        start = end;
        PersonSorter personSorter = new SecondPersonSorter();
//        PersonSorter personSorter = new FirstPersonSorter();
        personSorter.sort(people);
        end = System.currentTimeMillis();
        System.out.println("Time for sorting = " + (end - start));

        PersonService.print(people);

    }
}
