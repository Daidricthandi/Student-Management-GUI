package Util;

import Model.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FILE = "students.csv";
    private static List<Student> students = new ArrayList<>();

    // Load students from file at start
    static {
        loadFromFile();
    }

    public static void addStudent(Student student) {
        students.add(student);
        saveAllToFile();
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static void updateStudent(Student oldStudent, Student updatedStudent) {
        int index = students.indexOf(oldStudent);
        if (index >= 0) {
            students.set(index, updatedStudent);
            saveAllToFile();
        }
    }

    public static void deleteStudent(Student student) {
        students.remove(student);
        saveAllToFile();
    }

    private static void saveAllToFile() {
        try (FileWriter writer = new FileWriter(FILE)) {
            for (Student s : students) {
                writer.write(s.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromFile() {
        students.clear();
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Student s = new Student(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
                    students.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
