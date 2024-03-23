package org.example.assignment2cocktailapp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.List;

//This class allows to interact with the model
public class CocktailController {

    //using FXML annotations in order to use the elements added in the view (scene)
    @FXML
    private TextField firstLetter;

    @FXML
    private TableView<Cocktail> cocktailTable;

    @FXML
    private TableColumn<Cocktail, String> idDrinkColumn;

    @FXML
    private TableColumn<Cocktail, String> strDrinkColumn;

    @FXML
    private TableColumn<Cocktail, String> strCategoryColumn;

    @FXML
    private ImageView mainImage;

    @FXML
    private Label cocktailCount;

    //creating an object type ApiHttpClient to handle with the API connection
    ApiHttpClient apiCall = new ApiHttpClient();

    //creating a method to handle with the user clicked the search button
    @FXML
    protected void onButtonClick() throws IOException {
        //store the letter typed by the user into fLetter
        String fLetter = firstLetter.getText();
        //fetching the data from API using the fLetter
        String response = apiCall.fetchData(fLetter);
        //storing the data retrieved into cocktailList
        List<Cocktail> cocktailList = apiCall.parseJsonCocktails(response);
        //passing the data to an observable list to be manipulated with a table
        ObservableList<Cocktail> observableCocktailList = FXCollections.observableArrayList(cocktailList);
        //clearing the items in the table
        cocktailTable.getItems().clear();
        //adding the data retrieved with the API
        cocktailTable.getItems().addAll(observableCocktailList);
        //passing the data into each column. I have 3 columns in the table.
        idDrinkColumn.setCellValueFactory(cellData-> cellData.getValue().idDrinkProperty());
        strDrinkColumn.setCellValueFactory(cellData -> cellData.getValue().strDrinkProperty());
        strCategoryColumn.setCellValueFactory(cellData->cellData.getValue().strCategoryProperty());
        //passing the numbers of elements in cocktailList to the label cocktailCount
        cocktailCount.setText(String.valueOf(cocktailList.size()));
    }

    //setting the initialize method in order to handle when the user clicked on the table.
    @FXML
    protected void initialize() throws IOException {
        // Set a row factory for the TableView
        cocktailTable.setRowFactory(new Callback<TableView<Cocktail>, TableRow<Cocktail>>() {
            @Override
            public TableRow<Cocktail> call(TableView<Cocktail> tableView) {
                final TableRow<Cocktail> row = new TableRow<>();
                //using event handler setOnMouseClicked
                row.setOnMouseClicked(event -> {
                    // Check for double-click
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        Cocktail selectedCocktail = row.getItem();
                        //using try/catch block to handle any exception
                        try {
                            //getting the idDrink that was clicked
                            String idDrink1 = selectedCocktail.getIdDrink();
                            // Call method to switch scene
                            switchToScene(idDrink1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                return row;
            }
        });

        //using Image class to pass an image
        Image cocktailImage = new Image("file:src/main/resources/org/example/assignment2cocktailapp/zbartender.jpg");
        //adding an image into the scene
        mainImage.setImage(cocktailImage);

    }

    //the following method allows to change the scene
    private void switchToScene(String idDrink) throws IOException {

        //using FXMLLoader class in order to use a second .fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Second-view.fxml"));
        Parent root = loader.load();
        SecondController controller = loader.getController();
        //passing the value of idDrink into the controller
        controller.setIdDrink(idDrink);

        //setting the Stage
        Stage stage = (Stage) cocktailTable.getScene().getWindow();
        Scene scene = new Scene(root);
        //adding the style file
        scene.getStylesheets().add("file:src/main/resources/org/example/assignment2cocktailapp/style.css");
        stage.setScene(scene);
        stage.show();

    }

}


