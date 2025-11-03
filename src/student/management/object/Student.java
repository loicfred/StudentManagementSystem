package student.management.object;

public class Student {

    private long ID;
    private String Surname;
    private String OtherNames;
    private String Gender;
    private int Age;

    // the constructor of Student object
    public Student(long ID, String surname, String otherNames, String gender, int age) {
        this.ID = ID;
        Surname = surname;
        OtherNames = otherNames;
        Gender = gender;
        Age = age;
    }

    // getters
    public long getId() {
        return ID;
    }
    public String getSurname() {
        return Surname;
    }
    public String getOtherNames() {
        return OtherNames;
    }
    public String getGender() {
        return Gender;
    }
    public int getAge() {
        return Age;
    }

    // settings
    public void setId(long id) {
        ID = id;
    }
    public void setSurname(String surname) {
        Surname = surname;
    }
    public void setOtherNames(String otherNames) {
        OtherNames = otherNames;
    }
    public void setGender(String gender) {
        Gender = gender;
    }
    public void setAge(int age) {
        Age = age;
    }

    public enum Attribute {
        ID("ID"),
        SURNAME("Surname"),
        OTHERNAMES("Other names"),
        GENDER("Gender"),
        AGE("Age");

        public final String name;
        public String getName() {
            return name;
        }
        Attribute(String name) {
            this.name = name;
        }
    }
}
