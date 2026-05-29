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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddPet{
    private Stage stage;
    private PetOwner currentOwner;
    
    public AddPet(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        stage = new Stage();
        
        BorderPane layout = new BorderPane();
        VBox content = new VBox(20);
        
         //---Navigation bar
        HBox navbar = new HBox();
        navbar.setSpacing(20);
        navbar.setPadding(new Insets(10));
        navbar.setAlignment(Pos.CENTER_RIGHT);
        navbar.setMinHeight(40);

        Label homeLbl = new Label("Home");
        Label myPetLbl = new Label("My Pet");
        Label newAppLbl = new Label("New Application");
        Label appHistLbl = new Label("Application History");
        Label manageAppLbl = new Label("Manage Application");

        navbar.getChildren().addAll(homeLbl, myPetLbl, newAppLbl, appHistLbl, manageAppLbl);
        layout.setTop(navbar);
      
        Text title = new Text("Add Pet Application");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        
        HBox topRow = new HBox(30, title);
        topRow.setPadding(new Insets(30));
        navbar.setSpacing(20);
        topRow.setAlignment(Pos.CENTER);
        
        HBox row = new HBox(50);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(30));
        
        
        //---Form
        VBox petInfo = new VBox(15);
        
        //each row
        HBox row1 = new HBox(10);
        Label petID = new Label("Pet ID: ");
        TextField petTf = new TextField();
        petTf.setPromptText("e.g., P010");
        row1.getChildren().addAll(petID, petTf);
        petID.setMinWidth(120);
        
        HBox row2 = new HBox(10);
        Label petName = new Label("Pet Name: ");
        TextField petNametf = new TextField();
        petNametf.setPromptText("e.g., Mikey");
        row2.getChildren().addAll(petName, petNametf);
        petName.setMinWidth(120);
     
        HBox row3 = new HBox(10);
        Label species = new Label("Species: ");
        TextField speciesTf = new TextField();
        speciesTf.setPromptText("e.g., Cat");
        row3.getChildren().addAll(species, speciesTf);
        species.setMinWidth(120);
        
        HBox row4 = new HBox(10);
        Label breed = new Label("Breed: ");
        TextField breedTf = new TextField();
        breedTf.setPromptText("e.g., Persian");
        row4.getChildren().addAll(breed, breedTf);
        breed.setMinWidth(120);
        
        HBox row5 = new HBox(10);
        Label age = new Label("Age: ");
        TextField ageTf = new TextField();
        ageTf.setPromptText("e.g., 2");
        row5.getChildren().addAll(age, ageTf);
        age.setMinWidth(120);
        
        HBox row6 = new HBox(10);
        Label gender = new Label("Gender: ");
        ComboBox<String> genderCb = new ComboBox<>();
        genderCb.getItems().addAll("male", "female");
        genderCb.setPromptText("select gender"); //show ai prompt
        row6.getChildren().addAll(gender, genderCb);
        gender.setMinWidth(120);
        
        HBox row7 = new HBox(10);
        Label healthStatus = new Label("Health Status: ");
        TextField healthStatusTf = new TextField();
        healthStatusTf.setPromptText("e.g., Vaccinated");
        row7.getChildren().addAll(healthStatus, healthStatusTf);
        healthStatus.setMinWidth(120);
        
        HBox row8 = new HBox(10);
        Label adoptStatus = new Label("Adoption Status: ");
        ComboBox<String> adoptCb = new ComboBox<>();
        adoptCb.getItems().addAll("available", "adopted");
        adoptCb.setPromptText("select status");
        row8.getChildren().addAll(adoptStatus, adoptCb);
        adoptStatus.setMinWidth(120);
        
     
        //---Submit Button
        Button submitBtn = new Button("Submit");
        HBox row11 = new HBox(10);
        row11.getChildren().add(submitBtn);
        
        Label messageLbl = new Label();
        messageLbl.setVisible(false);
        
        submitBtn.setOnAction(e -> {
            
            // Validate — make sure nothing is empty
            if (petTf.getText().isBlank() || petNametf.getText().isBlank() || speciesTf.getText().isBlank() || breedTf.getText().isBlank() || ageTf.getText().isBlank() ||
                    healthStatusTf.getText().isBlank() || genderCb.getValue() == null || adoptCb.getValue() == null ) {
                
                messageLbl.setText("Please fill in all fields.");
                messageLbl.setVisible(true);
                return;
            } //ai prompt
    
            try{
                Pet pet = new Pet(
                    petTf.getText(),
                    petNametf.getText(),
                    speciesTf.getText(),
                    breedTf.getText(),
                    Integer.parseInt(ageTf.getText()),
                    genderCb.getValue(),
                    healthStatusTf.getText(),
                    adoptCb.getValue(),
                    currentOwner.getOwnerID()
                );

                FileHandler.savePet(pet);

                messageLbl.setText("Pet successfully added!");
                messageLbl.setVisible(true);
                        
            }catch(NumberFormatException ex){
                messageLbl.setText("Age must be a number."); 
                messageLbl.setVisible(true);
            }
    
        });
        
        petInfo.getChildren().addAll(row1, row2, row3, row4, row5,
                                  row6, row7, row8, submitBtn, messageLbl);
        
        row.getChildren().add(petInfo);
        content.getChildren().add(row);
                
        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.getChildren().addAll(topRow, content);
        layout.setCenter(card);
        
        //---Back label
        Label back = new Label("← Back to Home");
        back.setPadding(new Insets(20));
        layout.setBottom(back);
        back.setOnMouseClicked(e -> {
            new HomePage(currentOwner).show();
            stage.close();
        });
        
        homeLbl.setOnMouseClicked(e -> {
            new HomePage(currentOwner).show();
            stage.close();
        });
        
        myPetLbl.setOnMouseClicked(e -> {
            new MyPets(currentOwner).show();
            stage.close();
        });
        
        Scene scene = new Scene(layout, 700, 600);
        stage.setScene(scene);
        stage.setTitle("AddPet");
    }
    
    public void show(){
        stage.show();
    }
 
}