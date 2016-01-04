import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.util.Set;

/**
 * Created by hyq on 2016/1/3.
 */
public class Application {
    private static final String dbServer;
    private static final int dbPort;
    private static final String dbName;
    private static final String dbUsername;
    private static final String dbPassword;
    private static final MysqlDataSource ds;

    static {
        dbServer = "localhost";
        dbPort = 3306;
        dbName = "test";
        dbUsername = "root";
        dbPassword = "123456";
        ds = new MysqlConnectionPoolDataSource();
        ds.setServerName(dbServer);
        ds.setPort(dbPort);
        ds.setDatabaseName(dbName);
        ds.setUser(dbUsername);
        ds.setPassword(dbPassword);
        try {
            DatabaseMetaData databaseMetaData = ds.getConnection().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        clear();
        printAllPersons();
//        printOnePerson(2);
        addOnePerson(1, "黄", "玉强", 36);
        addOnePerson(2, "卢", "文", 33);
        addOnePerson(3, "黄", "歆瑶", 4);
        printAllPersons();
        modifyAge();
        printAllPersons();
    }

    private static void clear() {
        try (Connection connection = ds.getConnection()) {
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                statement.executeUpdate("DELETE from persons ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void modifyAge() {
        try (Connection connection = ds.getConnection()) {
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ResultSet rs = statement.executeQuery("select id,first_name,last_name,age from persons ");
                while (rs.next()) {
                    rs.updateNString(2, "实"); // for supporting unicode, use updateNString, not updateString
                    rs.updateNString(3, "岁");
                    rs.updateInt(4, 20);
                    rs.updateRow();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static void addOnePerson(long id, String firstName, String lastName, int age) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);
        try (Connection connection = getConnection()) {
            PersonDao personDao = new PersonDao(connection);
            personDao.add(person);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private static void printOnePerson(int id) {
        try (Connection connection = getConnection()) {
            PersonDao personDao = new PersonDao(connection);
            Person person = personDao.findOne(id);
            System.out.println("========");
            System.out.printf("Person for id(%d):\n", id);
            System.out.println(person);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private static void printAllPersons() {
        try (Connection connection = getConnection()) {
            PersonDao personDao = new PersonDao(connection);
            Set<Person> persons = personDao.findAll();
            System.out.println("========");
            System.out.println("All Persons:");
            persons.forEach(System.out::println);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
