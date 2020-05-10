package part1.lesson03.task01;

import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@ToString
@EqualsAndHashCode
public class MathBox {

    private List<Number> numbers;

    public MathBox(Number[] numbers) {
        Set<Number> numberSet = new HashSet<>(Arrays.asList(numbers));
        if (numbers.length != numberSet.size())
            throw new IllegalArgumentException("Array must have only unique elements!");
        this.numbers = new ArrayList<>(numberSet);
    }

    /**
     * summator, возвращающий сумму всех элементов коллекции
     */
    public Double summator() {
        AtomicReference<Double> n = new AtomicReference<>(0d);
        numbers.stream().forEach(nv -> n.updateAndGet(v -> v + nv.doubleValue()));
        return n.get();
    }

    /**
     * splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода.
     */
    public void splitter(Number divider) {
        int size = numbers.size();
        for (int i = 0; i < size; i++) {
            double divideResult = numbers.get(i).doubleValue() / divider.doubleValue();
            numbers.set(i, divideResult);
        }
    }

    /**
     * Remove existing value
     * @param var
     */
    public void removeIntegerIfExist(Integer var) {
        Iterator<Number> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().doubleValue() == var)
                iterator.remove();
        }
    }
}
