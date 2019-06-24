package seminar1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Seminar1Variant5 {

    private static BufferedReader reader; //Объект ввода-вывода текста.
    private static double a; //Угол альфа.
    //Константа для сообщения об ошибке ввода-вывода.
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";

    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {

        System.out.println("Семинар 1. Вариант 5.");
        System.out.println("Программа расчёта по двум формулам. \n");

        //Создаём объект для чтения пользовательского ввода из консоли
        reader = new BufferedReader(new InputStreamReader(System.in));
        //Получаем данные от пользователя, обрабатывая ошибку ввода-вывода. Корректность ввода проверяется в методе setUserInput.
        try {
            setUserInput();
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }
        System.out.println();

        double z1 = 1.0 - (1.0 / 4) * Math.pow(Math.sin(2 * a), 2) + Math.cos(2 * a); //формула z1;
        double z2 = Math.pow(Math.cos(a), 2) + Math.pow(Math.cos(a), 4); //формула z2;

        //Результат выводим с точностью до 2 знаков после запятой.
        System.out.println("Результат:");
        System.out.printf("z1 = %.2f \n", z1);
        System.out.printf("z2 = %.2f", z2);

    }

    private static void setUserInput() throws IOException {
        try {
            System.out.print("Введите значение угла альфа: ");
            String s = reader.readLine();
            a = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод! Введёное значение должно быть числом.");
            setUserInput();
        }
    }

}
