package ru.irregularexpression.vivt.inmz181.solutions.seminar7;

import java.io.*;
import java.util.TreeMap;
import java.util.Map;

public class Seminar7Variant5UchebnikP165 {

    static TreeMap<String, Book> library = new TreeMap<>();
    static String CATALOG_PATH;
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";


    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {
        System.out.println("Учебник, задания со с.165. Вариант 5.");
        System.out.println("Электронная библиотека.");
        try {
            initializeLibrary();
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
        }
        System.out.println("Библиотека открыта. Введите команду (? - справка).");

        boolean isExit = false;
        while (!isExit) {
            isExit = getCommand();
        }

        try {
            saveLibraryChanges();
            System.out.println("Изменения сохранены. Возвращайтесь снова.");
        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
        }
    }

    private static boolean getCommand() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String command = reader.readLine();
            switch (command) {
                case "?":
                    showHelp();
                    break;
                case "0":
                    showAllBooks();
                    break;
                case "1":
                case "2":
                case "3":
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
                case 0:
                    book.udk = data;
                    break;
                case 1:
                    book.author = data;
                    break;
                case 2:
                    book.title = data;
                    break;
                case 3:
                    book.year = Integer.parseInt(data);
                    break;
                case 4:
                    book.countInLibrary = Integer.parseInt(data);
                case 5:
                    library.put(book.udk, book);
                    book = new Book();
            }
        }
        System.out.printf("В каталоге библиотеки на настоящий момент хранится %d наименований книг.", library.size() );
        reader.close();
    }

    private static void saveLibraryChanges() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(CATALOG_PATH)));
        for (Map.Entry<String, Book> book : library.entrySet()) {
            writer.write(book.getValue().udk);
            writer.write(book.getValue().author);
            writer.write(book.getValue().title);
            writer.write(book.getValue().year);
            writer.write(book.getValue().countInLibrary);
            writer.newLine();
        }
        writer.close();
    }

    private static FileReader getFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Введите полный путь .txt файлу для хранения каталога библиотеки, \n если файла нет, укажите директорию, в которой он будет создан: ");
            String path = reader.readLine();
            reader.close();
            File f = new File(path);
            if (f.exists() && f.isDirectory()) {
                CATALOG_PATH = path + "catalog.txt";
                return new FileReader(new File(CATALOG_PATH));
            }
            if (f.exists() && !f.isDirectory()) {
                CATALOG_PATH = path;
                return new FileReader(f);
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Некорректный адрес файла или директории. Попытайтесь снова.");
            return getFile();
        }
    }
    
    private static void showAllBooks() {
        for (Map.Entry<String, Book> book : library.entrySet()) {

        }
    }

    private static void showHelp() {
        System.out.println("0 - показать все книги в наличии; \n" +
                "1 - добавить новую книгу в Каталог; \n" +
                "2 - взять книгу по УДК; \n" +
                "3 - вернуть книгу по УДК; \n" +
                "? - показать справку. \n" +
                "# - выход из программы. \n");
    }
}
