package part1.lesson07.task01;

import part1.lesson07.task01.service.FactorialService;
import part1.lesson07.task01.service.FourThreadFactorialService;

import java.math.BigInteger;
import java.util.Map;

/**
 * Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива. Использовать пул потоков для решения задачи.
 * <p>
 * Особенности выполнения:
 * <p>
 * Для данного примера использовать рекурсию - не очень хороший вариант, т.к. происходит большое выделение памяти, очень вероятен StackOverFlow.
 * Лучше перемножать числа в простом цикле при этом создавать объект типа BigInteger
 * По сути, есть несколько способа решения задания:
 * <p>
 * 1) распараллеливать вычисление факториала для одного числа
 * 2) распараллеливать вычисления для разных чисел
 * 3) комбинированный
 * При чем вычислив факториал для одного числа, можно запомнить эти данные и использовать их для вычисления другого, что будет гораздо быстрее
 */
public class Main {

    public static void main(String[] args) {
        Integer[] numbers = NumberCreator.createNumbers(100);

        long start = System.currentTimeMillis();
        FactorialService factorialService = new FourThreadFactorialService();
        Map<Integer, BigInteger> factorials = factorialService.findFactorials(numbers);

        for(Map.Entry<Integer, BigInteger> entry : factorials.entrySet()){
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("Time execution = " + (System.currentTimeMillis() - start));
    }

}
