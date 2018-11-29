package ru.irregularexpression.vivt.inmz181.solutions.seminar2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Seminar2Variant5 {
    private static BufferedReader reader; //Объект ввода-вывода текста.
    private static double x1; //Xнач.
    private static double xn; //Xкон.
    private static double e; //ε.
    private static int dx; //dx.

    //Константа для сообщения об ошибке ввода-вывода.
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";
    //Флаги команд ввода
    private static final int BEGIN_VALUE = 0;
    private static final int END_VALUE = 1;
    private static final int STEP_VALUE = 2;
    private static final int ACCURACY_VALUE = 3;

    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {

        System.out.println("Семинар 2. Вариант 5.");
        System.out.println("Вычислить и вывести на экран в виде таблицы значения функции, заданной с помощью \n" +
                        "ряда Тейлора, на интервале от xнач до xкон с шагом dx с точностью ε. Таблицу снабдить  \n" +
                        "заголовком  и  шапкой.  Каждая  строка  должна  содержать  значение  аргумента, значение \n" +
                        "функции и количество просуммированных членов ряда");
        System.out.println();
        //Создаём объект для чтения пользовательского ввода из консоли
        reader = new BufferedReader(new InputStreamReader(System.in));
        //Получаем входные данные от пользователя, обрабатывая ошибку ввода-вывода. Корректность ввода проверяется в методе setUserInput.
        try {
            setUserInput(BEGIN_VALUE);
            setUserInput(END_VALUE);
            setUserInput(STEP_VALUE);
            setUserInput(ACCURACY_VALUE);
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }
        System.out.println();

        //Результат выводим в таблицу, форматируя числа до 2 знаков после запятой.
        System.out.println("-----------------------------------");
        System.out.println("|        X      |         Y       |");
        System.out.println("-----------------------------------");

        final int MAX_ITERATIONS = 500;
        boolean isDone = true;
        double x = x1;
        double y = x;
        for (int i = 0; Math.abs(x) < xn; i += dx) {
            System.out.printf("|     %.4f    |      %.4f     |\n", x, y);
            double tmp = x * (Math.pow(x, 2) / ((2 * i + 2) * (2 * i + 3)));
            if (Math.abs(tmp) < e) break;
            x = tmp;
            y += (i / dx % 2 == 0) ? -1.0 * x : x;
            if (i > MAX_ITERATIONS) {
                isDone = false;
                break;
            }
        }

        if (isDone) {
            System.out.println("-----------------------------------");
            System.out.printf("\n \n Ближайшее значение функции с заданной точностью равно %.4f \n", y);
        } else {
            System.out.println("|      Ряд расходится!             |");
            System.out.println("-----------------------------------");
            System.out.printf("\n \n Функции не удалось достигнуть значения %.4f за %d итераций", xn, MAX_ITERATIONS);

        }



    }

    private static void setUserInput(int value) throws IOException {
        try {
            switch (value) {
                case BEGIN_VALUE:
                    System.out.print("Введите значение Xнач: ");
                    break;
                case END_VALUE:
                    System.out.print("Введите значение Xкон: ");
                    break;
                case ACCURACY_VALUE:
                    System.out.print("Введите значение точности ε: ");
                    break;
                case STEP_VALUE:
                    System.out.print("Введите значение шага dx: ");
                    break;
            }
            String s = reader.readLine();

            switch (value) {
                case BEGIN_VALUE:
                    x1 = Double.parseDouble(s);
                    break;
                case END_VALUE:
                    xn = Double.parseDouble(s);
                    break;
                case ACCURACY_VALUE:
                    e = Double.parseDouble(s);
                    break;
                case STEP_VALUE:
                    dx = Integer.parseInt(s);
                    break;
            }

        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод! Введёное значение должно быть числом.");
            setUserInput(value);
        }
    }
}
