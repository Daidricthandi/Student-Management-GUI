package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.Student;
import Util.DataManager;

public class AddStudentView {

    public static void display() {
        Stage window = new Stage(); // Create new window
        window.setTitle("Add Student");

        // Label for showing messages
        Label messageLabel = new Label();

        // Input fields
        TextField idField = new TextField();
        idField.setPromptText("ID");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField courseField = new TextField();
        courseField.setPromptText("Course");

        // Save button
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            try {
                String id = idField.getText();
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String course = courseField.getText();

                // Validation: no empty fields
                if(id.isEmpty() || name.isEmpty() || course.isEmpty()) {
                    messageLabel.setText("Please fill in all fields!");
                    return;
                }

                Student student = new Student(id, name, age, course);
                DataManager.addStudent(student);

                // Show confirmation in the window
                messageLabel.setText("Student saved: " + student.getName());

                // Clear input fields
                idField.clear();
                nameField.clear();
                ageField.clear();
                courseField.clear();

            } catch (NumberFormatException ex) {
                messageLabel.setText("Age must be a number!");
            }
        });

        // Layout
        VBox layout = new VBox(10,
                new Label("Enter student details:"),
                idField, nameField, ageField, courseField,
                saveBtn,
                messageLabel
        );
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layout, 300, 350);
        window.setScene(scene);
        window.show();
    }
}
