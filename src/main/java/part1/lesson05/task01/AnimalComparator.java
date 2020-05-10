package part1.lesson05.task01;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal animal1, Animal animal2) {
        int isOwnerEquals = animal1.getOwner().compareTo(animal2.getOwner());
        if (isOwnerEquals == 0) {
            int isNameEquals = animal1.getName().compareTo(animal2.getName());
            if (isNameEquals == 0) {
                return (int) (animal1.getWeight() - animal2.getWeight());
            }
            return isNameEquals;
        }
        return isOwnerEquals;
    }
}
