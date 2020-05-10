package part1.lesson02.task03.sort;

import part1.lesson02.task03.Person;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class SecondPersonSorter implements PersonSorter {
    @Override
    public void sort(Person[] people) {

        Comparator<Person> comparator = new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                if (person1.getSex() == Person.Sex.MAN && person2.getSex() == Person.Sex.WOMAN)
                    return -1;
                else if (person1.getSex() == Person.Sex.WOMAN && person2.getSex() == Person.Sex.MAN)
                    return 1;
                else if (person1.getAge() > person2.getAge())
                    return -1;
                else if (person1.getAge() < person2.getAge())
                    return 1;
                else return person1.getName().compareTo(person2.getName());

            }
        };

        Collections.sort(Arrays.asList(people), comparator);

    }
}
