package seminar9;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Seminar9Variant5 {

    static TreeMap<String, Book> library;
    static String CATALOG_PATH;
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";

    /**
     * Консольное приложение на Java.
     * Решение полностью идентично аналогичному заданию варианта 5 седьмого семинара
     * (со страницы 165 учебника). Только без реализации хранения каталога библиотеки в файле.
     *
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {
        System.out.println("Семинар 9. Вариант 5.");
        System.out.println("Электронная библиотека (без сохранения каталога в файл, решение с сохранением - в семинаре 7).");
        initializeLibrary();
        System.out.println("Библиотека открыта.");

        boolean isExit = false;
        while (!isExit) {
            System.out.print("Введите команду (? - справка):");
            isExit = getCommand();
        }

        saveLibraryChanges();
        System.out.println("Вы покинули библиотеку. Изменения не были сохранены.");
    }

    private static boolean getCommand() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String command = reader.readLine().trim();
            switch (command) {
                case "?":
                    showHelp();
                    break;
                case "0":
                    showAllBooks();
                    break;
                case "1":
                    addNewBookToCatalog();
                    break;
                case "2":
                    checkOut();
                    break;
                case "3":
                    checkIn();
                    break;
                case "4":
                    findBookByUdk();
                    break;
                case "5":
                    findBooksByAuthor();
                    break;
                case "6":
                    findBooksByTitle();
                    break;
                case "#":
                    return true;
                default:
                    System.out.println("Неизвестная команда. Для просмотра списка команд введите ?");
            }
            return false;

        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            return false;
        }
    }

    private static void initializeLibrary() {
        library = new TreeMap<>();
        System.out.println("В каталоге библиотеки на настоящий момент хранится 0 наименований книг.\n");
    }

    private static void saveLibraryChanges() {
         //Не требуется по условию задачи.
    }

    private static void showAllBooks() {
        if (library.size() == 0) {
            System.out.println("В библиотеке пока нет книг!");
        } else {
            for (Map.Entry<String, Book> book : library.entrySet()) {
                printBookData(book.getValue());
            }
        }
    }

    private static void checkOut() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите УДК книги, которую хотите взять: ");
        String udk = reader.readLine();
        if (!library.containsKey(udk)) {
            System.out.println("Ошибка! Такой книги нет в нашей библиотеке!");
            return;
        }
        Book book = library.get(udk);
        if (book.countInLibrary > 0) {
            book.countInLibrary--;
            System.out.println("Книга выдана!");
        } else {
            System.out.println("Извините, в библиотеке сейчас нет экземпляров этой книги!");
        }
    }

    private static void checkIn() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите УДК книги, которую хотите вернуть: ");
        String udk = reader.readLine();
        if (!library.containsKey(udk)) {
            System.out.println("Ошибка! Такой книги нет в нашей библиотеке! Возможно, Вы брали её в другом месте?");
            return;
        }
        library.get(udk).countInLibrary++;
        System.out.println("Спасибо, книга возвращена на полку!");
    }

    private static void findBookByUdk() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите УДК книги: ");
        String udk = reader.readLine();
        if (!library.containsKey(udk)) {
            System.out.println("Извините, такой книги у нас нет.");
        } else {
            System.out.println("Книга есть в каталоге!");
            printBookData(library.get(udk));
        }
    }

    private static void findBooksByAuthor() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите автора книги: ");
        String author = reader.readLine();
        int count = 0;
        for (Map.Entry<String, Book> book : library.entrySet()) {
            if (book.getValue().author.equals(author)) {
                printBookData(book.getValue());
                count++;
            }
        }
        if (count > 0) {
            System.out.printf("По Вашему запросу найдено %d книг автора %s.\n", count, author);
        } else {
            System.out.println("Книги этого автора у нас отсутствуют.");
        }
    }

    private static void findBooksByTitle() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите название или часть названия книги: ");
        String title = reader.readLine();
        int count = 0;
        for (Map.Entry<String, Book> book : library.entrySet()) {
            if (Pattern.matches(".*" + title + ".*", book.getValue().title)) {
                printBookData(book.getValue());
                count++;
            }
        }
        if (count > 0) {
            System.out.printf("По Вашему запросу найдено %d книг.\n", count);
        } else {
            System.out.println("Книги с подобным названием у нас отсутствуют.");
        }
    }

    private static void printBookData(Book book) {
        System.out.printf("%s | %s %s, год издания: %d, количество на полках: %d \n",
                book.udk,
                book.author,
                book.title,
                book.year,
                book.countInLibrary);
    }

    private static void addNewBookToCatalog() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите новый УДК: ");
        String udk = reader.readLine();
        if (library.containsKey(udk)) {
            System.out.println("Ошибка! Книга с таким УДК уже есть в нашей библиотеке:");
            printBookData(library.get(udk));
            return;
        }
        System.out.print("Укажите автора: ");
        String author = reader.readLine();
        System.out.print("Укажите название: ");
        String title = reader.readLine();
        try {
            System.out.print("Укажите год издания: ");
            int year = Integer.parseInt(reader.readLine());
            System.out.print("Укажите количество экземпляров: ");
            int count = Integer.parseInt(reader.readLine());
            Book book = new Book();
            book.udk = udk;
            book.author = author;
            book.title = title;
            book.year = year;
            book.countInLibrary = count;
            library.put(udk, book);
            System.out.println("Книга успешно добавлена!");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Год издания и количество экземпляров - целые числа!");
        }
    }


    private static void showHelp() {
        System.out.println("0 - показать все книги в наличии; \n" +
                "1 - добавить новую книгу в Каталог; \n" +
                "2 - выдать книгу по УДК; \n" +
                "3 - вернуть книгу по УДК; \n" +
                "4 - информация о книге по УДК; \n" +
                "5 - поиск книг по автору; \n" +
                "6 - поиск книг по названию; \n" +
                "? - показать справку. \n" +
                "# - выход из программы. \n");
    }
}
