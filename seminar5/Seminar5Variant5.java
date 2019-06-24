package seminar5;

import java.io.*;

public class Seminar5Variant5 {
    private static BufferedReader reader; //Объект ввода-вывода текста.
    private static FileReader fr; //Объект чтения файлов
    //Константа для сообщения об ошибке ввода-вывода.
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";

    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {

        System.out.println("Семинар 5. Вариант 5.");
        System.out.println("Программа чтения текста из файла и изменения в нём порядка слов (каждые 2 слова меняются местами). \n");

        //Создаём объект для чтения пользовательского ввода из консоли
        reader = new BufferedReader(new InputStreamReader(System.in));
        //Получаем данные от пользователя, обрабатывая ошибку ввода-вывода. Корректность ввода проверяется в методе setUserInput.
        try {
            setUserInput();
            reader.close();
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }
        System.out.println();

        reader = new BufferedReader(fr);
        try {
            while (reader.ready()) {
                String s = reader.readLine();
                /*
                В качестве разделителя слов принимаем один из возможных пробельных символов, повторённый
                не менее 1 раза. "Словом" для данного задания считается всё, что не является пробелом
                или переносом строки.
                 */
                String[] tmpArr = s.split("\\s+");
                for (int i = 0; i < tmpArr.length - 1; i+=2) {
                    String tmp = tmpArr[i];
                    tmpArr[i] = tmpArr[i + 1];
                    tmpArr[i + 1] = tmp;
                }
                System.out.println(String.join(" ", tmpArr));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return; //Останавливаем приложение, если получение данных из консоли вернуло ошибку ввода-вывода.
        }


    }

    private static void setUserInput() throws IOException {
        try {
            System.out.print("Введите полный путь к файлу: ");
            String path = reader.readLine();
            File f = new File(path);
            if (!f.exists() || f.isDirectory()) throw new FileNotFoundException();
            fr = new FileReader(f);

        } catch (FileNotFoundException e) {
            System.out.println("Указанный файл не существует! Проверьте правильность указания пути к нему.");
            setUserInput();
        }
    }
}
