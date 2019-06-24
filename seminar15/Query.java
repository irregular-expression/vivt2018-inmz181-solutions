package seminar15;

import java.util.Date;

public class Query {
    String location;
    String flight;
    String fio;
    Date date;

    Query setLocation(String location) {
        this.location = location;
        return this;
    }

    Query setFlight(String flight) {
        this.flight = flight;
        return this;
    }

    Query setFio(String fio) {
        this.fio = fio;
        return this;
    }

    Query setDate(Date date) {
        this.date = date;
        return this;
    }
}
