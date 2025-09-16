package View;

import Util.DataManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Model.Student;

public class AddStudentView {

    // Returns a Pane to be placed in the main content area
    public static Pane getView(Pane contentArea) {
        // Input fields
        TextField idField = new TextField();
        idField.setPromptText("e.g. 001");

        TextField nameField = new TextField();
        nameField.setPromptText("Full name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField courseField = new TextField();
        courseField.setPromptText("Course (e.g. Computer Science)");

        Label messageLabel = new Label();

        // Save button
        Button saveBtn = new Button("Save Student");
        saveBtn.setOnAction(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String course = courseField.getText().trim();

            // Basic validation
            if (id.isEmpty() || name.isEmpty() || ageText.isEmpty() || course.isEmpty()) {
                messageLabel.setText("❌ Please fill in all fields.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);

                // Create student with course included
                Student student = new Student(id, name, age, course);
                DataManager.addStudent(student);

                messageLabel.setText("✅ Student added successfully!");
                idField.clear();
                nameField.clear();
                ageField.clear();
                courseField.clear();
            } catch (NumberFormatException ex) {
                messageLabel.setText("❌ Age must be a number.");
            }
        });

        // Back button: returns to List view inside the same window
        Button backBtn = new Button("← Back to List");
        backBtn.setOnAction(e -> contentArea.getChildren().setAll(ListStudentView.getView(contentArea)));

        // Form layout
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

        // Container
        VBox layout = new VBox(15, form, messageLabel, backBtn);
        layout.setPadding(new Insets(20));

        return layout;
    }
}
