package View;

import Util.DataManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Model.Student;

public class EditStudentView {

    // Signature matches the call from ListStudentView
    public static Pane getView(Pane contentArea, Student student) {
        // Fields pre-filled with existing student data
        TextField idField = new TextField(student.getId());
        TextField nameField = new TextField(student.getName());
        TextField ageField = new TextField(String.valueOf(student.getAge()));
        TextField courseField = new TextField(student.getCourse());

        Label messageLabel = new Label();

        // Save changes button
        Button saveBtn = new Button("Save Changes");
        saveBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String course = courseField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || course.isEmpty()) {
                messageLabel.setText("❌ All fields are required.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                messageLabel.setText("❌ Age must be a valid number.");
                return;
            }

            Student updated = new Student(id, name, age, course);
            DataManager.updateStudent(student, updated);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Student updated successfully.", ButtonType.OK);
            alert.showAndWait();

            // Return to refreshed list view
            contentArea.getChildren().setAll(ListStudentView.getView(contentArea));
        });

        // Cancel / Back button - discards changes and returns to list
        Button cancelBtn = new Button("← Back to List");
        cancelBtn.setOnAction(e -> contentArea.getChildren().setAll(ListStudentView.getView(contentArea)));

        // Layout - use a GridPane for labels/fields
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Student ID:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Age:"), 0, 2);
        form.add(ageField, 1, 2);
        form.add(new Label("Course:"), 0, 3);
        form.add(courseField, 1, 3);
        form.add(saveBtn, 1, 4);

        VBox layout = new VBox(12, new Label("Edit Student"), form, messageLabel, cancelBtn);
        layout.setPadding(new Insets(16));

        return layout;
    }
}
