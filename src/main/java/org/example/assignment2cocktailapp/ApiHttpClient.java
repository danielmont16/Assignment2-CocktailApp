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

    public String fetchData(String fLetter) throws IOException {

        String url= "https://www.thecocktaildb.com/api/json/v1/1/search.php?f="+fLetter;

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

    public List<Cocktail> parseJsonCocktails(String responseBody) {
        List<Cocktail> cocktailList = new ArrayList<>();

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
                if (drinkObject.has("strDrink")) {
                    String idDrink = drinkObject.get("idDrink").getAsString();
                    String strDrink = drinkObject.get("strDrink").getAsString();
                    String strCategory = drinkObject.get("strCategory").getAsString();

                    cocktail.setIdDrink(idDrink);
                    cocktail.setStrDrink(strDrink);
                    cocktail.setStrCategory(strCategory);
                }

                // Add the Cocktail object to the list
                cocktailList.add(cocktail);
            }
        }

        return cocktailList;
    }



    public String fetchDataDetails(String idDrink) throws IOException {

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

    public List<Cocktail> parseJsonCocktailDetails(String responseBody) {
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

                    cocktail.setIdDrink(idDrink);
                    cocktail.setStrDrink(strDrink);
                    cocktail.setStrInstructions(strInstructions);
                    cocktail.setStrGlass(strGlass);
                    cocktail.setStrDrinkThumb(strDrinkThumb);
//
                }

                // Fetch ingredients and measures
                List<String> ingredients = new ArrayList<>();
                List<String> measures = new ArrayList<>();

                for (int i = 1; i <= 15; i++) {
                    String ingredient = getStringValue(drinkObject, "strIngredient" + i);
                    String measure = getStringValue(drinkObject, "strMeasure" + i);
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

        return cocktailListDetail;
    }

    private String getStringValue(JsonObject jsonObject, String key) {
        if (jsonObject.has(key)) {
            JsonElement element = jsonObject.get(key);
            if (!element.isJsonNull()) {
                return element.getAsString();
            }
        }
        return null;
    }

}
