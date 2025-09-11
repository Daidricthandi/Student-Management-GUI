package Model;

public class Student {
    private String id;
    private String name;
    private int age;
    private String course;

    // Constructor
    public Student(String id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }

    // Convert student to CSV line for saving to file
    public String toCSV() {
        return id + "," + name + "," + age + "," + course;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
