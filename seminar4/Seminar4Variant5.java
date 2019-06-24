package seminar4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Seminar4Variant5 {
    private static BufferedReader reader; //Объект ввода-вывода текста.
    private static int n; //размер матрицы.
    private static int low; //минимальное значение элемента матрицы.
    private static int high; //максимальное значение элемента матрицы.
    private static int[][] matrix;
    private final static int GET_MATRIX_SIZE = 0;
    private final static int GET_LOW_EDGE = 1;
    private final static int GET_HIGH_EDGE = 2;
    //Константа для сообщения об ошибке ввода-вывода.
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";

    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {

        System.out.println("Семинар 4. Вариант 5.");
        System.out.println("Программа расчёта по двумерной целочисленной матрице. \n");

        //Создаём объект для чтения пользовательского ввода из консоли
        reader = new BufferedReader(new InputStreamReader(System.in));
        //Получаем данные от пользователя, обрабатывая ошибку ввода-вывода. Корректность ввода проверяется в методе setUserInput.
        try {
            setUserInput(GET_MATRIX_SIZE);
            setUserInput(GET_LOW_EDGE);
            setUserInput(GET_HIGH_EDGE);
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }
        System.out.println();
        System.out.printf("Матрица %dx%d заполняется случайными значениями в диапазоне от %d до %d... \n", n, n, low, high);
        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Random random = new Random();
                matrix[i][j] = low + random.nextInt(high - low + 1);
                System.out.printf("     %d     ", matrix[i][j]);
            }
            System.out.println();
        }
        //№1
        System.out.println("\n Задание №1");
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int tmpSum = 0; //временная переменная для записи суммы элементов в столбце
            for (int j = 0; j < n; j++) {
                if (matrix[j][i] < 0) {
                    tmpSum = 0; //если в столбце найдено отрицательное число, выходим из цикла по столбцу и обнуляем сумму по нему
                    break;
                }
                tmpSum += matrix[j][i];
            }
            sum += tmpSum;
        }
        System.out.printf("Сумма элементов в тех столбцах, которые не содержат отрицательных элементов: %d.\n", sum);
        //№2
        System.out.println("\n Задание №2");
        /*
        Используем Integer вместо int, чтобы избежать отдельного цикла по инициализации начального значения
        переменной с помощью проверки на null.
         */
        Integer diagMinSum = null;

        /*
        Проходим циклом по всем диагоналям матрицы, параллельным побочной,  исключая саму побочную и угловые
        ячейки, которые не образуют искомых диагоналей (считаем, что диагональ матрицы имеет не менее 2 ячеек).
         */
        for (int i = 1; i < (n * 2) - 2; i++) {
            int tmpSum = 0;
            if (i != n - 1) {
                //Определяем стартовые координаты для внутреннего цикла
                int x = (i < n - 1) ? 0 : i % n + 1;
                int y = (i < n - 1) ? i : n - 1;
                //Суммируем модули элементов диагонали
                for (; x < n && y > -1; x++, y--) {
                    tmpSum += Math.abs(matrix[x][y]);
                }
                if (diagMinSum == null) {
                    diagMinSum = tmpSum;
                } else if (tmpSum < diagMinSum) {
                    diagMinSum = tmpSum;
                }
            }
        }
        System.out.printf("Минимум среди сумм модулей элементов в диагоналях матрицы, параллельных побочной диагонали матрицы: %d.\n", diagMinSum);



    }

    private static void setUserInput(int command) throws IOException {
        switch (command) {
            case GET_MATRIX_SIZE:
                try {
                    System.out.print("Введите размерность квадратной матрицы: ");
                    String s = reader.readLine();
                    n = Integer.parseInt(s);
                    if (n < 3) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ввод! В матрице должно существовать больше 2 диагоналей, \n поэтому ведёное значение должно быть целым числом не меньше 3.");
                    setUserInput(GET_MATRIX_SIZE);
                }
                break;
            case GET_LOW_EDGE:
                try {
                    System.out.print("Введите минимальное допустимое число: ");
                    String s = reader.readLine();
                    low = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ввод! Введёное значение должно быть целым числом.");
                    setUserInput(GET_LOW_EDGE);
                }
                break;
            case GET_HIGH_EDGE:
                try {
                    System.out.print("Введите максимальное допустимое число: ");
                    String s = reader.readLine();
                    high = Integer.parseInt(s);
                    if (low > high) {
                        int tmp = low;
                        low = high;
                        high = tmp;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ввод! Введёное значение должно быть целым числом.");
                    setUserInput(GET_HIGH_EDGE);
                }
                break;

        }
    }
}
