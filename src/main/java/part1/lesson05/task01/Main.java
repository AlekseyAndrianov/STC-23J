package part1.lesson05.task01;

import java.util.List;
import java.util.Set;

/**
 * Разработать программу – картотеку домашних животных.
 * У каждого животного есть уникальный идентификационный номер, кличка, хозяин (объект класс Person с полями – имя, возраст, пол), вес.
 * <p>
 * Реализовать:
 * <p>
 * метод добавления животного в общий список (учесть, что добавление дубликатов должно приводить к исключительной ситуации)
 * поиск животного по его кличке (поиск должен быть эффективным)
 * изменение данных животного по его идентификатору
 * вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
 */
public class Main {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Set<Animal> animals = AnimalCreator.getAnimals(100, 200);
        System.out.println("End after " + (System.currentTimeMillis() - start) + " ms.");

        start = System.currentTimeMillis();
        AnimalDB animalDB = new AnimalDB(animals);
        Animal a = animalDB.getAnimalByName("Jack");
        animalDB.setNewInfoById(a.getId(), "TUUUU", a.getOwner(), 200);
        System.out.println(animalDB.toString());

        System.out.println("End after " + (System.currentTimeMillis() - start) + " ms.");
    }

}
