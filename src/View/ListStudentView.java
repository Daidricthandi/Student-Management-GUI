package View;

import Util.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import Model.Student;

public class ListStudentView {

    public static Pane getView(Pane contentArea) {
        // Table setup
        TableView<Student> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Student, Number> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getAge()));

        TableColumn<Student, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCourse()));

        table.getColumns().addAll(idCol, nameCol, ageCol, courseCol);

        // Load data
        ObservableList<Student> students = FXCollections.observableArrayList(DataManager.getStudents());
        table.setItems(students);

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search by ID or Name...");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                table.setItems(FXCollections.observableArrayList(DataManager.getStudents()));
            } else {
                ObservableList<Student> filtered = FXCollections.observableArrayList();
                for (Student s : DataManager.getStudents()) {
                    if (s.getId().toLowerCase().contains(newVal.toLowerCase())
                            || s.getName().toLowerCase().contains(newVal.toLowerCase())) {
                        filtered.add(s);
                    }
                }
                table.setItems(filtered);
            }
        });

        // Buttons
        Button addBtn = new Button("➕ Add New Student");
        addBtn.setOnAction(e -> contentArea.getChildren().setAll(AddStudentView.getView(contentArea)));

        Button editBtn = new Button("✏️ Edit Selected");
        editBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                contentArea.getChildren().setAll(EditStudentView.getView(contentArea, selected));
            } else {
                showAlert("No Selection", "Please select a student to edit.");
            }
        });

        Button deleteBtn = new Button("🗑️ Delete Selected");
        deleteBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to delete student " + selected.getName() + "?",
                        ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        DataManager.removeStudent(selected);
                        table.setItems(FXCollections.observableArrayList(DataManager.getStudents()));
                    }
                });
            } else {
                showAlert("No Selection", "Please select a student to delete.");
            }
        });

        // Layout
        HBox controls = new HBox(10, addBtn, editBtn, deleteBtn);
        controls.setPadding(new Insets(10));

        VBox topBox = new VBox(10, searchField, controls);
        topBox.setPadding(new Insets(10));

        BorderPane layout = new BorderPane();
        layout.setTop(topBox);
        layout.setCenter(table);

        return layout;
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
