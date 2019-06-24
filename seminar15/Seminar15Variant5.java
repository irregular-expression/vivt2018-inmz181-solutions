package seminar15;

import seminar7.Book;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Seminar15Variant5 {
    static List<Query> list = new ArrayList<>();
    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";

    /**
     * Консольное приложение на Java
     *
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {
        System.out.println("Семинар 15. Вариант 5.");
        System.out.println("Программа учета заявок на авиабилеты. Каждая заявка содержит: пункт назначения, номер рейса, \n" +
                "фамилию и инициалы пассажира, желаемую дату вылета. Следует обеспечить выбор с помощью меню и выполнение функций.");

        System.out.println("======================.");
        System.out.println("Обработка заявок на билеты открыта.");
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Введите команду (? - справка):");
            isExit = getCommand();
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
                case "ADD":
                    addNewQuery();
                    break;
                case "DEL":
                    deleteQueryByIndex();
                    break;
                case "SBFLIGHT":
                    findQueriesByFlight();
                    break;
                case "SBDATE":
                    findQueriesByDate();
                    break;
                case "SHOW":
                    showAllQueries();
                    break;
                case "E":
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

    private static void showAllQueries() {
        if (list.size() == 0) {
            System.out.println("Нет заявок!");
        } else {
            for (int i = 0; i < list.size(); i++) {
                printQueryData(i, list.get(i));
            }
        }
    }


    private static void deleteQueryByIndex() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Укажите номер заявки в списке, которую следует удалить: ");
            int index = Integer.parseInt(reader.readLine());
            if (index >= 0 && index < list.size()) {
                list.remove(index);
                System.out.println("Заявка удалена!");
            } else {
                System.out.println("Ошибка! Нет такой заявки! Воспользуйтесь командой SHOW, чтобы увидеть список заявок.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Укажите целое число!");
        }
    }

    private static void findQueriesByFlight() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите номер рейса: ");
        String flight = reader.readLine();
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).flight.equals(flight)) {
                printQueryData(i, list.get(i));
                count++;
            }
        }
        if (count > 0) {
            System.out.printf("По Вашему запросу найдено %d заявок на рейс %s.\n", count, flight);
        } else {
            System.out.printf("Заявки на рейс %s отсутствуют.\n", flight);
        }
    }

    private static void findQueriesByDate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Укажите день отправления: ");
            int day = Integer.parseInt(reader.readLine());
            System.out.print("Укажите месяц отправления (от 1 до 12): ");
            int month = Integer.parseInt(reader.readLine());
            System.out.print("Укажите год отправления: ");
            int year = Integer.parseInt(reader.readLine());
            if (DateValidation.isValidDate(day, month, year)) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, day);
                int count = 0;
                for (int i = 0; i < list.size(); i++) {
                    Query query = list.get(i);
                    if (day == query.date.getDay() + 1 && month == query.date.getMonth() + 1 && year == query.date.getYear() + 1900) {
                        printQueryData(i, query);
                        count++;
                    }
                }
                if (count > 0) {
                    System.out.printf("По Вашему запросу найдено %d заявок на дату %d/%d/%d.\n", count, day, month, year);
                } else {
                    System.out.printf("Заявки на дату %d/%d/%d отсутствуют.\n", day, month, year);
                }
            } else {
                System.out.println("На указанную дату невозможно оставить заявку. Вероятно, её не существует или рейсы на неё уже состоялись.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Год, месяц и день - целые числа!");
        }
    }

    private static void printQueryData(int id, Query query) {
        System.out.printf("%d) %s, %s, %s, желаемая дата вылета: %d/%d/%d \n",
                id,
                query.location,
                query.fio,
                query.flight,
                query.date.getDay() + 1,
                query.date.getMonth() + 1,
                query.date.getYear() + 1900);
    }

    private static void addNewQuery() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Укажите пункт назначения: ");
        String location = reader.readLine();
        System.out.print("Укажите номер рейса: ");
        String flight = reader.readLine();
        System.out.print("Укажите фамилию и инициалы пассажира: ");
        String fio = reader.readLine();
        try {
            System.out.print("Укажите желаемый день отправления: ");
            int day = Integer.parseInt(reader.readLine());
            System.out.print("Укажите месяц отправления (от 1 до 12): ");
            int month = Integer.parseInt(reader.readLine());
            System.out.print("Укажите год отправления: ");
            int year = Integer.parseInt(reader.readLine());
            if (DateValidation.isValidDate(day, month, year)) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, day);
                Query query = new Query().setLocation(location)
                        .setFio(fio)
                        .setFlight(flight)
                        .setDate(calendar.getTime());
                list.add(query);
                System.out.println("Заявка добавлена!");
            } else {
                System.out.println("На указанную дату невозможно оставить заявку. Вероятно, её не существует или рейсы на неё уже состоялись.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! Год, месяц и день - целые числа!");
        }

    }


    private static void showHelp() {
        System.out.println("ADD - добавить заявку; \n" +
                "DEL - удалить заявку; \n" +
                "SHOW - показать все заявки; \n" +
                "SBFLIGHT - показать все заявки на рейс; \n" +
                "SBDATE - показать все заявки на дату; \n" +
                "? - показать справку. \n" +
                "E - выход из программы. \n");
    }

    static class DateValidation {
        static int MAX_VALID_YR = 9999;
        static int MIN_VALID_YR = Calendar.getInstance().get(Calendar.YEAR);

        private static boolean isLeap(int year)
        {
            return (((year % 4 == 0) &&
                    (year % 100 != 0)) ||
                    (year % 400 == 0));
        }

        static boolean isValidDate(int d,
                                   int m,
                                   int y)
        {
            if (y > MAX_VALID_YR ||
                    y < MIN_VALID_YR)
                return false;
            if (m < 1 || m > 12)
                return false;
            if (d < 1 || d > 31)
                return false;

            if (m == 2)
            {
                if (isLeap(y))
                    return (d <= 29);
                else
                    return (d <= 28);
            }
            if (m == 4 || m == 6 ||
                    m == 9 || m == 11)
                return (d <= 30);
            return true;
        }
    }
}
