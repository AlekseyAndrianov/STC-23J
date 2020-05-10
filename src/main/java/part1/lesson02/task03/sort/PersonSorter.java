package part1.lesson02.task03.sort;

import part1.lesson02.task03.Person;

/**
 * Единый интерфейс для классов сортировки. Реализовать два различных метода сортировки этого массива по правилам:
 *
 * первые идут мужчины
 * выше в списке тот, кто более старший
 * имена сортируются по алфавиту
 */
public interface PersonSorter {

    void sort(Person[] people);
}
