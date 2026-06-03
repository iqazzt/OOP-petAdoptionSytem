/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PetProfile extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label lblTitle = new Label("PET PROFILE");
        lblTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        Label lblPetID = new Label("Pet ID:");
        Label lblName = new Label("Pet Name:");
        Label lblSpecies = new Label("Species:");
        Label lblBreed = new Label("Breed:");
        Label lblAge = new Label("Age:");
        Label lblGender = new Label("Gender:");
        Label lblHealth = new Label("Health Status:");

        TextField tfPetID = new TextField();
        TextField tfName = new TextField();
        TextField tfSpecies = new TextField();
        TextField tfBreed = new TextField();
        TextField tfAge = new TextField();
        TextField tfHealth = new TextField();

        ComboBox<String> cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female");

        Button btnSave = new Button("Save");
        Button btnClear = new Button("Clear");

        TextArea taOutput = new TextArea();
        taOutput.setEditable(false);
        taOutput.setPrefHeight(150);

        btnSave.setOnAction(e -> {

            String info =
                    "Pet ID: " + tfPetID.getText() +
                    "\nPet Name: " + tfName.getText() +
                    "\nSpecies: " + tfSpecies.getText() +
                    "\nBreed: " + tfBreed.getText() +
                    "\nAge: " + tfAge.getText() +
                    "\nGender: " + cbGender.getValue() +
                    "\nHealth Status: " + tfHealth.getText();

            taOutput.setText(info);
        });

        btnClear.setOnAction(e -> {

            tfPetID.clear();
            tfName.clear();
            tfSpecies.clear();
            tfBreed.clear();
            tfAge.clear();
            tfHealth.clear();

            cbGender.setValue(null);

            taOutput.clear();
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(lblPetID, 0, 0);
        grid.add(tfPetID, 1, 0);

        grid.add(lblName, 0, 1);
        grid.add(tfName, 1, 1);

        grid.add(lblSpecies, 0, 2);
        grid.add(tfSpecies, 1, 2);

        grid.add(lblBreed, 0, 3);
        grid.add(tfBreed, 1, 3);

        grid.add(lblAge, 0, 4);
        grid.add(tfAge, 1, 4);

        grid.add(lblGender, 0, 5);
        grid.add(cbGender, 1, 5);

        grid.add(lblHealth, 0, 6);
        grid.add(tfHealth, 1, 6);

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(btnSave, btnClear);

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(
                lblTitle,
                grid,
                buttons,
                taOutput
        );

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Pet Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
