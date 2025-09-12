import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import View.AddStudentView;
import View.ListStudentView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create buttons for the main menu
        Button addBtn = new Button("Add Student");
        addBtn.setOnAction(e -> AddStudentView.display()); // Opens Add Student window

        Button listBtn = new Button("List Students");
        listBtn.setOnAction(e -> ListStudentView.display()); // ✅ Opens List Student window

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e -> primaryStage.close()); // Close the application

        // Layout for the main menu
        VBox layout = new VBox(20, addBtn, listBtn, exitBtn);
        layout.setStyle("-fx-padding: 50; -fx-alignment: center;");

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Management System");
        primaryStage.show(); // Show the main window
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }
}
