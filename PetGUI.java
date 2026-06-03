
// PetGUI.java

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PetGUI extends Application {

    private Pet pet;

    @Override
    public void start(Stage primaryStage) {

        // Labels
        Label lblPetID = new Label("Pet ID:");
        Label lblName = new Label("Pet Name:");
        Label lblSpecies = new Label("Species:");
        Label lblBreed = new Label("Breed:");
        Label lblAge = new Label("Age:");
        Label lblGender = new Label("Gender:");
        Label lblHealth = new Label("Health Status:");

        // TextFields
        TextField tfPetID = new TextField();
        TextField tfName = new TextField();
        TextField tfSpecies = new TextField();
        TextField tfBreed = new TextField();
        TextField tfAge = new TextField();
        TextField tfHealth = new TextField();

        // ComboBox
        ComboBox<String> cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female");

        // Buttons
        Button btnSave = new Button("Save Pet");
        Button btnAdopt = new Button("Adopt Pet");

        // TextArea
        TextArea taOutput = new TextArea();
        taOutput.setPrefHeight(200);

        // GridPane Layout
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

        // Button Layout
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(btnSave, btnAdopt);

        // Main Layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(grid, buttonBox, taOutput);

        // Save Button Event
        btnSave.setOnAction(e -> {

            pet = new Pet(
                    tfPetID.getText(),
                    tfName.getText(),
                    tfSpecies.getText(),
                    tfBreed.getText(),
                    Integer.parseInt(tfAge.getText()),
                    cbGender.getValue(),
                    tfHealth.getText(),
                    false
            );

            taOutput.setText(pet.displayPetInfo());
        });

        // Adopt Button Event
        btnAdopt.setOnAction(e -> {

            if (pet != null) {

                pet.setAdoptionStatus(true);

                taOutput.setText(
                        pet.displayPetInfo());
            }
        });

        // Scene
        Scene scene = new Scene(root, 500, 550);

        primaryStage.setTitle("Pet Adoption System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}