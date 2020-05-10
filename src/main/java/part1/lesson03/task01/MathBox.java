package part1.lesson03.task01;

import lombok.EqualsAndHashCode;
import part1.lesson03.task02.ObjectBox;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Конструктор на вход получает массив Number. Элементы не могут повторяться.
 * Элементы массива внутри объекта раскладываются в подходящую коллекцию (выбрать самостоятельно).
 * Существует метод summator, возвращающий сумму всех элементов коллекции.
 * Существует метод splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода.
 * Хранящиеся в объекте данные полностью заменяются результатами деления.
 * Необходимо правильно переопределить методы toString, hashCode, equals, чтобы можно было использовать MathBox для вывода данных на экран
 * и хранение объектов этого класса в коллекциях (например, hashMap). Выполнение контракта обязательно!
 * Создать метод, который получает на вход Integer и если такое значение есть в коллекции, удаляет его.
 */
@EqualsAndHashCode
public class MathBox extends ObjectBox {

    public MathBox(Number[] numbers) {
        super(numbers);
        Set<Number> numberSet = new HashSet<>(Arrays.asList(numbers));
        if (numbers.length != numberSet.size())
            throw new IllegalArgumentException("Array must have only unique elements!");
    }

    /**
     * summator, возвращающий сумму всех элементов коллекции
     */
    public Double summator() {
        AtomicReference<Double> n = new AtomicReference<>(0d);
        collection.stream().forEach(nv -> n.updateAndGet(v -> v + ((Number) nv).doubleValue()));
        return n.get();
    }

    /**
     * splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода.
     */
    public void splitter(Number divider) {
        int size = collection.size();
        for (int i = 0; i < size; i++) {
            double divideResult = ((Number) collection.get(i)).doubleValue() / divider.doubleValue();
            if ((int) divideResult == divideResult &&
                    ((Number) collection.get(i)).doubleValue() == (int) (((Number) collection.get(i)).doubleValue()) &&
                    divider.doubleValue() == (int) divider.doubleValue())
                collection.set(i, (int) divideResult);
            collection.set(i, divideResult);
        }
    }

    /**
     * Remove existing value
     *
     * @param var
     */
    public void removeIntegerIfExist(Integer var) {
        deleteObject((double) var);
    }

    @Override
    public void addObject(Object object) {
        if (!(object instanceof Number))
            throw new IllegalArgumentException();
        boolean isContain = collection.stream().anyMatch(o -> ((Number) o).doubleValue() == ((Number) object).doubleValue());
        if (!isContain)
            collection.add(object);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MathBox : ");
        for (Object o : collection) {
            stringBuilder.append(o + "; ");
        }
        return stringBuilder.toString();
    }
}
