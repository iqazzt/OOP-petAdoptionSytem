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
        
        HBox navBar = new HBox(25);
        navBar.setPadding(new Insets(25, 40, 25, 40));
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setStyle("-fx-background-color: #FFFFFF;");

        Label webname = new Label("FurEver Friends");
        webname.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        //--- navigation bar ---
        Region navSpacer = new Region();
        HBox.setHgrow(navSpacer, Priority.ALWAYS);

        Hyperlink homeBtn = new Hyperlink("Home");
        Hyperlink myPetBtn = new Hyperlink("My Pet");
        Hyperlink newAppBtn = new Hyperlink("New Application");
        Hyperlink historyBtn = new Hyperlink("Application History");
        Hyperlink manageBtn = new Hyperlink("Manage Application");

        //--- menu links ---
        String linkStyle = "-fx-text-fill: #000000;" + "-fx-underline: false;" + 
                           "-fx-font-size: 14px;";

        homeBtn.setStyle(linkStyle);
        myPetBtn.setStyle(linkStyle);
        newAppBtn.setStyle(linkStyle);
        historyBtn.setStyle(linkStyle);
        manageBtn.setStyle(linkStyle);
        
        navBar.getChildren().addAll(webname, navSpacer, homeBtn, myPetBtn,newAppBtn,
                                    historyBtn, manageBtn);
        
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
    
        //--- input from user ---
        Label applicantIdLabel = new Label("Applicant ID");
        //--- auto filled with the user's ID ---
        TextField applicantIdField = new TextField(currentOwner.getOwnerID());
        applicantIdField.setEditable(false); //--- locked (no edit) ---
        applicantIdField.setPromptText("user ID");
    
        Label petIdLabel = new Label("Pet ID");
        TextField petIdField = new TextField();
        petIdField.setPromptText("P101");
        
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPromptText("example@gmail.com");
        
        Label describeLabel = new Label("Describe your adoption reason");
        TextArea describeArea = new TextArea();
        describeArea.setPromptText("Share your experience/Convince the owner/Your commitment/...");

        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(500);
        submitBtn.setPrefHeight(35);
        submitBtn.setStyle("-fx-background-color: #FBC473; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 5px;");
        
        //--- wrap in HBox to make the title center ---
        HBox titleCenter = new HBox(title);
        titleCenter.setAlignment(Pos.CENTER);
        
        //--- add to grid ---
        pane.add(titleCenter, 0, 0);

        pane.add(applicantIdLabel, 0, 1);
        pane.add(applicantIdField, 0, 2);

        pane.add(petIdLabel, 0, 3);
        pane.add(petIdField, 0, 4);
        
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

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("New Application");
        stage.setScene(scene);
        stage.show();
        
        //--- process after user submit ---
        submitBtn.setOnAction(e -> {
     
            String petId = petIdField.getText().trim();   
            String email = emailField.getText().trim();
            String desc = describeArea.getText().trim();
            
            //--- empty field check ---
            if (petId.isEmpty() || email.isEmpty() || desc.isEmpty()) {
                Alert errAlert = new Alert(Alert.AlertType.ERROR, "Please fill in all fields.");
                errAlert.showAndWait();
                return;
            }
            
            //-- load all pets ---
            ArrayList<Pet> allPets = FileHandler.loadPets();

            Pet selectedPet = null;

            //--- check if pet exists ---
            for (Pet pet : allPets) {
                if (pet.getPetID().equals(petId)) {
                    selectedPet = pet;
                    break;
                }
            }

            if (selectedPet == null) {
                Alert errAlert = new Alert(Alert.AlertType.ERROR, "Invalid Pet ID");
                errAlert.showAndWait();
                return;
            }

            //--- check if user owns the pet ---
            if (selectedPet.getOwnerID().equals(currentOwner.getOwnerID())) {
                Alert errAlert = new Alert(Alert.AlertType.ERROR, "You cannot apply for your own pet");
                errAlert.showAndWait();
                return;
            }

            //--- check if pet is available ---
            if (!selectedPet.getAdoptionStatus().equalsIgnoreCase("AVAILABLE")) {
                Alert errAlert = new Alert(Alert.AlertType.ERROR, "This pet is not available for adoption");
                errAlert.showAndWait();
                return;
            }
        
            //--- generate Application ID for each application ---  
            String applicationId = FileHandler.generateApplicationId();
            //--- generate submission date for each application ---
            String submissionDate = java.time.LocalDate.now().toString();

            String[] parts = {
                    applicationId,
                    applicantIdField.getText(),
                    petIdField.getText(),
                    emailField.getText(),
                    describeArea.getText(),
                    "PENDING",
                    submissionDate,
                    "No comment"
                };

            FileHandler.saveApplication(parts);

            //--- send alert to applicant when they submit ---
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Application submitted!");
            //--- keep showing the popout ---
            alert.showAndWait();
        
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
