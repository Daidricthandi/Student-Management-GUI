package View;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.Student;
import Util.DataManager;

public class ListStudentView {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("List of Students");

        // Create a search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search by ID or Name...");

        // Table to display students
        TableView<Student> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(DataManager.getStudents()));

        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getId()));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        table.getColumns().addAll(idColumn, nameColumn);

        // Wrap the student list in a FilteredList for searching
        FilteredList<Student> filteredData = new FilteredList<>(FXCollections.observableArrayList(DataManager.getStudents()), p -> true);
        table.setItems(filteredData);

        // Filter logic: updates when user types
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all if search is empty
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return student.getId().toLowerCase().contains(lowerCaseFilter) ||
                        student.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Edit button
        Button editButton = new Button("Edit Selected");
        editButton.setOnAction(e -> editSelectedStudent(table));

        // Delete button
        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> deleteSelectedStudent(table, filteredData));

        // Layout
        HBox buttonLayout = new HBox(10, editButton, deleteButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, searchField, table, buttonLayout);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }

    // Method to edit a selected student
    private static void editSelectedStudent(TableView<Student> table) {
        Student selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a student to edit.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog(selected.getName());
        dialog.setTitle("Edit Student");
        dialog.setHeaderText("Editing Student: " + selected.getId());
        dialog.setContentText("Enter new name:");

        dialog.showAndWait().ifPresent(newName -> {
            selected.setName(newName);
            DataManager.saveStudents();
            table.refresh();
        });
    }

    // Method to delete a selected student
    private static void deleteSelectedStudent(TableView<Student> table, FilteredList<Student> filteredData) {
        Student selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a student to delete.");
            return;
        }
        DataManager.removeStudent(selected);
        filteredData.getSource().remove(selected);
        table.refresh();
    }

    // Helper method for alerts
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
