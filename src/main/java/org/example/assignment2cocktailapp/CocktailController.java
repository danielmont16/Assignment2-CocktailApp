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

public class CocktailController {

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

    ApiHttpClient apiCall = new ApiHttpClient();

    @FXML
    protected void onButtonClick() throws IOException {
        String fLetter = firstLetter.getText();
        String response = apiCall.fetchData(fLetter);
        List<Cocktail> cocktailList = apiCall.parseJsonCocktails(response);
        ObservableList<Cocktail> observableCocktailList = FXCollections.observableArrayList(cocktailList);
        cocktailTable.getItems().clear();
        cocktailTable.getItems().addAll(observableCocktailList);
        idDrinkColumn.setCellValueFactory(cellData-> cellData.getValue().idDrinkProperty());
        strDrinkColumn.setCellValueFactory(cellData -> cellData.getValue().strDrinkProperty());
        strCategoryColumn.setCellValueFactory(cellData->cellData.getValue().strCategoryProperty());

        cocktailCount.setText(String.valueOf(cocktailList.size()));
    }


    @FXML
    protected void initialize() throws IOException {
        // Set a row factory for the TableView
        cocktailTable.setRowFactory(new Callback<TableView<Cocktail>, TableRow<Cocktail>>() {
            @Override
            public TableRow<Cocktail> call(TableView<Cocktail> tableView) {
                final TableRow<Cocktail> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) { // Check for double-click
                        Cocktail selectedCocktail = row.getItem();

                        try {
                            String idDrink1 = selectedCocktail.getIdDrink();
                            switchToScene(idDrink1); // Call method to switch scene
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                return row;
            }
        });

        Image cocktailImage = new Image("file:src/main/resources/org/example/assignment2cocktailapp/zbartender.jpg");

        mainImage.setImage(cocktailImage);



    }

private void switchToScene(String idDrink) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Second-view.fxml"));
    Parent root = loader.load();


    SecondController controller = loader.getController();
    controller.setIdDrink(idDrink); // Set idDrink value in Scene1Controller

    Stage stage = (Stage) cocktailTable.getScene().getWindow();
    Scene scene = new Scene(root);
    scene.getStylesheets().add("file:src/main/resources/org/example/assignment2cocktailapp/style.css");
    stage.setScene(scene);
    stage.show();


}

}


