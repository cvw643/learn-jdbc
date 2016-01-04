
import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by hyq on 2016/1/3.
 */
public class PersonDao {
    private Connection connection = null;

    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    public Set<Person> findAll() throws SQLException {
        Set<Person> persons = new TreeSet<>();
        String query = "select * from persons";

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getLong("id"));
                person.setFirstName(rs.getNString("first_name"));
                person.setLastName(rs.getNString("last_name"));
                person.setAge(rs.getInt("age"));
                persons.add(person);
            }
        }

        return persons;
    }

    public Person findOne(long id) throws SQLException {
        String query = "select * from persons where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getLong("id"));
                person.setFirstName(rs.getNString("first_name"));
                person.setLastName(rs.getNString("last_name"));
                person.setAge(rs.getInt("age"));
                return person;
            }
        }

        return null;
    }

    public void add(Person person) throws SQLException {
        String insert = "insert into persons(id, first_name, last_name, age) values(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setLong(1, person.getId());
            statement.setNString(2, person.getFirstName());
            statement.setNString(3, person.getLastName());
            statement.setInt(4, person.getAge());
            statement.executeUpdate();
        }
    }

}
