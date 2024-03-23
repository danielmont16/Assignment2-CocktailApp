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
import java.util.List;
//This class allows to interact with the model
public class SecondController {

    //defining properties to switchScene
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String idDrink;

    //using FXML annotations in order to use the elements added in the view (scene)
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

    //this method allows to display the information in the second scene
    public void setIdDrink(String idDrink) throws IOException {
        //setting the value of idDrink
        this.idDrink = idDrink;
        //displaying the value in the label id
        id.setText(idDrink);

        //creating an object ApiHttpClient
        ApiHttpClient data = new ApiHttpClient();
        //fetching the data and storing the response
        String response = data.fetchDataDetails(idDrink);
        //parsing the data and storing into a cocktailList
        List<Cocktail> cocktailList = data.parseJsonCocktailDetails(response);

        //using for each to iterate over the cocktailList elements
        cocktailList.forEach(element -> {
            //using Image class to handle the image parsed from the API
            Image cocktailImg = new Image(element.getStrDrinkThumb());
            //using the image view element to set the image
            img.setImage(cocktailImg);
            //using the textarea to display the instructions
            instructions.setText(element.getStrInstructions());
            //using the textarea to display the cocktail glass style
            cocktailGlass.setText(element.getStrGlass());
            //using the label to display the cocktail name
            cocktailName.setText(element.getStrDrink());
        });

        //the following code allows to retrieve and display the ingredients and measures
        //Storing the data from cocktailList into ingredient1
        List <String> ingredient1 = cocktailList.get(0).getIngredients();
        //Storing the data from cocktailList into measures
        List<String> measures = cocktailList.get(0).getMeasures();

        // Initialize ingMeasures type StringBuilder
        StringBuilder ingMeasures = new StringBuilder();


        for (int i = 0; i < ingredient1.size(); i++) {
            //iterating over ingredient1 and concatenating each ingredient with their measure
            String ingText = ingredient1.get(i) + " : " + measures.get(i)+"\n";
            //adding each element to the ingMeasures
            ingMeasures.append(ingText);
        }

        //using the textarea to display the data of ingredients and measures as a string
        ingredients.setText(ingMeasures.toString());
    }

    //The following method allows to return to the main scene
    public void switchToScene1(ActionEvent event) throws IOException {
        //setting root with the first-view fxml
        root = FXMLLoader.load(getClass().getResource("first-view.fxml"));
        //setting the stage using the given .fxml file
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //creating the scene
        scene = new Scene(root,600, 400);
        //adding the css file
        scene.getStylesheets().add("file:src/main/resources/org/example/assignment2cocktailapp/style.css");
        //adding the scene to the stage
        stage.setScene(scene);
        //showing the stage
        stage.show();

    }




}
