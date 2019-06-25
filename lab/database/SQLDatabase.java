package lab.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabase {

    public static void addToDb(Person person) throws SQLException {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);

                try {
                    statement.execute("insert into user(name, phone, sex, address, occupation) values(" +
                            person.getName() + ", " +
                            person.getPhone() + ", " +
                            person.isSex() + ", " +
                            person.getAddress() + ", " +
                            person.getOccupation() + ")");
                    connection.commit();
                } catch (SQLException e)  {
                    connection.rollback();
                }

                connection.setAutoCommit(true);

            }
        }
    }

    public static List<Person> getFromDb() throws SQLException {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("select * from user");
                while (rs.next()) {
                    persons.add(new Person(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getBoolean("sex"),
                            rs.getString("address"),
                            rs.getString("occupation")));
                }
            }
        }
        return persons;
    }


    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("create table if not exist user(" +
                    "id integer primary key auto_increment, " +
                    "name varchar(100)," +
                    "phone varchar(100)," +
                    "sex boolean," +
                    "address varchar(100)," +
                    "occupation varchar(100));");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test");
    }



}
