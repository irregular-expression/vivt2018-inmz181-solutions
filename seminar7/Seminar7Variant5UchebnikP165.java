package ru.irregularexpression.vivt.inmz181.solutions.seminar7;

import java.io.*;
import java.util.TreeMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Seminar7Variant5UchebnikP165 {

    static TreeMap<String, Book> library = new TreeMap<>();
    static String CATALOG_PATH;
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";


    /**
     * Консольное приложение на Java
     *
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {
        System.out.println("Учебник, задания со с.165. Вариант 5.");
        System.out.println("Электронная библиотека.");
        try {
            initializeLibrary();
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        System.out.println("Библиотека открыта.");


        boolean isExit = false;
        while (!isExit) {
            System.out.print("Введите команду (? - справка):");
            isExit = getCommand();
        }

        try {
            saveLibraryChanges();
            System.out.println("Вы покинули библиотеку. Изменения сохранены. Возвращайтесь снова.");
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);

        }
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

    private static void initializeLibrary() throws IOException {
        BufferedReader reader = new BufferedReader(getFile());
        int cursor = 4;
        Book book = new Book();
        while (reader.ready()) {
            String data = reader.readLine();
            cursor = (cursor + 1) % 6;
            switch (cursor) {
                case 5:
                    book.udk = data;
                    break;
                case 0:
                    book.author = data;
                    break;
                case 1:
                    book.title = data;
                    break;
                case 2:
                    book.year = Integer.parseInt(data);
                    break;
                case 3:
                    book.countInLibrary = Integer.parseInt(data);
                    break;
                case 4:
                    if (book.udk != null) library.put(book.udk, book);
                    book = new Book();
            }
        }
        System.out.printf("В каталоге библиотеки на настоящий момент хранится %d наименований книг.\n", library.size());
        reader.close();
    }

    private static void saveLibraryChanges() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(CATALOG_PATH)));
        for (Map.Entry<String, Book> book : library.entrySet()) {
            writer.write(book.getValue().udk + System.getProperty("line.separator"));
            writer.write(book.getValue().author + System.getProperty("line.separator"));
            writer.write(book.getValue().title + System.getProperty("line.separator"));
            writer.write(String.valueOf(book.getValue().year) + System.getProperty("line.separator"));
            writer.write(String.valueOf(book.getValue().countInLibrary) + System.getProperty("line.separator"));
            writer.newLine();
        }
        writer.close();
    }

    private static FileReader getFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Введите полный путь к файлу catalog.txt для хранения каталога библиотеки: ");
            String path = reader.readLine();
            File f = new File(path);
            if (f.exists()) {
                if (f.isDirectory()) {
                    PrintWriter writer = new PrintWriter(CATALOG_PATH, "UTF-8");
                    writer.write("");
                    writer.close();
                    return new FileReader(new File(CATALOG_PATH));
                } else {
                    CATALOG_PATH = path;
                    return new FileReader(f);
                }
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Некорректный адрес файла или директории. Попытайтесь снова.");
            e.printStackTrace();
            return getFile();
        }
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
            return;
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
