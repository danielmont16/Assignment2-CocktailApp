package org.example.assignment2cocktailapp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//This class allows to start the stage and set the first scene
public class CocktailApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the first scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("first-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);

        //adding a custom css file to the scene
        scene.getStylesheets().add("file:src/main/resources/org/example/assignment2cocktailapp/style.css");

        // Set the first scene
        primaryStage.setScene(scene);
        //adding an icon to the Stage
        Image applicationIcon = new Image("file:src/main/resources/org/example/assignment2cocktailapp/zicon.png");
        primaryStage.getIcons().add(applicationIcon);
        //setting the title to the Stage
        primaryStage.setTitle("Cocktail Application");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
