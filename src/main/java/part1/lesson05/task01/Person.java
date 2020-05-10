package part1.lesson05.task01;

import lombok.Builder;
import lombok.Data;

/**
 * хозяин (объект класс Person с полями – имя, возраст, пол)
 */
@Data
@Builder
public class Person implements Comparable<Person> {
    private String name;
    private int age;
    private boolean isMan;

    @Override
    public int compareTo(Person person) {
        if (person == null)
            return -1;
        return this.name.compareTo(person.getName());
    }
}
