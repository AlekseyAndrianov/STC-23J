package part1.lesson03.task02;

import java.util.ArrayList;
import java.util.List;

/**
 * хранит коллекцию Object.
 */
public class ObjectBox {
    private List<Object> objectCollection;

    public ObjectBox() {
        this.objectCollection = new ArrayList<>();
    }

    /**
     * добавляющий объект в коллекцию.
     * @param object
     */
    public void addObject(Object object){
        objectCollection.add(object);
    }

    /**
     * проверяющий наличие объекта в коллекции и при наличии удаляющий его.
     * @param object
     */
    public void deleteObject(Object object){
        if (objectCollection.contains(object))
            objectCollection.remove(object);
    }

    /**
     * выводящий содержимое коллекции в строку.
     */
    public void dump(){
        for (Object o : objectCollection) {
            System.out.print(o + "; ");
        }
    }
}
