/**
 * Created by hyq on 2016/1/3.
 */

public class Person implements Comparable<Person> {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        return (int)(this.id - o.getId());
    }

    @Override
    public String toString() {
        return getClass().getName() + "(id:" + id + "," +
                "firstName:" + firstName + "," +
                "lastName:" + lastName + "," +
                "age:" + age + ")";
    }
}
