package project2;

import java.io.BufferedReader;
import java.io.FileReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ApplicationHistory {

    private Stage stage;
    private PetOwner currentOwner;

    // Constructor for integration — opened from navbar
    public ApplicationHistory(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        this.stage = new Stage();
    }

    //--- ApplicationRecord saves data from each application ---
    public static class ApplicationRecord {

        private String applicationId;
        private String applicantId;
        private String petId;
        private String email;
        private String description;
        private String status;
        private String submissionDate;
        private String comment;
        
        //--- constructor to initialize application data ---
        public ApplicationRecord(String applicationId, String applicantId, String petId,
                                 String email, String description, String status,
                                 String submissionDate, String comment) {

            this.applicationId = applicationId;
            this.applicantId = applicantId;
            this.petId = petId;
            this.email = email;
            this.description = description;
            this.status = status;
            this.submissionDate = submissionDate;
            this.comment = comment;
        }
        
        //--- getter methods to access application data ---
        public String getApplicationId() {
            return applicationId;
        }

        public String getApplicantId() {
            return applicantId;
        }

        public String getPetId() {
            return petId;
        }

        public String getEmail() {
            return email;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        public String getSubmissionDate() {
            return submissionDate;
        }

        public String getComment() {
            return comment;
        }
    }

    // Build and show the Application History screen
    public void show() {
        Stage primaryStage = this.stage;

        Label webname = new Label("FurEver Friends");
        webname.setStyle("-fx-font-weight: bold;");
        
        //--- navigation bar buttons as demo ---
        Button homeBtn = new Button("Home");
        Button myPetBtn = new Button("My Pet");
        Button newAppBtn = new Button("New Application");
        Button historyBtn = new Button("Application History");
        Button manageBtn = new Button("Manage Application");
        Button profileBtn = new Button("Profile");
        
        //--- highlight current page : Application History---
        historyBtn.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        
        Region navSpacer = new Region();
        HBox.setHgrow(navSpacer, Priority.ALWAYS);

        HBox navBar = new HBox(15, webname, navSpacer, homeBtn, myPetBtn, newAppBtn, historyBtn, manageBtn, profileBtn);
        navBar.setPadding(new Insets(15));
        navBar.setAlignment(Pos.CENTER_LEFT);
        
        //--- title ---
        Label title = new Label("Application History");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold;");

        //--- table header ---
        HBox tableHeader = createHeader();
        //--- all remaining rows besides header ---
        VBox tableRows = new VBox(0);

        //--- load data from applications.txt ---
        for (String[] parts : FileHandler.loadApplications()) {
            HBox row = createRow(parts);
            tableRows.getChildren().add(row);
        }
        
        //--- combine table header and rows into one table box ---
        VBox tableBox = new VBox(0, tableHeader, tableRows);
        tableBox.setStyle("-fx-border-color: #cccccc;" + "-fx-border-width: 1;" +
                          "-fx-background-color: white;");
        
        //--- combine title and table into center layout ---
        VBox centerBox = new VBox(20, title, tableBox);
        centerBox.setPadding(new Insets(30, 40, 40, 40));
        centerBox.setAlignment(Pos.TOP_CENTER);
     
        BorderPane root = new BorderPane();
        root.setTop(navBar);
        root.setCenter(centerBox);
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Application History");
        stage.setScene(scene);
        stage.show();
        
        // ── Navbar actions ──
        homeBtn.setOnAction(ex -> {
            new HomePage(currentOwner).show();
            stage.close();
        });
        myPetBtn.setOnAction(ex -> {
            new MyPets(currentOwner).show();
            stage.close();
        });
        newAppBtn.setOnAction(ex -> {
            new NewApplication(currentOwner).show();
            stage.close();
        });
        manageBtn.setOnAction(ex -> {
            new ManageApplication(currentOwner).show();
            stage.close();
        });
        
    }
    
    //--- create horizontal box for table header ---
    private HBox createHeader() {
        String headerStyle = "-fx-font-weight: bold; -fx-font-size: 13px;";

        Label applicationIdHeader = new Label("Application ID");
        applicationIdHeader.setStyle(headerStyle);
        applicationIdHeader.setPrefWidth(150);

        Label dateHeader = new Label("Submission Date");
        dateHeader.setStyle(headerStyle);
        dateHeader.setPrefWidth(150);

        Label statusHeader = new Label("Status");
        statusHeader.setStyle(headerStyle);
        statusHeader.setPrefWidth(120);

        Label commentHeader = new Label("Comment");
        commentHeader.setStyle(headerStyle);
        commentHeader.setPrefWidth(150);
        
        //--- combine all labels into one header row ---
        HBox header = new HBox(20, applicationIdHeader, dateHeader, statusHeader, commentHeader);
        header.setPadding(new Insets(12, 10, 12, 10));
        header.setStyle("-fx-background-color: #eeeeee;");

        return header;
    }
    
    //--- create horizontal box for rows to store application record ---
    private HBox createRow(String[] app) {
        Label applicationIdLabel = new Label(app[0]);
        applicationIdLabel.setPrefWidth(150);

        Label dateLabel = new Label(app[6]);
        dateLabel.setPrefWidth(150);

        Label statusLabel = new Label(app[5]);
        statusLabel.setPrefWidth(120);

        Label commentLabel = new Label(app[7]);
        commentLabel.setPrefWidth(300);
        commentLabel.setWrapText(true);

        //--- set colors based on status ---
        if (app[5].equalsIgnoreCase("APPROVED")) {
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } 
        else if (app[5].equalsIgnoreCase("REJECTED")) {
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } 
        else {
            statusLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        }
        
        //--- combine all labels to creat one row ---
        HBox row = new HBox(20, applicationIdLabel, dateLabel, statusLabel, commentLabel);
        row.setPadding(new Insets(12, 10, 12, 10));
        row.setStyle("-fx-border-color: #eeeeee;" + "-fx-border-width: 0 0 1 0;" +
                     "-fx-background-color: white;");
        return row;
    }

}