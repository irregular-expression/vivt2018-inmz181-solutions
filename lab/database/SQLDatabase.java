package lab.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabase {


    public static void clearAll() throws SQLException {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                try {
                    String query = "delete from user";
                    statement.execute(query);
                    connection.commit();
                } catch (SQLException e)  {
                    connection.rollback();
                    e.printStackTrace();
                }
                connection.setAutoCommit(true);
            }
        }
    }

    public static void addToDb(Person person) throws SQLException {
        try (Connection connection = getConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                try {
                    String query = "insert into user(name, phone, email) values('" +
                            person.getName() + "', '" +
                            person.getPhone() + "', '" +
                            person.getEmail() + "')";

                    statement.execute(query);
                    connection.commit();

                } catch (SQLException e)  {
                    connection.rollback();
                    e.printStackTrace();
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
                            rs.getString("email")));
                }
            }
        }
        return persons;
    }


    public static void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("create table if not exists user(" +
                    "id integer primary key auto_increment, " +
                    "name varchar(100)," +
                    "phone varchar(100)," +
                    "email varchar(100));");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:~/phonebook_db;DB_CLOSE_ON_EXIT=FALSE");
    }



}
