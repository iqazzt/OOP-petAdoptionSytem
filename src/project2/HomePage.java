/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HomePage {
    private Stage stage;
    private PetOwner currentOwner;

    public HomePage(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        stage = new Stage();

        BorderPane layout = new BorderPane();

        // ── Navigation bar ────────────────────────────────────────
        HBox navbar = new HBox();
        navbar.setSpacing(20);
        navbar.setPadding(new Insets(10));
        navbar.setAlignment(Pos.TOP_RIGHT);

        Label homeLbl      = new Label("Home");
        Label myPetLbl     = new Label("My Pet");
        Label newAppLbl    = new Label("New Application");
        Label appHistLbl   = new Label("Application History");
        Label manageAppLbl = new Label("Manage Application");

        navbar.getChildren().addAll(homeLbl, myPetLbl, newAppLbl, appHistLbl, manageAppLbl);
        layout.setTop(navbar);

        // ── Page heading ──────────────────────────────────────────
        Label heading = new Label("List of Pets");
        heading.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        heading.setPadding(new Insets(15, 15, 5, 15));

        // ── Pet listing ───────────────────────────────────────────
        FlowPane petGrid = new FlowPane();
        petGrid.setHgap(15);
        petGrid.setVgap(15);
        petGrid.setPadding(new Insets(15));

        // Load all pets then exclude pets owned by the logged-in user
        // A user cannot adopt their own pets
        ArrayList<Pet> allPets = FileHandler.loadPets();
        ArrayList<Pet> othersPets = new ArrayList<>();
        for (Pet pet : allPets) {
            if (!pet.getOwnerID().equals(currentOwner.getOwnerID())) {
                othersPets.add(pet);
            }
        }

if (othersPets.isEmpty()) {
    Label emptyLbl = new Label("No pets available at the moment.");
    emptyLbl.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
    petGrid.getChildren().add(emptyLbl);
} else {
    for (Pet pet : othersPets) {
        VBox card = buildPetCard(pet);
        petGrid.getChildren().add(card);
    }
}

        VBox centerBox = new VBox(5);
        centerBox.setPadding(new Insets(10));

        centerBox.getChildren().addAll(
            heading,
            new Separator(),
            petGrid
        );

        layout.setCenter(centerBox);

        // ── Add Pet button (bottom right) ─────────────────────────
        Button addpetBtn = new Button("Add Pet");
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setPadding(new Insets(10));
        bottom.getChildren().add(addpetBtn);
        layout.setBottom(bottom);

        // ── Button and label actions ──────────────────────────────
        addpetBtn.setOnAction(e -> {
            new AddPet(currentOwner).show();
            stage.close();
        });

        myPetLbl.setOnMouseClicked(e -> {
            new MyPets(currentOwner).show();
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

        Scene scene = new Scene(layout, 900, 600);
        stage.setScene(scene);
        stage.setTitle("FurEver Friends – Home");
    }

    public void show() {
        stage.show();
    }

    // ── Build one pet card ────────────────────────────────────────
    private VBox buildPetCard(Pet pet) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.setPrefWidth(200);
        card.setStyle(
            "-fx-border-color: #cccccc; " +
            "-fx-border-radius: 8; " +
            "-fx-background-color: #ffffff; " +
            "-fx-background-radius: 8;"
        );

        // Pet name as card title
        Label nameLbl = new Label(pet.getName());
        nameLbl.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));

        // Key details
        Label speciesLbl = new Label("Species : " + pet.getSpecies());
        Label breedLbl   = new Label("Breed   : " + pet.getBreed());
        Label ageLbl     = new Label("Age     : " + pet.getAge() + " year(s)");
        Label genderLbl  = new Label("Gender  : " + pet.getGender());
        Label statusLbl  = new Label("Status  : " + pet.getAdoptionStatus());
        statusLbl.setStyle(
            pet.getAdoptionStatus().equalsIgnoreCase("available")
                ? "-fx-text-fill: green; -fx-font-weight: bold;"
                : "-fx-text-fill: gray;"
        );

        // View Pet button — opens PetProfile with this pet
        Button viewBtn = new Button("View Pet");
        viewBtn.setPrefWidth(176);
        viewBtn.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-cursor: hand;");
        viewBtn.setOnAction(e -> {
            new PetProfile(pet, currentOwner).show();
        });

        card.getChildren().addAll(nameLbl, speciesLbl, breedLbl, ageLbl, genderLbl, statusLbl, viewBtn);
        return card;
    }
}
