/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aisyahrosdan
 */
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ManageApplication extends Application{
    @Override
    public void start(Stage primaryStage) {
 
        // navigation bar
        Label webname = new Label("FurEver Friends");
        webname.setFont(new Font("Arial", 15));
        webname.setStyle("-fx-font-weight: bold;");
 
        Button homeBtn = new Button("Home");
        Button myPetBtn = new Button("My Pet");
        Button newAppBtn = new Button("New Application");
        Button historyBtn = new Button("Application History");
        Button manageBtn = new Button("Manage Application");
 
        // current page
        manageBtn.setStyle("-fx-background-color: black; -fx-text-fill: white;");
 
        Region navSpacer = new Region();
        HBox.setHgrow(navSpacer, Priority.ALWAYS);
 
        HBox navBar = new HBox(15, webname, navSpacer,
                homeBtn, myPetBtn, newAppBtn, historyBtn, manageBtn);
        navBar.setPadding(new Insets(15));
        navBar.setAlignment(Pos.CENTER_LEFT);
 
        // title
        Label title = new Label("Manage Application");
        title.setFont(new Font("Arial", 28));
 
        // table
        // We use a VBox to build rows manually so we can add
        // interactive Comment field + Approve/Reject buttons per row
 
        // table header
        HBox tableHeader = createHeader();
 
        VBox tableRows = new VBox(0);
        tableRows.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");
 
        // load data from applications.txt
        List<String[]> applications = loadApplications();
 
        if (applications.isEmpty()) {
            Label emptyLabel = new Label("No applications found.");
            emptyLabel.setFont(new Font("Arial", 13));
            emptyLabel.setPadding(new Insets(20));
            tableRows.getChildren().add(emptyLabel);
        } else {
            for (String[] app : applications) {
                HBox row = createRow(app, tableRows, applications);
                tableRows.getChildren().add(row);
            }
        }
 
        VBox tableBox = new VBox(0, tableHeader, tableRows);
        tableBox.setStyle(
            "-fx-border-color: #cccccc;" +
            "-fx-border-width: 1;" +
            "-fx-background-color: white;"
        );
 
        // center layout
        VBox centerBox = new VBox(20, title, tableBox);
        centerBox.setPadding(new Insets(30, 40, 40, 40));
        centerBox.setAlignment(Pos.TOP_CENTER);
 
        ScrollPane scrollPane = new ScrollPane(centerBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f5f5f5; -fx-background: #f5f5f5;");
 
        // root layout
        BorderPane root = new BorderPane();
        root.setTop(navBar);
        root.setCenter(scrollPane);
        root.setStyle("-fx-background-color: #f5f5f5;");
 
        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setTitle("Manage Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    // create table header
    private HBox createHeader() {
        String headerStyle =
            "-fx-font-weight: bold; -fx-font-size: 13px;";
 
        Label appIdHeader = new Label("Application ID");
        appIdHeader.setStyle(headerStyle);
        appIdHeader.setPrefWidth(130);
 
        Label applicantHeader = new Label("Applicant ID");
        applicantHeader.setStyle(headerStyle);
        applicantHeader.setPrefWidth(130);
 
        Label petHeader = new Label("Pet ID");
        petHeader.setStyle(headerStyle);
        petHeader.setPrefWidth(80);
 
        Label emailHeader = new Label("Email");
        emailHeader.setStyle(headerStyle);
        emailHeader.setPrefWidth(180);
 
        Label descHeader = new Label("Description");
        descHeader.setStyle(headerStyle);
        descHeader.setPrefWidth(200);
 
        Label commentHeader = new Label("Comment");
        commentHeader.setStyle(headerStyle);
        commentHeader.setPrefWidth(180);
 
        Label actionHeader = new Label("Action");
        actionHeader.setStyle(headerStyle);
        actionHeader.setPrefWidth(160);
 
        HBox header = new HBox(10,
            appIdHeader, applicantHeader, petHeader,
            emailHeader, descHeader, commentHeader, actionHeader);
        header.setPadding(new Insets(12, 10, 12, 10));
        header.setStyle("-fx-background-color: #eeeeee;");
        header.setAlignment(Pos.CENTER_LEFT);
 
        return header;
    }
 
    // create table row
    private HBox createRow(String[] app, VBox tableRows, List<String[]> applications) {
 
        // app format: [appId, applicantId, petId, email, description, status, date, comment]
        String appId       = app.length > 0 ? app[0] : "";
        String applicantId = app.length > 1 ? app[1] : "";
        String petId       = app.length > 2 ? app[2] : "";
        String email       = app.length > 3 ? app[3] : "";
        String description = app.length > 4 ? app[4] : "";
        String status      = app.length > 5 ? app[5] : "PENDING";
 
        Label appIdLabel = new Label(appId);
        appIdLabel.setPrefWidth(130);
        appIdLabel.setWrapText(true);
 
        Label applicantLabel = new Label(applicantId);
        applicantLabel.setPrefWidth(130);
        applicantLabel.setWrapText(true);
 
        Label petLabel = new Label(petId);
        petLabel.setPrefWidth(80);
 
        Label emailLabel = new Label(email);
        emailLabel.setPrefWidth(180);
        emailLabel.setWrapText(true);
 
        Label descLabel = new Label(description);
        descLabel.setPrefWidth(200);
        descLabel.setWrapText(true);
 
        // comment text field
        TextField commentField = new TextField();
        commentField.setPromptText("Enter your comment here");
        commentField.setPrefWidth(180);
 
        // status label (shown after approve/reject)
        Label statusLabel = new Label(status);
        statusLabel.setFont(new Font("Arial", 12));
        if (status.equals("APPROVED")) {
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else if (status.equals("REJECTED")) {
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
 
        // approve button
        Button approveBtn = new Button("Approve");
        approveBtn.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
 
        // reject Button
        Button rejectBtn = new Button("Reject");
        rejectBtn.setStyle(
            "-fx-background-color: #e53935;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
 
        // approve action
        approveBtn.setOnAction(e -> {
            String comment = commentField.getText().trim();
            if (comment.isEmpty()) comment = "Approved";
            updateApplication(appId, "APPROVED", comment);
            statusLabel.setText("APPROVED");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            approveBtn.setDisable(true);
            rejectBtn.setDisable(true);
            showAlert("Application " + appId + " has been APPROVED.");
        });
 
        // reject action
        rejectBtn.setOnAction(e -> {
            String comment = commentField.getText().trim();
            if (comment.isEmpty()) comment = "Rejected";
            updateApplication(appId, "REJECTED", comment);
            statusLabel.setText("REJECTED");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            approveBtn.setDisable(true);
            rejectBtn.setDisable(true);
            showAlert("Application " + appId + " has been REJECTED.");
        });
 
        // disable buttons if already decided
        if (status.equals("APPROVED") || status.equals("REJECTED")) {
            approveBtn.setDisable(true);
            rejectBtn.setDisable(true);
        }
 
        HBox actionBox = new HBox(8, commentField, approveBtn, rejectBtn, statusLabel);
        actionBox.setAlignment(Pos.CENTER_LEFT);
        actionBox.setPrefWidth(400);
 
        HBox row = new HBox(10,
            appIdLabel, applicantLabel, petLabel,
            emailLabel, descLabel, actionBox);
        row.setPadding(new Insets(12, 10, 12, 10));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-border-color: #eeeeee; -fx-border-width: 0 0 1 0; -fx-background-color: white;");
 
        return row;
    }
 
    // load applications from file 
    private List<String[]> loadApplications() {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("applications.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    list.add(parts);
                }
            }
        } catch (IOException e) {
            // file not found — return empty list
        }
        return list;
    }
 
    //  update application status in file 
    private void updateApplication(String appId, String newStatus, String comment) {
        try {
            File file = new File("applications.txt");
            List<String> lines = Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();
 
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 8 && parts[0].equals(appId)) {
                    // update status (index 5) and comment (index 7)
                    parts[5] = newStatus;
                    parts[7] = comment;
                    line = String.join(",", parts);
                }
                updatedLines.add(line);
            }
 
            Files.write(file.toPath(), updatedLines);
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // show alert 
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}
