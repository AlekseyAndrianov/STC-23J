package part1.lesson05.task01;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * метод добавления животного в общий список (учесть, что добавление дубликатов должно приводить к исключительной ситуации)
 * поиск животного по его кличке (поиск должен быть эффективным)
 * изменение данных животного по его идентификатору
 * вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
 */
@AllArgsConstructor
public class AnimalDB {

    private List<Animal> animals;

    public void addAnimal(Animal newAnimal) {
        if (animals.contains(newAnimal))
            throw new IllegalArgumentException("Database contains the same Animal");
        animals.add(newAnimal);
    }

    public Animal getAnimalByName(String name) {
        return animals.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
    }

    public void setNewInfoById(UUID id, String name, Person owner, double weight) {
        Animal animal = animals.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
        if (animal == null)
            throw new IllegalStateException("No such Animal in the Database");
        animal.setName(name);
        animal.setOwner(owner);
        animal.setWeight(weight);
    }

    @Override
    public String toString() {
        Collections.sort(animals, new Comparator<Animal>() {
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
        });
        StringBuilder stringBuilder = new StringBuilder();
        animals.stream().forEach(a -> stringBuilder.append(a.toString() + "\n"));
        return stringBuilder.toString();
    }
}
