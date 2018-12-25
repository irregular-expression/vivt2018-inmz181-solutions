package ru.irregularexpression.vivt.inmz181.solutions.seminar3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Seminar3Variant5 {

    private static BufferedReader reader; //Объект ввода-вывода текста.
    private static int INPUT_ARRAY_SIZE; //Количество элементов в массиве.
    private static double[] inputArray;

    //Константа для сообщения об ошибке ввода-вывода.
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";
    private static final int GET_MAX_ARRAY_SIZE = 0;
    private static final int GET_ARRAY = 1;

    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {

        System.out.println("Семинар 3. Вариант 5.");
        System.out.println("Работа с массивами. \n");

        //Создаём объект для чтения пользовательского ввода из консоли
        reader = new BufferedReader(new InputStreamReader(System.in));
        //Получаем данные от пользователя, обрабатывая ошибку ввода-вывода. Корректность ввода проверяется в методе setUserInput.
        try {
            setUserInput(GET_MAX_ARRAY_SIZE);
            inputArray = new double[INPUT_ARRAY_SIZE];
            setUserInput(GET_ARRAY);
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }

        //№1
        System.out.println();
        System.out.println("Задание №1");
        double max = inputArray[0];
        for (int i = 1; i < inputArray.length; i++) if (inputArray[i] > max) max = inputArray[i];
        System.out.printf("Максимальный элемент массива - %.2f! \n", max);

        //№2
        System.out.println();
        System.out.println("Задание №2");
        double sum = 0.0d;
        boolean isLastPositiveFound = false;
        for (int i = inputArray.length - 1; i >= 0; i--) {
            if (!isLastPositiveFound && inputArray[i] > 0) {
                isLastPositiveFound = true; //Находим последний положительный элемент
            } else if (isLastPositiveFound) {
                sum += inputArray[i]; //Суммируем элементы, если последний положительный элемент найден.
            }
        }
        if (isLastPositiveFound) {
            System.out.printf("Сумма элементов массива, расположенных перед последним положительным элементом равна %.2f \n", sum);
        } else {
            System.out.println("Во введённом массиве отсутствуют положительные элементы.");
        }

        //№3
        System.out.println();
        System.out.println("Задание №3");
        double begin;
        double end;
        double tmp;
        //Получаем входной диапазон
        try {
            begin = getDoubleNumber("Введите начало диапазона:");
            end = getDoubleNumber("Введите конец диапазона:");
            //Защита от дурака, если пользователь указал большее число первым.
            if (begin > end) {
                tmp = begin;
                begin = end;
                end = tmp;
            }
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }

        /*
        Несмотря на то, что условие задачи не говорит о сортировке напрямую, фактически, задача сводится именно к
        сортировке массива по бинарному критерию, при которой не соответствующие условию (попадающие в диапазон)
        элементы окажутся в конце массива и будут заполнены нулями.
        */
        boolean isElementsFound = true;
        for (int i = 0; i < inputArray.length; i++) {
            //Если достигнут конец массива, а элементов найдено не было, значит, элемент нужно обнулить
            if (!isElementsFound) {
                inputArray[i] = 0;
                continue; //Проверку и поиск больше запускать не надо, т.к. все элементы были найдены.
            }
            //Если модуль элемента принадлежит диапазону, то ищем следующий непринадлежащий
            if (Math.abs(inputArray[i]) >= begin && Math.abs(inputArray[i]) <= end) {
                isElementsFound = false;
                for (int j = i; j < inputArray.length; j++) {
                    //Если такой элемент найден, меняем их местами и останавливаем поиск
                    if (Math.abs(inputArray[j]) < begin || Math.abs(inputArray[j]) > end) {
                        tmp = inputArray[i];
                        inputArray[i] = inputArray[j];
                        inputArray[j] = tmp;
                        isElementsFound = true;
                        break;
                    }
                }

            }
        }

        System.out.print("Массив после обработки: ");
        for (int i = 0; i < inputArray.length; i++)
            System.out.print(String.format(i < inputArray.length - 1 ? "%.2f, " : "%.2f.", inputArray[i]));

    }

    private static void setUserInput(int command) throws IOException {
        switch (command) {
            case GET_MAX_ARRAY_SIZE:
                try {
                    System.out.print("Введите размер массива: ");
                    String s = reader.readLine();
                    INPUT_ARRAY_SIZE = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ввод! Введёное значение должно быть целым числом.");
                    setUserInput(GET_MAX_ARRAY_SIZE);
                }
                break;
            case GET_ARRAY:
                for (int i = 0; i < inputArray.length; i++) {
                    inputArray[i] = getDoubleNumber(String.format("Введите число #%d: ", i));
                }


        }
    }

    private static double getDoubleNumber(String message) throws IOException {
        try {
            System.out.println(message);
            String s = reader.readLine();
            double result = Double.parseDouble(s);
            return result;
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод! Введёное значение должно быть числом.");
            return getDoubleNumber(message);
        }
    }

}
