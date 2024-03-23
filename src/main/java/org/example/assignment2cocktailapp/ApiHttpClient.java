package org.example.assignment2cocktailapp;
import com.google.gson.*;
import javafx.collections.FXCollections;
import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

//this class is used to fetch the API data

public class ApiHttpClient {

    //this method is used to fetch data using the first endpoint that the API offers.
    public String fetchData(String fLetter) throws IOException {
        //in the url variable I am storing the complete endpoint
        //fLetter is the first letter that the user should type
        String url= "https://www.thecocktaildb.com/api/json/v1/1/search.php?f="+fLetter;

        //using try and catch to handle with any error with the connection with the API
        try {
            // Create HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Create HTTP request targeting the provided URL
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();

            // Send HTTP request and obtain HTTP response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response was successful (status code 200)
            if (response.statusCode() == 200) {
                // Return the response body as a String
                return response.body();
            } else {
                // Throw IOException with informative error message for non-200 status code
                throw new IOException("Failed to fetch data. HTTP Status Code: " + response.statusCode());
            }
        } catch (URISyntaxException e) {
            // Throw IOException with informative error message for invalid URL
            throw new IOException("Invalid URL: " + url);
        } catch (InterruptedException e) {
            // Throw IOException with informative error message for interruption during HTTP request
            throw new IOException("Failed to fetch data. Request was interrupted.");
        }
    }//end of fetchData

    // The following method is used to parse the data that comes from the API
    public List<Cocktail> parseJsonCocktails(String responseBody) {
        //creating a list to store the data
        List<Cocktail> cocktailList = new ArrayList<>();

        // Parsing the JSON response to get the root object using the class Cocktail
        JsonObject rootObject = new Gson().fromJson(responseBody, JsonObject.class);

        // Check if the "drinks" array exists
        if (rootObject.has("drinks")) {
            // Get the "drinks" array
            JsonArray drinksArray = rootObject.getAsJsonArray("drinks");

            // Iterate over each drink object in the array
            for (JsonElement element : drinksArray) {
                // Convert each drink object to a Cocktail object
                Cocktail cocktail = new Cocktail();

                // Extract the "strDrink" field from the drink object
                JsonObject drinkObject = element.getAsJsonObject();
                if (drinkObject.has("strDrink")) {
                    //matching the object name with the class name define
                    String idDrink = drinkObject.get("idDrink").getAsString();
                    String strDrink = drinkObject.get("strDrink").getAsString();
                    String strCategory = drinkObject.get("strCategory").getAsString();

                    //using the set method define in the model (Cocktail class)
                    cocktail.setIdDrink(idDrink);
                    cocktail.setStrDrink(strDrink);
                    cocktail.setStrCategory(strCategory);
                }

                // Add the Cocktail object to the list
                cocktailList.add(cocktail);
            }
        }
        //returning cocktailList
        return cocktailList;
    }


    //this method is used to fetch the data from the second endpoint
    //the key to fetch the data is the idDrink
    public String fetchDataDetails(String idDrink) throws IOException {

        //Store the complete endpoint adding the idDrink as a key
        String url= "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="+idDrink;

        try {
            // Create HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Create HTTP request targeting the provided URL
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();

            // Send HTTP request and obtain HTTP response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response was successful (status code 200)
            if (response.statusCode() == 200) {
                // Return the response body as a String
                return response.body();
            } else {
                // Throw IOException with informative error message for non-200 status code
                throw new IOException("Failed to fetch data. HTTP Status Code: " + response.statusCode());
            }
        } catch (URISyntaxException e) {
            // Throw IOException with informative error message for invalid URL
            throw new IOException("Invalid URL: " + url);
        } catch (InterruptedException e) {
            // Throw IOException with informative error message for interruption during HTTP request
            throw new IOException("Failed to fetch data. Request was interrupted.");
        }
    }//end of fetchDataDetails

    //This method is used to parse the data from the fetchDataDetails method
    public List<Cocktail> parseJsonCocktailDetails(String responseBody) {

        //creating a list to store the data
        List<Cocktail> cocktailListDetail = new ArrayList<>();

        // Parse the JSON response to get the root object
        JsonObject rootObject = new Gson().fromJson(responseBody, JsonObject.class);

        // Check if the "drinks" array exists
        if (rootObject.has("drinks")) {
            // Get the "drinks" array
            JsonArray drinksArray = rootObject.getAsJsonArray("drinks");

            // Iterate over each drink object in the array
            for (JsonElement element : drinksArray) {
                // Convert each drink object to a Cocktail object
                Cocktail cocktail = new Cocktail();

                // Extract the "strDrink" field from the drink object
                JsonObject drinkObject = element.getAsJsonObject();
                if (drinkObject.has("idDrink")) {
                    String idDrink = drinkObject.get("idDrink").getAsString();
                    String strDrink = drinkObject.get("strDrink").getAsString();
                    String strInstructions = drinkObject.get("strInstructions").getAsString();
                    String strGlass = drinkObject.get("strGlass").getAsString();
                    String strDrinkThumb = drinkObject.get("strDrinkThumb").getAsString();

                    //using the set methods defines in the model (Cocktail class)
                    cocktail.setIdDrink(idDrink);
                    cocktail.setStrDrink(strDrink);
                    cocktail.setStrInstructions(strInstructions);
                    cocktail.setStrGlass(strGlass);
                    cocktail.setStrDrinkThumb(strDrinkThumb);
                }

                // Fetch ingredients and measures
                List<String> ingredients = new ArrayList<>();
                List<String> measures = new ArrayList<>();

                // This loop iterate over the ingredients and measures, because
                // the JSON structure gives me a list of separate names for each ingredient and measure
                for (int i = 1; i <= 15; i++) {
                    //matching the name of each ingredient with the model (Cocktail class)
                    String ingredient = getStringValue(drinkObject, "strIngredient" + i);
                    //matching the name of each measure with the model (Cocktail class)
                    String measure = getStringValue(drinkObject, "strMeasure" + i);
                    //the following "if" deals with the case of null elements
                    if (ingredient != null && !ingredient.isEmpty()) {
                        ingredients.add(ingredient);
                        measures.add(measure != null ? measure : "");
                    } else {
                        // Break out of the loop if there are no more ingredients
                        break;
                    }
                }

                // Set ingredients and measures
                cocktail.setIngredients(FXCollections.observableArrayList(ingredients));
                cocktail.setMeasures(FXCollections.observableArrayList(measures));

                // Add the Cocktail object to the list
                cocktailListDetail.add(cocktail);


            }
        }
    //return cocktailListDetail
        return cocktailListDetail;
    }

    //this method is used to handle with the cases of null elements being passed to the JSON object
    //some of the ingredients and measures could be null and that will crash the application
    private String getStringValue(JsonObject jsonObject, String key) {
        //using each strIngredient to check if is null object
        if (jsonObject.has(key)) {
            JsonElement element = jsonObject.get(key);
            //return elements with not null values
            if (!element.isJsonNull()) {
                return element.getAsString();
            }
        }
        return null;
    }

}
