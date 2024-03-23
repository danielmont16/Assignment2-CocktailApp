package org.example.assignment2cocktailapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String idDrink;

    @FXML
    private Label id;

    @FXML
    private Label cocktailName;

    @FXML
    private ImageView img;

    @FXML
    private TextArea instructions;

    @FXML
    private TextArea ingredients;

    @FXML
    private TextArea cocktailGlass;

    public void setIdDrink(String idDrink) throws IOException {
        this.idDrink = idDrink;
        id.setText(idDrink);


        ApiHttpClient data = new ApiHttpClient();
        String response = data.fetchDataDetails(idDrink);
        List<Cocktail> cocktailList = data.parseJsonCocktailDetails(response);

        cocktailList.forEach(element -> {
            Image cocktailImg = new Image(element.getStrDrinkThumb());
            img.setImage(cocktailImg);
            instructions.setText(element.getStrInstructions());
            cocktailGlass.setText(element.getStrGlass());
            cocktailName.setText(element.getStrDrink());
        });

        List<String> ingredient1 = new ArrayList<>();
        List<String> measures = new ArrayList<>();

        ingredient1 = cocktailList.get(0).getIngredients();
        measures = cocktailList.get(0).getMeasures();

        StringBuilder ingMeasures = new StringBuilder(); // Initialize StringBuilder here
        for (int i = 0; i < ingredient1.size(); i++) {
            String ingText = ingredient1.get(i) + " : " + measures.get(i)+"\n";
            ingMeasures.append(ingText);
        }

        ingredients.setText(ingMeasures.toString()); // Set text after the loop
    }


    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("first-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root,600, 400);
        scene.getStylesheets().add("file:src/main/resources/org/example/assignment2cocktailapp/style.css");
        stage.setScene(scene);
        stage.show();

    }




}
