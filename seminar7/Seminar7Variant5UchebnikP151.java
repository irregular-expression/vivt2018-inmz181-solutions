package seminar7;

import java.io.*;

public class Seminar7Variant5UchebnikP151 {

    private static final String IO_ERROR_MESSAGE = "Произошла ошибка ввода-вывода. Перезапустите приложение и/или проверьте работоспособность системного метода консольного ввода";

    /**
     * Консольное приложение на Java
     * @param args - параметр метода main() по умолчанию в Java. Не используется в коде.
     */
    public static void main(String[] args) {
        System.out.println("Учебник, задания со с.151. Вариант 5.");
        System.out.println("Программа создаёт файл с данными о 6 сканерах, параметры которых вводятся с \n" +
                "клавиатуры и вносятся в объекты ScanInfo. Данные могут записываться в любую позицию от 0 до 5. После записи \n" +
                "пустые позиции, заполненные нулями, \"уплотняются\".");

        int MAX_SCANNERS_COUNT = 6;
        ScanInfo[] scanners = new ScanInfo[MAX_SCANNERS_COUNT];

        for (int i = 0; i < scanners.length; i++) {
            try {
                scanners[getPositionNumber(i)] = getScanInfo();
            } catch (IOException e) {
                System.out.println(IO_ERROR_MESSAGE);
            }
        }

        System.out.println("\n Данные сканеров созданы. Записываем их в бинарный файл seminar.dat согласно условию задачи.");

        try {
            RandomAccessFile file = getFile();
            System.out.println("Файл создан, размер файла " + file.length() + " байт");
            file.writeInt(MAX_SCANNERS_COUNT);
            System.out.println("В файл записано количество сканеров, размер файла " + file.length() + " байт");
            for (ScanInfo scanner : scanners) writeScanInfo(file, scanner); //пишем данные о сканерах
            compressFile(file);
            file.close();

        } catch (IOException e) {
            System.out.println(IO_ERROR_MESSAGE);
        }


    }


    private static int getPositionNumber(int num) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n;
        System.out.printf("Введите номер позиции от 0 до 5 для записи данных о сканере %d: ", num);
        try {
            String s = reader.readLine();
            n = Integer.parseInt(s);
            if (n < 0 || n >= 6) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод! Допустимы числа от 0 до 5.");
            return getPositionNumber(num);
        }
        return n;
    }

    private static ScanInfo getScanInfo() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ScanInfo scanner = new ScanInfo();
        try {
            System.out.print("Введите название сканера (до 25 символов): ");
            String s = reader.readLine();
            if (s.length() > 25) s = s.substring(0, 25);
            else if (s.length() < 25) s = String.format("%-25s", s);
            scanner.model = s.toCharArray();
            System.out.print("Введите цену (целое число): ");
            scanner.price = Integer.parseInt(reader.readLine());
            System.out.print("Введите горизонтальный размер области сканирования: ");
            scanner.x_size = Double.parseDouble(reader.readLine());
            System.out.print("Введите вертикальный размер области сканирования: ");
            scanner.y_size = Double.parseDouble(reader.readLine());
            System.out.print("Введите оптическое разрешение: ");
            scanner.optr = Integer.parseInt(reader.readLine());
            System.out.print("Введите число градаций серого: ");
            scanner.grey = Integer.parseInt(reader.readLine());
            System.out.printf("Данные сканера созданы: \n model : %s \n price : %d \n x_size : %.2f \n y_size : %.2f \n optr : %d  \n grey : %d \n",
                    s, scanner.price, scanner.x_size, scanner.y_size, scanner.optr, scanner.grey);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод числа! Попытайтесь снова.");
            return getScanInfo();
        }
        return new ScanInfo();
    }

    private static byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInput in = new ObjectInputStream(bis)) {
            return in.readObject();
        }
    }

    private static RandomAccessFile getFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Введите полный путь директории для создания файла: ");
            String path = reader.readLine();
            File f = new File(path);
            if (!f.exists() || !f.isDirectory()) throw new FileNotFoundException();
            return new RandomAccessFile(new File(path + "seminar.dat"), "rw");
        } catch (FileNotFoundException e) {
            System.out.println("Указанная директория не существует или в ней невозможно создать файл.");
            return getFile();
        }
    }

    private static RandomAccessFile writeScanInfo(RandomAccessFile file, ScanInfo info) throws IOException {
        if (info != null) {
            file.write(convertToBytes(info.model));
            file.writeInt(info.price);
            file.writeDouble(info.x_size);
            file.writeDouble(info.y_size);
            file.writeInt(info.optr);
            file.writeInt(info.grey);
            System.out.println("Сохранены данные сканера, размер файла " + file.length() + " байт");
        } else {
            /*1 сериализованный объект ScanInfo занимает 105 байт, тип null занимает 5 байт, поэтому
            заполняем 105 байт нулями для сохранения кратности позиций. */
            for (int i = 0; i < 105; i++) file.write((byte) 0);
            System.out.println("Данные несуществующего сканера заполнены нулями, размер файла " + file.length() + " байт");
        }
        return file;
    }

    private static RandomAccessFile compressFile(RandomAccessFile file) throws IOException {
        System.out.println("Выполняется сжатие файла, размер файла до сжатия " + file.length() + " байт");
        long pointerPos = 4;
        file.seek(0);
        int scannersCount = file.readInt();
        for (long i = pointerPos; i < file.length(); i += 105) {
            file.seek(i);
            boolean hasClearData = true;
            for (int j = 0; j < 105; j++) if (file.readByte() != 0) hasClearData = false;
            if (hasClearData) {
                long startPos = file.getFilePointer() - 105;
                long resultLength = file.length() - 105;
                byte[] data = new byte[(int) resultLength - (int) startPos];
                file.readFully(data);
                file.seek(startPos);
                file.write(data);
                file.setLength(resultLength);
                scannersCount--;
            }
        }
        file.seek(0);
        file.writeInt(scannersCount);
        System.out.println("Сжатие файла завершено, размер файла после сжатия " + file.length() + " байт");
        System.out.println("Файл содержит данные о " + scannersCount + " сканерах.");

        return file;
    }
}
