package project2;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.text.FontWeight;

public class NewApplication {

    private Stage stage;
    private PetOwner currentOwner;

    //--- constructor for integration (opened from navbar) ---
    public NewApplication(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        this.stage = new Stage();
    }
    
    //--- build and show the New Application screen ---
    public void show() {
        Stage primaryStage = this.stage;

        //--- navigation bar ---
        HBox navBar = new HBox(25);
        navBar.setPadding(new Insets(25, 40, 25, 40));
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setStyle("-fx-background-color: #FFFFFF;");

        Label webname = new Label("FurEver Friends");
        webname.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Region navSpacer = new Region();
        HBox.setHgrow(navSpacer, Priority.ALWAYS);

        Hyperlink homeBtn    = new Hyperlink("Home");
        Hyperlink myPetBtn   = new Hyperlink("My Pet");
        Hyperlink newAppBtn  = new Hyperlink("New Application");
        Hyperlink historyBtn = new Hyperlink("Application History");
        Hyperlink manageBtn  = new Hyperlink("Manage Application");
        
        //--- menu links ---
        String linkStyle = "-fx-text-fill: #000000; -fx-underline: false; -fx-font-size: 14px;";
        homeBtn.setStyle(linkStyle);
        myPetBtn.setStyle(linkStyle);
        newAppBtn.setStyle(linkStyle);
        historyBtn.setStyle(linkStyle);
        manageBtn.setStyle(linkStyle);

        navBar.getChildren().addAll(webname, navSpacer, homeBtn, myPetBtn,
                                    newAppBtn, historyBtn, manageBtn);

        //--- grid pane for application form ---
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(30));
        pane.setAlignment(Pos.CENTER);
        
        //--- form title ---
        Label title = new Label("Adoption Application");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold;");

        //--- Applicant ID auto filled (user cant edit) ---
        Label applicantIdLabel = new Label("Applicant ID");
        TextField applicantIdField = new TextField(currentOwner.getOwnerID());
        applicantIdField.setEditable(false);
        applicantIdField.setStyle("-fx-background-color: #f0f0f0;");

        //--- Pet selection (dropdown of available pets & not owned by this user) ---
        Label petLabel = new Label("Select Pet");

        ArrayList<Pet> availablePets = new ArrayList<>();
        for (Pet p : FileHandler.loadPets()) {
            if (p.getAdoptionStatus().equalsIgnoreCase("available") && 
               !p.getOwnerID().equals(currentOwner.getOwnerID())) {
                    availablePets.add(p);
            }
        }

        ComboBox<String> petDropdown = new ComboBox<>();
        for (Pet p : availablePets) {
            petDropdown.getItems().add(p.getPetID() + " – " + p.getName() + " (" + p.getType() + ")");
        }
        petDropdown.setPromptText("Choose a pet");
        petDropdown.setPrefWidth(500);

        //--- Email auto filled (applicant dont have to enter again) ---
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField(currentOwner.getOwnerEmail());
        emailField.setEditable(false);
        emailField.setStyle("-fx-background-color: #f0f0f0;");

        Label describeLabel = new Label("Describe your adoption reason");
        TextArea describeArea = new TextArea();
        describeArea.setPromptText("Share your experience / Convince the owner / Your commitment...");
        describeArea.setPrefWidth(500);

        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(500);
        submitBtn.setPrefHeight(35);
        submitBtn.setStyle("-fx-background-color: #FBC473; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 5px;");
        
        //--- wrap in HBox to make the title center ---
        HBox titleCenter = new HBox(title);
        titleCenter.setAlignment(Pos.CENTER);
        
        //--- add to grid ---
        pane.add(titleCenter, 0, 0);

        pane.add(applicantIdLabel, 0, 1);
        pane.add(applicantIdField, 0, 2);
        
        pane.add(petLabel, 0, 3);
        pane.add(petDropdown, 0, 4);
        
        pane.add(emailLabel, 0, 5);
        pane.add(emailField, 0, 6);
        
        pane.add(describeLabel, 0, 7);
        pane.add(describeArea, 0, 8);
        
        pane.add(submitBtn, 0, 9);

        //--- organize the layout : navigation(top), form(center) ---
        BorderPane root = new BorderPane();
        root.setTop(navBar);
        root.setCenter(pane);
        root.setStyle("-fx-background-color: white;");
        pane.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("New Application");
        stage.setScene(scene);
        stage.show();

        //--- process after user submit ---
        submitBtn.setOnAction(e -> {
            String desc = describeArea.getText().trim();
            
            //--- empty field check & make sure pet have been chosen ---
            if (petDropdown.getValue() == null || desc.isEmpty()) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Please select a pet and fill in all fields.");
                err.showAndWait();
                return;
            }


            //--- Extract the selected pet's ID from the dropdown label ---
            //--- P101 - Name (Species) ---

            String selectedLabel = petDropdown.getValue();
            String selectedPetId = selectedLabel.split(" - ")[0].trim();

            //--- generate Application ID for each application ---
            String applicationId  = FileHandler.generateApplicationId();
            //--- generate submission date for each application ---
            String submissionDate = java.time.LocalDate.now().toString();

            String[] parts = {
                applicationId,
                currentOwner.getOwnerID(),
                selectedPetId,
                currentOwner.getOwnerEmail(), // email auto-filled from session
                desc,
                "PENDING",
                submissionDate,
                "No comment"
            };

            FileHandler.saveApplication(parts);

            //--- send alert to applicant when they submit ---
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Application submitted!");
            //--- keep showing the popout ---
            alert.showAndWait();

            describeArea.clear();
            petDropdown.setValue(null);
        });

        //--- navbar actions ---
        homeBtn.setOnAction(ex -> {
            new HomePage(currentOwner).show();
            stage.close();
        });
        
        myPetBtn.setOnAction(ex -> {
            new MyPets(currentOwner).show();
            stage.close();
        });
        
        historyBtn.setOnAction(ex -> {
            new ApplicationHistory(currentOwner).show();
            stage.close();
        });
        
        manageBtn.setOnAction(ex -> {
            new ManageApplication(currentOwner).show();
            stage.close();
        });
    }
}
