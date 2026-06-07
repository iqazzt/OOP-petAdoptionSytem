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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MyPets {
    
    //---fields
    private Stage stage;
    private VBox content;
    private PetOwner currentOwner;
    private ArrayList<Pet> petList;

    //---constructor
    public MyPets(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        stage = new Stage();
        
        BorderPane layout = new BorderPane();
        
        //---navigation bar
        HBox navbar = new HBox();
        navbar.setPadding(new Insets(25, 40, 25, 40));
        
        //left side
        Label webname = new Label("FurEver Friends");
        webname.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        HBox left = new HBox(webname);
        left.setAlignment(Pos.CENTER_LEFT);
        
        //right side (menu)
        Label homeLbl = new Label("Home");
        Label myPetLbl = new Label("My Pet");
        Label newAppLbl = new Label("New Application");
        Label appHistLbl = new Label("Application History");
        Label manageAppLbl = new Label("Manage Application");
        
        String labelstyle = "-fx-text-fill: #000000;" + "-fx-underline: false;" + 
                           "-fx-font-size: 14px;" + "-fx-cursor: hand;";
        
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
        homeLbl.setOnMouseClicked(e -> {
            new HomePage(currentOwner).show();
            stage.close();
        });

        newAppLbl.setOnMouseClicked(e -> {
            new NewApplication(currentOwner).show();
            stage.close();
        });

        appHistLbl.setOnMouseClicked(e -> {
            new ApplicationHistory(currentOwner).show();
            stage.close();
        });

        manageAppLbl.setOnMouseClicked(e -> {
            new ManageApplication(currentOwner).show();
            stage.close();
        });
        
        //---content area
        content = new VBox(20);
        content.setFillWidth(false);

        Text title = new Text("My Pets");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        
        HBox topRow = new HBox(title);
        topRow.setPadding(new Insets(30));
        topRow.setAlignment(Pos.CENTER_LEFT);
        
        //---load pets
        petList = FileHandler.loadPetsByOwner(currentOwner.getOwnerID());
        displayPets();
        
        VBox card = new VBox(10, topRow, content);
        card.setPadding(new Insets(10));
        
        layout.setCenter(card);
        
        //---scene
        layout.setStyle("-fx-background-color: white;");
                
        Scene scene = new Scene(layout, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("My Pets");
    }

    //---private methods
    //---display petlist as rows
    private void displayPets() {
        content.getChildren().clear();

        if (petList.isEmpty()) {
            content.getChildren().add(new Label("No pets listed yet."));
            return;
        }
        
        for (Pet pet : petList) {
            HBox row = new HBox(30);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(20));
            row.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: lightgray;"
            );

            HBox petInfo = new HBox(20);
            
            //each column
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

            //---buttons
            Button editBtn   = new Button("Edit");
            Button deleteBtn = new Button("Delete");
            
            editBtn.setStyle(
                "-fx-background-color: #2c3e50;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 8 24;" +
                "-fx-background-radius: 6;" +
                "-fx-cursor: hand;"
            );
            
            deleteBtn.setStyle(
                "-fx-background-color: #e57373;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 8 24;" +
                "-fx-background-radius: 6;" +
                "-fx-cursor: hand;"
            );
            
            //---edit action
            editBtn.setOnAction(e -> showEditDialog(pet));
            
            //---delete action (remove from list then rewrite file)
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
            
            VBox btnBox = new VBox(10, editBtn, deleteBtn);
            row.getChildren().addAll(petInfo, btnBox);
            content.getChildren().add(row);
        }
    }

    //---edit dialog
    private void showEditDialog(Pet pet) {
        //create a new popup window
        Stage dialog = new Stage();
        dialog.setTitle("EditPet");
        dialog.initModality(Modality.APPLICATION_MODAL);

        //---form
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(10);
        form.setVgap(20);
        form.setAlignment(Pos.CENTER);
        
        Text title = new Text("Edit Pet");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label idLbl = new Label("Pet ID: " + pet.getPetID() + " (cannot change)");
        
        //---prefill fields with current pet data
        TextField petNameTf = new TextField(pet.getName());
        TextField speciesTf = new TextField(pet.getSpecies());
        TextField breedTf = new TextField(pet.getBreed());
        TextField ageTf = new TextField(String.valueOf(pet.getAge()));
        TextField healthStatusTf = new TextField(pet.getHealthStatus());
        
        petNameTf.setMaxWidth(200);
        speciesTf.setMaxWidth(200);
        breedTf.setMaxWidth(200);
        ageTf.setMaxWidth(200);
        healthStatusTf.setMaxWidth(200);

        ComboBox<String> genderCb = new ComboBox<>();
        genderCb.getItems().addAll("male", "female");
        genderCb.setValue(pet.getGender());  //set current value

        ComboBox<String> adoptCb = new ComboBox<>();
        adoptCb.getItems().addAll("available", "adopted");
        adoptCb.setValue(pet.getAdoptionStatus());

        //---buttons
        Button saveBtn   = new Button("Save");
        Button cancelBtn = new Button("Cancel");
        
        saveBtn.setStyle(
            "-fx-background-color: #2c3e50;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 8 24;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );
        
        cancelBtn.setStyle(
            "-fx-background-color: #e57373;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 8 24;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        HBox btnRow = new HBox(10, saveBtn, cancelBtn);

        //---form layout
        form.add(idLbl, 0, 0, 2, 1);

        form.add(new Label("Name:"), 0, 1);
        form.add(petNameTf, 1, 1);

        form.add(new Label("Species:"), 0, 2);
        form.add(speciesTf, 1, 2);

        form.add(new Label("Breed:"), 0, 3);
        form.add(breedTf, 1, 3);

        form.add(new Label("Age:"), 0, 4);
        form.add(ageTf, 1, 4);

        form.add(new Label("Gender:"), 0, 5);
        form.add(genderCb, 1, 5);

        form.add(new Label("Health Status:"), 0, 6);
        form.add(healthStatusTf, 1, 6);

        form.add(new Label("Adoption Status:"), 0, 7);
        form.add(adoptCb, 1, 7);

        form.add(btnRow, 0, 8, 2, 1);

        //---button actions
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
        
        root.getChildren().addAll(title, form);
        root.setStyle("-fx-background-color: white;");

        dialog.setScene(new Scene(root, 1000, 600));
        dialog.showAndWait();
    }

    public void show() {
        stage.show();
    }
}