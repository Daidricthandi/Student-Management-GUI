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

public class EditStudentView {

    public static void display(Student student) {
        Stage window = new Stage();
        window.setTitle("Edit Student");

        Label messageLabel = new Label();

        TextField idField = new TextField(student.getId());
        TextField nameField = new TextField(student.getName());
        TextField ageField = new TextField(String.valueOf(student.getAge()));
        TextField courseField = new TextField(student.getCourse());

        Button saveBtn = new Button("Save Changes");
        saveBtn.setOnAction(e -> {
            try {
                String id = idField.getText();
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String course = courseField.getText();

                Student updated = new Student(id, name, age, course);
                DataManager.updateStudent(student, updated);

                messageLabel.setText("Student updated: " + updated.getName());
            } catch (NumberFormatException ex) {
                messageLabel.setText("Age must be a number!");
            }
        });

        VBox layout = new VBox(10, new Label("Edit student details:"),
                idField, nameField, ageField, courseField,
                saveBtn, messageLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(layout, 300, 350);
        window.setScene(scene);
        window.show();
    }
}
