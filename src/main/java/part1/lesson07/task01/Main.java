package part1.lesson07.task01;

import part1.lesson07.task01.service.FourThreadFactorialService;
import part1.lesson07.task01.service.TreeMapFactorialService;
import part1.lesson07.task01.service.HashMapFactorialService;

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

        try {
            String result1 = FactorialServiceExecutor.getAverageExecutionTime(10, TreeMapFactorialService.class);
            String result2 = FactorialServiceExecutor.getAverageExecutionTime(10, HashMapFactorialService.class);
            String result3 = FactorialServiceExecutor.getAverageExecutionTime(10, FourThreadFactorialService.class);

            System.out.println(result1);
            System.out.println(result2);
            System.out.println(result3);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
