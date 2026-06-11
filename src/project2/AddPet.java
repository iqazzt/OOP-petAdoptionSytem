/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class AddPet {

    private Stage stage;
    private PetOwner currentOwner;

    public AddPet(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        stage = new Stage();

        BorderPane layout = new BorderPane();
        VBox content = new VBox(20);

        //---navigation bar 
        HBox navbar = new HBox();
        navbar.setPadding(new Insets(25, 40, 25, 40));
        navbar.setAlignment(Pos.CENTER_LEFT);

        Label webname = new Label("FurEver Friends");
        webname.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        HBox left = new HBox(webname);
        left.setAlignment(Pos.CENTER_LEFT);

        Label homeLbl      = new Label("Home");
        Label myPetLbl     = new Label("My Pet");
        Label newAppLbl    = new Label("New Application");
        Label appHistLbl   = new Label("Application History");
        Label manageAppLbl = new Label("Manage Application");

        String labelstyle = "-fx-text-fill: #000000;" +
                            "-fx-underline: false;"   +
                            "-fx-font-size: 14px;"    +
                            "-fx-cursor: hand;";

        homeLbl.setStyle(labelstyle);
        myPetLbl.setStyle(labelstyle);
        newAppLbl.setStyle(labelstyle);
        appHistLbl.setStyle(labelstyle);
        manageAppLbl.setStyle(labelstyle);

        HBox right = new HBox(25, homeLbl, myPetLbl, newAppLbl, appHistLbl, manageAppLbl);
        right.setAlignment(Pos.CENTER_RIGHT);

        navbar.getChildren().addAll(left, right);
        HBox.setHgrow(right, Priority.ALWAYS);
        layout.setTop(navbar);

        //---navigation events
        homeLbl.setOnMouseClicked(e -> { new HomePage(currentOwner).show(); stage.close(); });
        myPetLbl.setOnMouseClicked(e -> { new MyPets(currentOwner).show(); stage.close(); });
        newAppLbl.setOnMouseClicked(e -> { new NewApplication(currentOwner).show(); stage.close(); });
        appHistLbl.setOnMouseClicked(e -> { new ApplicationHistory(currentOwner).show(); stage.close(); });
        manageAppLbl.setOnMouseClicked(e -> { new ManageApplication(currentOwner).show(); stage.close(); });

        //---page title 
        Text title = new Text("Add Pet Application");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));

        HBox topRow = new HBox(30, title);
        topRow.setPadding(new Insets(30));
        navbar.setSpacing(20);
        topRow.setAlignment(Pos.CENTER);

        HBox row = new HBox(50);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(30));

        //---form fields 
        VBox petInfo = new VBox(15);

        // Pet Name
        HBox row2 = new HBox(10);
        Label petName = new Label("Pet Name: ");
        petName.setMinWidth(120);
        TextField petNametf = new TextField();
        petNametf.setPromptText("e.g., Mikey");
        row2.getChildren().addAll(petName, petNametf);

        // Type 
        HBox row3 = new HBox(10);
        Label typeLbl = new Label("Type: ");
        typeLbl.setMinWidth(120);
        ComboBox<String> typeCb = new ComboBox<>();
        typeCb.getItems().addAll("Cat", "Dog", "Rabbit", "Hamster", "Bird");
        typeCb.setPromptText("Select type");
        row3.getChildren().addAll(typeLbl, typeCb);

        // Breed
        HBox row4 = new HBox(10);
        Label breed = new Label("Breed: ");
        breed.setMinWidth(120);
        TextField breedTf = new TextField();
        breedTf.setPromptText("e.g., Persian");
        row4.getChildren().addAll(breed, breedTf);

        // Age
        HBox row5 = new HBox(10);
        Label age = new Label("Age: ");
        age.setMinWidth(120);
        TextField ageTf = new TextField();
        ageTf.setPromptText("e.g., 2");
        row5.getChildren().addAll(age, ageTf);

        // Gender
        HBox row6 = new HBox(10);
        Label gender = new Label("Gender: ");
        gender.setMinWidth(120);
        ComboBox<String> genderCb = new ComboBox<>();
        genderCb.getItems().addAll("male", "female");
        genderCb.setPromptText("Select gender");
        row6.getChildren().addAll(gender, genderCb);

        // Health Status
        HBox row7 = new HBox(10);
        Label healthStatus = new Label("Health Status: ");
        healthStatus.setMinWidth(120);
        TextField healthStatusTf = new TextField();
        healthStatusTf.setPromptText("e.g., Vaccinated");
        row7.getChildren().addAll(healthStatus, healthStatusTf);

        // Image Upload
        HBox row8 = new HBox(10);
        row8.setAlignment(Pos.CENTER_LEFT);
        Label imageLbl = new Label("Pet Image: ");
        imageLbl.setMinWidth(120);
        Label imagePathLbl = new Label("No image selected");
        imagePathLbl.setStyle("-fx-text-fill: gray; -fx-font-size: 11px;");
        Button chooseImageBtn = new Button("Upload Image");
        chooseImageBtn.setStyle(
            "-fx-background-color: #e0e0e0;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 4;"
        );

        //---stores the selected image path
        final String[] selectedImagePath = {""};

        chooseImageBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Pet Image");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                selectedImagePath[0] = selectedFile.getAbsolutePath();
                imagePathLbl.setText(selectedFile.getName());
                imagePathLbl.setStyle("-fx-text-fill: green; -fx-font-size: 11px;");
            }
        });

        row8.getChildren().addAll(imageLbl, chooseImageBtn, imagePathLbl);

        //---message + Submit button 
        Label messageLbl = new Label();
        messageLbl.setVisible(false);

        Button submitBtn = new Button("Submit");

        submitBtn.setOnAction(e -> {
            //---validation (image is optional)
            if (petNametf.getText().isBlank() || typeCb.getValue() == null ||
                breedTf.getText().isBlank()   || ageTf.getText().isBlank()  ||
                genderCb.getValue() == null   || healthStatusTf.getText().isBlank()) {

                messageLbl.setText("Please fill in all fields.");
                messageLbl.setStyle("-fx-text-fill: red;");
                messageLbl.setVisible(true);
                return;
            }

            try {
                //---auto-generate Pet ID
                String autoId = FileHandler.generatePetId();

                Pet pet = new Pet(
                    autoId,
                    petNametf.getText(),
                    typeCb.getValue(),          
                    breedTf.getText(),
                    Integer.parseInt(ageTf.getText()),
                    genderCb.getValue(),
                    healthStatusTf.getText(),
                    "available",                
                    currentOwner.getOwnerID(),
                    selectedImagePath[0]        
                );

                FileHandler.savePet(pet);

                messageLbl.setText("Pet successfully added! ID: " + autoId);
                messageLbl.setStyle("-fx-text-fill: green;");
                messageLbl.setVisible(true);

                //---clear form
                petNametf.clear();
                typeCb.setValue(null);
                breedTf.clear();
                ageTf.clear();
                genderCb.setValue(null);
                healthStatusTf.clear();
                selectedImagePath[0] = "";
                imagePathLbl.setText("No image selected");
                imagePathLbl.setStyle("-fx-text-fill: gray; -fx-font-size: 11px;");

            } catch (NumberFormatException ex) {
                messageLbl.setText("Age must be a number.");
                messageLbl.setStyle("-fx-text-fill: red;");
                messageLbl.setVisible(true);
            }
        });

        HBox row11 = new HBox(10);
        row11.getChildren().add(submitBtn);

        petInfo.getChildren().addAll(row2, row3, row4, row5, row6, row7, row8,
                                     submitBtn, messageLbl);

        row.getChildren().add(petInfo);
        content.getChildren().add(row);

        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.getChildren().addAll(topRow, content);
        layout.setCenter(card);

        //---styles
        layout.setStyle("-fx-background-color: white;");

        petInfo.setStyle(
            "-fx-background-color: #f9f9f9;" +
            "-fx-border-color: #e0e0e0;"     +
            "-fx-border-radius: 8;"          +
            "-fx-background-radius: 8;"      +
            "-fx-padding: 20;"
        );

        submitBtn.setStyle(
            "-fx-background-color: #2c3e50;" +
            "-fx-text-fill: white;"          +
            "-fx-font-weight: bold;"         +
            "-fx-padding: 8 24;"             +
            "-fx-background-radius: 6;"      +
            "-fx-cursor: hand;"
        );

        Scene scene = new Scene(layout, 1000, 620);
        stage.setScene(scene);
        stage.setTitle("AddPet");
    }

    public void show() {
        stage.show();
    }
}
