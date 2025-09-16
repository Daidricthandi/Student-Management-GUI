import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import View.AddStudentView;
import View.ListStudentView;

public class Main extends Application {

    private StackPane contentArea; //This is where views will load

    @Override
    public void start(Stage primaryStage) {
        // Navigation buttons
        Button addBtn = new Button("Add Student");
        Button listBtn = new Button("List Students");
        Button exitBtn = new Button("Exit");

        // Layout for the navigation menu
        VBox navMenu = new VBox(20, addBtn, listBtn, exitBtn);
        navMenu.setStyle("-fx-padding: 20; -fx-background-color: #dfe6e9; -fx-alignment: top-center;");

        //content area
        contentArea = new StackPane();
        contentArea.setStyle("-fx-padding: 20;");

        //Main layout
        BorderPane root = new BorderPane();
        root.setLeft(navMenu);
        root.setCenter(contentArea);

        //Buttons actions
        addBtn.setOnAction(e -> setContent(AddStudentView.getView(contentArea)));
        listBtn.setOnAction(e -> setContent(ListStudentView.getView(contentArea)));
        exitBtn.setOnAction(e -> primaryStage.close());

        //show the main scene
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Management System");
        primaryStage.show();
    }

    //Helper method to switch content
    private void setContent(javafx.scene.layout.Pane view) {
        contentArea.getChildren().setAll(view);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
