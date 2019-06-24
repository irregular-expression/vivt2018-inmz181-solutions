package seminar7;

import java.io.Serializable;

class ScanInfo implements Serializable {  //В Java сериализуемый в массив байтов объект должен реализовывать интерфейс Serializable
    char model[]; //   наименование  модели
    int price; //   цена
    double  x_size; //   горизонтальный  размер  области  сканирования
    double  y_size; //   вертикальный  размер  области  сканирования
    int  optr; //   оптическое  разрешение
    int  grey; //   число  градаций  серого

    protected ScanInfo() {
        this.model = new char[25];
    }
}
