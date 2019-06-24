package seminar7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Seminar7Variant5Seminar3 {

    private static double[] inputArray;

    //Константа для сообщения об ошибке ввода-вывода.
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";


    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {

        System.out.println("Семинар 7. Вариант 5.");
        System.out.println("Выполнить задания третьего семинара («Одномерные массивы») и четвертого семинара \n («Двумерные массивы»), оформив каждый пункт задания в виде функции. Все необходимые данные для функций \n должны передаваться им в качестве параме-тров. Использование глобальных переменных в функциях \n не допускается. \n");
        try {
            inputArray = initializeArray();
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }

        System.out.println();
        System.out.println("Семинар 3. Задание №1");
        printMaxElement(inputArray);

        System.out.println();
        System.out.println("Семинар 3. Задание №2");
        printSumOfElementsBeforeLastPositive(inputArray);

        System.out.println();
        System.out.println("Семинар 3. Задание №3");
        try {
            printCuttedPartOfArray(inputArray);
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
        }
    }

    public static void printCuttedPartOfArray(double[] inputArray) throws IOException{
        double begin;
        double end;
        double tmp;
        //Получаем входной диапазон

            begin = getDoubleNumber("Введите начало диапазона:");
            end = getDoubleNumber("Введите конец диапазона:");
            //Защита от дурака, если пользователь указал большее число первым.
            if (begin > end) {
                tmp = begin;
                begin = end;
                end = tmp;
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

    public static void printSumOfElementsBeforeLastPositive(double[] inputArray) {
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
    }

    public static void printMaxElement(double[] arr) {
        double max = arr[0];
        for (int i = 1; i < arr.length; i++) if (arr[i] > max) max = arr[i];
        System.out.printf("Максимальный элемент массива - %.2f! \n", max);
    }

    public static double[] initializeArray() throws IOException {
        //Получаем данные от пользователя, обрабатывая ошибку ввода-вывода. Корректность ввода проверяется в методе setUserInput.
        double[] arr = new double[getMaxArraySize()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = getDoubleNumber(String.format("Введите число #%d: ", i));
        }
        return arr;
    }

    private static int getMaxArraySize() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Введите размер массива: ");
            String s = reader.readLine();
            reader.close();
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод! Введёное значение должно быть целым числом.");
            return getMaxArraySize();
        }
    }

    private static double getDoubleNumber(String message) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
