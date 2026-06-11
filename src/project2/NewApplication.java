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

    public NewApplication(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        this.stage = new Stage();
    }

    public void show() {
        Stage primaryStage = this.stage;

        // ── Navigation bar ────────────────────────────────────────
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

        String linkStyle = "-fx-text-fill: #000000; -fx-underline: false; -fx-font-size: 14px;";
        homeBtn.setStyle(linkStyle);
        myPetBtn.setStyle(linkStyle);
        newAppBtn.setStyle(linkStyle);
        historyBtn.setStyle(linkStyle);
        manageBtn.setStyle(linkStyle);

        navBar.getChildren().addAll(webname, navSpacer, homeBtn, myPetBtn,
                                    newAppBtn, historyBtn, manageBtn);

        // ── Form ──────────────────────────────────────────────────
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(30));
        pane.setAlignment(Pos.CENTER);

        Label title = new Label("Adoption Application");
        title.setFont(new Font("Arial", 28));
        title.setStyle("-fx-font-weight: bold;");

        // Applicant ID — locked to logged-in user
        Label applicantIdLabel = new Label("Applicant ID");
        TextField applicantIdField = new TextField(currentOwner.getOwnerID());
        applicantIdField.setEditable(false);
        applicantIdField.setStyle("-fx-background-color: #f0f0f0;");

        // Pet selection — dropdown of available pets (not owned by this user)
        Label petLabel = new Label("Select Pet");

        // Load only available pets that don't belong to the current user
        ArrayList<Pet> availablePets = new ArrayList<>();
        for (Pet p : FileHandler.loadPets()) {
            if (p.getAdoptionStatus().equalsIgnoreCase("available")
                    && !p.getOwnerID().equals(currentOwner.getOwnerID())) {
                availablePets.add(p);
            }
        }

        ComboBox<String> petDropdown = new ComboBox<>();
        for (Pet p : availablePets) {
            petDropdown.getItems().add(p.getPetID() + " – " + p.getName() + " (" + p.getSpecies() + ")");
        }
        petDropdown.setPromptText("Choose a pet");
        petDropdown.setPrefWidth(500);

        // Email — auto-filled from logged-in user, read-only
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField(currentOwner.getOwnerEmail());
        emailField.setEditable(false);
        emailField.setStyle("-fx-background-color: #f0f0f0;");

        // Adoption reason
        Label describeLabel = new Label("Describe your adoption reason");
        TextArea describeArea = new TextArea();
        describeArea.setPromptText("Share your experience / Convince the owner / Your commitment...");
        describeArea.setPrefWidth(500);

        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(500);
        submitBtn.setPrefHeight(35);
        submitBtn.setStyle("-fx-background-color: #FBC473; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 5px;");

        HBox titleCenter = new HBox(title);
        titleCenter.setAlignment(Pos.CENTER);

        pane.add(titleCenter,      0, 0);
        pane.add(applicantIdLabel, 0, 1);
        pane.add(applicantIdField, 0, 2);
        pane.add(petLabel,         0, 3);
        pane.add(petDropdown,      0, 4);
        pane.add(emailLabel,       0, 5);
        pane.add(emailField,       0, 6);
        pane.add(describeLabel,    0, 7);
        pane.add(describeArea,     0, 8);
        pane.add(submitBtn,        0, 9);

        BorderPane root = new BorderPane();
        root.setTop(navBar);
        root.setCenter(pane);
        root.setStyle("-fx-background-color: white;");
        pane.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("New Application");
        stage.setScene(scene);
        stage.show();

        // ── Submit action ─────────────────────────────────────────
        submitBtn.setOnAction(e -> {
            String desc = describeArea.getText().trim();

            if (petDropdown.getValue() == null || desc.isEmpty()) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Please select a pet and fill in all fields.");
                err.showAndWait();
                return;
            }

            // Extract the selected pet's ID from the dropdown label (format: "P101 – Name (Species)")
            String selectedLabel = petDropdown.getValue();
            String selectedPetId = selectedLabel.split(" – ")[0].trim();

            String applicationId  = FileHandler.generateApplicationId();
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

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Application submitted!");
            alert.showAndWait();

            describeArea.clear();
            petDropdown.setValue(null);
        });

        // ── Navbar actions ────────────────────────────────────────
        homeBtn.setOnAction(ex -> { new HomePage(currentOwner).show(); stage.close(); });
        myPetBtn.setOnAction(ex -> { new MyPets(currentOwner).show(); stage.close(); });
        historyBtn.setOnAction(ex -> { new ApplicationHistory(currentOwner).show(); stage.close(); });
        manageBtn.setOnAction(ex -> { new ManageApplication(currentOwner).show(); stage.close(); });
    }
}
