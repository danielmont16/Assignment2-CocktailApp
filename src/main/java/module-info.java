module org.example.assignment2cocktailapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens org.example.assignment2cocktailapp to javafx.fxml;
    exports org.example.assignment2cocktailapp;
}