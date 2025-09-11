package Util;

import Model.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FILE = "students.csv"; // File to save students
    private static List<Student> students = new ArrayList<>(); // In-memory list

    // Add student to list and save to file
    public static void addStudent(Student student) {
        students.add(student);
        saveToFile(student);
    }

    // Append student to CSV file
    private static void saveToFile(Student student) {
        try (FileWriter writer = new FileWriter(FILE, true)) { // append mode
            writer.write(student.toCSV() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all students in memory
    public static List<Student> getStudents() {
        return students;
    }
}
