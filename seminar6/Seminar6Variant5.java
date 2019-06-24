package seminar6;

import java.io.*;

public class Seminar6Variant5 {
    private static BufferedReader reader; //Объект ввода-вывода текста.
    private static Aeroflot[] flightArray = new Aeroflot[7]; //Массив из 7 объектов класса Aeroflot
    //Константа для сообщения об ошибке ввода-вывода.
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";

    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {

        System.out.println("Семинар 6. Вариант 5.");
        System.out.println("Задание №1");
        System.out.println("Ввод  с  клавиатуры  данных  в  массив,  состоящий  из  семи  элементов  типа AEROFLOT; \n записи должны быть размещены в алфавитном порядке по названиям пунктов назначения.");
        //Создаём объект для чтения пользовательского ввода из консоли
        reader = new BufferedReader(new InputStreamReader(System.in));
        //Получаем данные от пользователя, обрабатывая ошибку ввода-вывода. Корректность ввода проверяется в методе setUserInput.
        try {
            for (int i = 0; i < flightArray.length; i++) {
                flightArray[i] = new Aeroflot();
                System.out.print("Введите пункт назначения: ");
                flightArray[i].airport = reader.readLine();
                System.out.print("Введите номер рейса: ");
                flightArray[i].flight = reader.readLine();
                System.out.print("Введите самолёт: ");
                flightArray[i].planetype = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }
        System.out.println();

        /*
        Сортируем аэрофлоты в массиве в алфавитном порядке. На самом деле, в Java обычно используются встроенные
        инструменты сортировки, но в учебных целях мы пишем алгоритм сортировки.
        */

        Aeroflot tmp;
        for (int i = 1; i < flightArray.length;) {
            /*
            Стандартная реализация метода String.compareTo() основана на алфавитном порядке и возвращает
            результат сравнения (-1 - меньше, 0 - равно, 1 - больше) в виде int.
            */
            if (i == 0 || flightArray[i - 1].airport.compareTo(flightArray[i].airport) < 0)
                i++;
            else {
                tmp = flightArray[i];
                flightArray[i] = flightArray[i - 1];
                flightArray[i - 1] = tmp;
                i--;
            }
        }

        System.out.println("Расписание вылетов: ");
        for (int i = 0; i < flightArray.length; i++) System.out.printf(" %s | %s | %s \n",
                flightArray[i].airport,
                flightArray[i].flight,
                flightArray[i].planetype);

        System.out.println("Задания №2, №3");
        System.out.println("Вывод на экран пунктов назначения и номеров рейсов, обслуживаемых самолетом, тип которого \n введен с клавиатуры; \n если таких рейсов нет, выдать на дисплей соответствующее сообщение.");

        try {
            String plane = reader.readLine();
            boolean hasFlights = false;
            System.out.println("-----------------------------------");
            for (Aeroflot aeroflot : flightArray) {
                if (aeroflot.planetype.equals(plane)) {
                    hasFlights = true;
                    System.out.printf("Пункт назначения: %s  | № рейса:  %s  \n",
                            aeroflot.airport,
                            aeroflot.flight);
                }
            }
            if (!hasFlights) System.out.println("Рейсы не найдены!");
            System.out.println("-----------------------------------");
            reader.close();
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }
    }

}
