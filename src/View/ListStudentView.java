package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.Student;
import Util.DataManager;

public class ListStudentView {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("List Students");

        TableView<Student> table = new TableView<>();

        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Student, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        table.getColumns().addAll(idColumn, nameColumn, ageColumn, courseColumn);

        ObservableList<Student> studentsList = FXCollections.observableArrayList(DataManager.getStudents());
        table.setItems(studentsList);

        Button editBtn = new Button("Edit Selected");
        editBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                EditStudentView.display(selected);
                table.setItems(FXCollections.observableArrayList(DataManager.getStudents()));
            } else {
                showAlert("No selection", "Please select a student to edit.");
            }
        });

        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                DataManager.deleteStudent(selected);
                table.setItems(FXCollections.observableArrayList(DataManager.getStudents()));
            } else {
                showAlert("No selection", "Please select a student to delete.");
            }
        });

        VBox layout = new VBox(10, table, editBtn, deleteBtn);
        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
