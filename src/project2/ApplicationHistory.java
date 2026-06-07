package project2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ApplicationHistory {

    private Stage stage;
    private PetOwner currentOwner;

    //--- constructor for integration (opened from navbar) ---
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

    //--- build and show the Application History screen ---
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
        
        //--- title ---
        Label title = new Label("Application History");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold;");

        //--- table header ---
        HBox tableHeader = createHeader();
        //--- all remaining rows besides header ---
        VBox tableRows = new VBox(0);

        //--- load data from applications.txt (only show this user's applications) ---
        for (String[] parts : FileHandler.loadApplications()) {
            // parts[1] = applicantId (only show rows submitted by the logged-in user) ---
            if (parts[1].equals(currentOwner.getOwnerID())) {
                HBox row = createRow(parts);
                tableRows.getChildren().add(row);
            }
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
        root.setStyle("-fx-background-color: white;");
     
        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("Application History");
        stage.setScene(scene);
        stage.show();
        
        //--- navbar actions ---
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
        String headerStyle = "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;";

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
        header.setStyle("-fx-background-color: #FBC473;");

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
