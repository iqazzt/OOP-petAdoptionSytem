/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MyPets {
    private Stage stage;
    private VBox content;
    private PetOwner currentOwner;
    private ArrayList<Pet> petList;

    public MyPets(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        stage = new Stage();
        
        BorderPane layout = new BorderPane();
        content = new VBox(20);
        
         //---Navigation bar
        HBox navbar = new HBox();
        navbar.setSpacing(20);
        navbar.setPadding(new Insets(10));
        navbar.setAlignment(Pos.TOP_RIGHT);

        Label homeLbl = new Label("Home");
        Label myPetLbl = new Label("My Pet");
        Label newAppLbl = new Label("New Application");
        Label appHistLbl = new Label("Application History");
        Label manageAppLbl = new Label("Manage Application");

        navbar.getChildren().addAll(homeLbl, myPetLbl, newAppLbl, appHistLbl, manageAppLbl);
        layout.setTop(navbar);

        Text title = new Text("My Pets");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        
        HBox topRow = new HBox(30, title);
        topRow.setPadding(new Insets(30));
        navbar.setSpacing(20);
        topRow.setAlignment(Pos.CENTER);
        
        //load pets
        petList = FileHandler.loadPetsByOwner(currentOwner.getOwnerID());
        
        displayPets();
        
        VBox card = new VBox();
        card.setPadding(new Insets(10));
        card.getChildren().addAll(topRow, content);
        layout.setCenter(card);
        
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

        Scene scene = new Scene(layout, 700, 500);
        stage.setScene(scene);
        stage.setTitle("My Pets");
    }

    // ── Display petlist as rows ────────────────────────
    private void displayPets() {
        content.getChildren().clear();

        if (petList.isEmpty()) {
            content.getChildren().add(new Label("No pets listed yet."));
            return;
        }

        for (Pet pet : petList) {
            HBox row = new HBox(50);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(30));

            HBox petInfo = new HBox(25);

            // Each column
            VBox petIDBox = new VBox(5);
            petIDBox.getChildren().addAll(
                new Label("Pet ID"),
                new Label(pet.getPetID())
            );

            VBox nameBox = new VBox(5);
            nameBox.getChildren().addAll(
                new Label("Name"),
                new Label(pet.getName())
            );

            VBox speciesBox = new VBox(5);
            speciesBox.getChildren().addAll(
                new Label("Species"),
                new Label(pet.getSpecies())
            );

            VBox breedBox = new VBox(5);
            breedBox.getChildren().addAll(
                new Label("Breed"),
                new Label(pet.getBreed())
            );

            VBox ageBox = new VBox(5);
            ageBox.getChildren().addAll(
                new Label("Age"),
                new Label(String.valueOf(pet.getAge()))
            );

            VBox genderBox = new VBox(5);
            genderBox.getChildren().addAll(
                new Label("Gender"),
                new Label(pet.getGender())
            );
            
            VBox healthBox = new VBox(5);
            healthBox.getChildren().addAll(
                new Label("Health Status"),
                new Label(pet.getHealthStatus())
            );

            VBox statusBox = new VBox(5);
            statusBox.getChildren().addAll(
                new Label("Status"),
                new Label(pet.getAdoptionStatus())
            );

            petInfo.getChildren().addAll(
                petIDBox,
                nameBox,
                speciesBox,
                breedBox,
                ageBox,
                genderBox,
                healthBox,
                statusBox
            );

            //---Button
            Button editBtn   = new Button("Edit");
            Button deleteBtn = new Button("Delete");

            //Delete — remove from list then rewrite file //ai prompt
            deleteBtn.setOnAction(e -> {
                //show confirmation alert
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Delete Pet");
                confirm.setHeaderText(null);
                confirm.setContentText("Delete " + pet.getPetID() + "?");
                
                //wait for user response
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        petList.remove(pet); //remove from list
                        FileHandler.saveAllPets(petList); 
                        displayPets();
                    }
                });
            });

            // Edit
            editBtn.setOnAction(e -> showEditDialog(pet));

            VBox btnBox = new VBox(10, editBtn, deleteBtn);
            row.getChildren().addAll(petInfo, btnBox);
            content.getChildren().add(row);
        }
    }

    // ── Edit dialog fahamkan
    private void showEditDialog(Pet pet) {
        //create a new popup window
        Stage dialog = new Stage();
        dialog.setTitle("EditPet");
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox box = new VBox(10);
        box.setPadding(new Insets(20));

        Label idLbl = new Label("Pet ID: " + pet.getPetID() + " (cannot change)");
        
        //prefill fields with current pet data
        TextField petNameTf = new TextField(pet.getName());
        TextField speciesTf = new TextField(pet.getSpecies());
        TextField breedTf = new TextField(pet.getBreed());
        TextField ageTf = new TextField(String.valueOf(pet.getAge()));
        TextField healthStatusTf = new TextField(pet.getHealthStatus());

        ComboBox<String> genderCb = new ComboBox<>();
        genderCb.getItems().addAll("male", "female");
        genderCb.setValue(pet.getGender());  //set current value

        ComboBox<String> adoptCb = new ComboBox<>();
        adoptCb.getItems().addAll("available", "adopted");
        adoptCb.setValue(pet.getAdoptionStatus());

        Button saveBtn   = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        HBox btnRow = new HBox(10, saveBtn, cancelBtn);

        box.getChildren().addAll(
            new Label("Edit Pet"), idLbl,
            new Label("Name:"), petNameTf,
            new Label("Species:"), speciesTf,
            new Label("Breed:"), breedTf,
            new Label("Age:"), ageTf,
            new Label("Gender:"), genderCb,
            new Label("Health Status:"), healthStatusTf,
            new Label("Adoption Status:"), adoptCb,
            btnRow
        );

        saveBtn.setOnAction(e -> {
            // Update pet object with new values
            pet.setName(petNameTf.getText().trim());
            pet.setSpecies(speciesTf.getText().trim());
            pet.setBreed(breedTf.getText().trim());
            pet.setAge(Integer.parseInt(ageTf.getText().trim()));
            pet.setGender(genderCb.getValue());
            pet.setHealthStatus(healthStatusTf.getText().trim());
            pet.setAdoptionStatus(adoptCb.getValue());

            FileHandler.saveAllPets(petList);
            displayPets();
            dialog.close(); //close dialog
        });

        cancelBtn.setOnAction(e -> dialog.close());

        dialog.setScene(new Scene(box, 300, 420));
        dialog.showAndWait();
    }

    public void show() {
        stage.show();
    }
}