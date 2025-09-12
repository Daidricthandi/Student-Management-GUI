package Util;

import Model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String FILE = "students.csv";
    private static List<Student> students = new ArrayList<>();

    // Load students from file at startup
    static {
        loadFromFile();
    }

    // Create / add a student and persist
    public static void addStudent(Student student) {
        students.add(student);
        saveAllToFile();
    }

    public static List<Student> getStudents() {
        return students;
    }

    // Update using student ID matching
    public static void updateStudent(Student oldStudent, Student updatedStudent) {
        int idx = findIndexById(oldStudent.getId());
        if (idx >= 0) {
            students.set(idx, updatedStudent);
            saveAllToFile();
        }
    }

    // Delete by object (uses equals which is by id)
    public static void deleteStudent(Student student) {
        students.removeIf(s -> s.getId().equals(student.getId()));
        saveAllToFile();
    }

    // ---- compatibility wrappers the UI expects ----
    // removeStudent(...) was used in the List view — provide it
    public static void removeStudent(Student student) {
        deleteStudent(student);
    }

    // saveStudents() was called by your view — provide it
    public static void saveStudents() {
        saveAllToFile();
    }
    // ------------------------------------------------

    // Internal save helper - overwrites file with the full list
    private static void saveAllToFile() {
        try (FileWriter writer = new FileWriter(FILE)) {
            for (Student s : students) {
                writer.write(s.toCSV() + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read students from the CSV file
    private static void loadFromFile() {
        students.clear();
        File file = new File(FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    try {
                        String id = parts[0];
                        String name = parts[1];
                        int age = Integer.parseInt(parts[2]);
                        String course = parts[3];
                        students.add(new Student(id, name, age, course));
                    } catch (NumberFormatException nfe) {
                        // skip invalid line
                        System.err.println("Skipping invalid student line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper: find index by ID
    private static int findIndexById(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
}
