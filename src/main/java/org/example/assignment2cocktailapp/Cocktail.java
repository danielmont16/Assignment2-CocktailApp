package org.example.assignment2cocktailapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//This is the cocktail class
public class Cocktail {

    //the following are the properties define for this class
    private StringProperty idDrink = null;
    private StringProperty strDrink = null;
    private StringProperty strCategory = null;
    private StringProperty strInstructions =null;
    private StringProperty strGlass = null;
    private StringProperty strDrinkThumb = null;
    private ObservableList<String> ingredients = null;
    private ObservableList<String> measures = null;

    //this is the constructor
    public Cocktail() {
        this.idDrink = new SimpleStringProperty();
        this.strDrink = new SimpleStringProperty();
        this.strCategory = new SimpleStringProperty();
        this.strInstructions = new SimpleStringProperty();
        this.strGlass = new SimpleStringProperty();
        this.strDrinkThumb = new SimpleStringProperty();
        this.ingredients = FXCollections.observableArrayList();
        this.measures = FXCollections.observableArrayList();

    }

    //Getters
    public String getIdDrink() {
        return idDrink.get();
    }

    public StringProperty idDrinkProperty() {
        return idDrink;
    }

    public String getStrCategory() {
        return strCategory.get();
    }

    public StringProperty strCategoryProperty() {
        return strCategory;
    }

    public String getStrDrink() {

        return strDrink.get();
    }

    public StringProperty strDrinkProperty() {

        return strDrink;
    }

    public String getStrInstructions() {
        return strInstructions.get();
    }

    public StringProperty strInstructionsProperty() {
        return strInstructions;
    }

    public StringProperty strGlassProperty() {
        return strGlass;
    }

    public String getStrGlass() {
        return strGlass.get();
    }

    public String getStrDrinkThumb() {
        return strDrinkThumb.get();
    }

    public StringProperty strDrinkThumbProperty() {
        return strDrinkThumb;
    }

    public ObservableList<String> getIngredients() {
        return ingredients;
    }

    public ObservableList<String> getMeasures() {
        return measures;
    }


    // Setters
    public void setIdDrink(String idDrink) {
        this.idDrink.set(idDrink);
    }

    public void setStrCategory(String strCategory) {
        this.strCategory.set(strCategory);
    }

    public void setStrDrink(String strDrink) {
        this.strDrink.set(strDrink);
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions.set(strInstructions);
    }

    public void setStrGlass(String strGlass) {
        this.strGlass.set(strGlass);
    }

    public void setStrDrinkThumb(String strDrinkThumb) {
        this.strDrinkThumb.set(strDrinkThumb);
    }

    public void setIngredients(ObservableList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasures(ObservableList<String> measures) {
        this.measures = measures;
    }

}
