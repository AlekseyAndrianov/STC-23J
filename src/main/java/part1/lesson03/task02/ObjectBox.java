package part1.lesson03.task02;

import lombok.Getter;

import java.util.*;

/**
 * хранит коллекцию Object.
 */
public class ObjectBox {
    protected List<Object> collection;

    public ObjectBox(Object[] collection) {
        this.collection = new ArrayList<>(Arrays.asList(collection));
    }

    public ObjectBox() {
        collection = new ArrayList<>();
    }

    /**
     * добавляющий объект в коллекцию.
     *
     * @param object
     */
    public void addObject(Object object) {
        collection.add(object);
    }

    /**
     * проверяющий наличие объекта в коллекции и при наличии удаляющий его.
     *
     * @param object
     */
    public void deleteObject(Object object) {
        Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o.equals(object)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * выводящий содержимое коллекции в строку.
     */
    public void dump() {
        for (Object o : collection) {
            System.out.print(o + "; ");
        }
    }
}
