/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PetProfile {
    
    private Stage stage;
    private Pet pet;
    private PetOwner currentOwner;

    // Constructor — receives the selected Pet and the logged-in owner
    public PetProfile(Pet pet, PetOwner currentOwner) {
        this.pet          = pet;
        this.currentOwner = currentOwner;
        this.stage        = new Stage();
    }

    public void show() {

        // ── Title ────────────────────────────────────────────────
        Label lblTitle = new Label("PET PROFILE");
        lblTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        // ── Pet details (read-only labels, pre-filled from Pet object) ──
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(30);
        grid.setVgap(10);

        grid.add(new Label("Pet ID:"),       0, 0);
        grid.add(makeValue(pet.getPetID()),  1, 0);

        grid.add(new Label("Pet Name:"),     0, 1);
        grid.add(makeValue(pet.getName()),   1, 1);

        grid.add(new Label("Species:"),      0, 2);
        grid.add(makeValue(pet.getSpecies()), 1, 2);

        grid.add(new Label("Breed:"),        0, 3);
        grid.add(makeValue(pet.getBreed()),  1, 3);

        grid.add(new Label("Age:"),          0, 4);
        grid.add(makeValue(String.valueOf(pet.getAge()) + " year(s)"), 1, 4);

        grid.add(new Label("Gender:"),       0, 5);
        grid.add(makeValue(pet.getGender()), 1, 5);

        grid.add(new Label("Health Status:"),     0, 6);
        grid.add(makeValue(pet.getHealthStatus()), 1, 6);

        grid.add(new Label("Adoption Status:"),     0, 7);
        grid.add(makeValue(pet.getAdoptionStatus()), 1, 7);

        Button adoptBtn = new Button("Adopt Me");
        adoptBtn.setPrefWidth(200);
        adoptBtn.setStyle(
            "-fx-background-color: black; " +
            "-fx-text-fill: white;"
        );

        adoptBtn.setOnAction(e -> {
            new NewApplication(currentOwner).show();
            stage.close();
        });;
        
        // ── Back button ───────────────────────────────────────────
        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> {
            new HomePage(currentOwner).show();
            stage.close();
        });

        // ── Full layout ───────────────────────────────────────────
        VBox root = new VBox(15,
                lblTitle,
                grid,
                adoptBtn,
                backBtn
        );
        root.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(root, 520, 450);
        stage.setTitle("Pet Profile – " + pet.getName());
        stage.setScene(scene);
        stage.show();
    }

    // Helper — creates a bold read-only label to display a pet field value
    private Label makeValue(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-weight: bold;");
        return lbl;
    }
    
}
