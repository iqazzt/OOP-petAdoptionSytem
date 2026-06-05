package project2;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;

public class NewApplication {

    private Stage stage;
    private PetOwner currentOwner;

    // Constructor for integration — opened from navbar
    public NewApplication(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        this.stage = new Stage();
    }
    
    // Build and show the New Application screen
    public void show() {
        Stage primaryStage = this.stage;

        Label webname = new Label("FurEver Friends");
        webname.setStyle("-fx-font-weight: bold;");
        
        //--- navigation bar as demo ---
        Button homeBtn = new Button("Home");
        Button myPetBtn = new Button("My Pet");
        Button newAppBtn = new Button("New Application");
        Button historyBtn = new Button("Application History");
        Button manageBtn = new Button("Manage Application");
        Button profileBtn = new Button("Profile");
        
        //--- highlight current page : New Application ---
        newAppBtn.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        
        Region navSpacer = new Region();
        HBox.setHgrow(navSpacer, Priority.ALWAYS);

        HBox navBar = new HBox(15, webname, navSpacer, homeBtn, myPetBtn, newAppBtn, historyBtn, manageBtn, profileBtn);
        navBar.setPadding(new Insets(15));
        navBar.setAlignment(Pos.CENTER_LEFT);
        
        //--- Grid pane for application form ---
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
        TextField applicantIdField = new TextField();
        applicantIdField.setPromptText("username");
        
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
        submitBtn.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        
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

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("New Application");
        stage.setScene(scene);
        stage.show();
        
        //--- process after user submit ---
        submitBtn.setOnAction(e -> {
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
        
        // ── Navbar actions ──
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
