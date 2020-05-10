package part1.lesson02.task02;

/**
 * Задание 2. Составить программу, генерирующую N случайных чисел.
 * <p>
 * Для каждого числа k вычислить квадратный корень q.
 * Если квадрат целой части q числа равен k, то вывести это число на экран.
 * Предусмотреть что первоначальные числа могут быть отрицательные, в этом случае генерировать исключение.
 */
public class NumberServiceApplication {

    public static void main(String[] args) {

        NumberService numberService = new NumberService();
        numberService.outputNumbers(10);

    }
}
