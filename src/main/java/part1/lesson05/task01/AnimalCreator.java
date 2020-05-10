package part1.lesson05.task01;

import java.util.*;

public class AnimalCreator {
    private final static String[] personNames = new String[]{"Jack", "Geck", "Julia", "Afrodita", "Elizaveta", "Nick"};
    private final static String[] animalNames = new String[]{"Jack", "Geck", "Julia", "Afrodita", "Elizaveta", "Nick"};

    public static Set<Animal> getAnimals(int personCount, int animalCount) {
        List<Person> personList = new ArrayList<>();
        Set<Animal> animalList = new TreeSet<>(new AnimalComparator());

        for (int i = 0; i < personCount; i++) {
            Person person = Person.builder()
                    .age((int) (Math.random() * 100))
                    .isMan(Math.random() > 0.5 ? true : false)
                    .name(personNames[(int) (Math.random() * 5)]).build();
            personList.add(person);
        }

        for (int i = 0; i < animalCount; i++) {
            Animal animal = Animal.builder()
                    .id(UUID.randomUUID())
                    .name(animalNames[(int) (Math.random() * 5)])
                    .weight(Math.random() * 50)
                    .owner(personList.get((int) (Math.random() * personCount)))
                    .build();
            animalList.add(animal);
        }
        return animalList;
    }
}
