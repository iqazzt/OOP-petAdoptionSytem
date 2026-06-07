
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

    public PetProfile(Pet pet, PetOwner currentOwner) {
        this.pet = pet;
        this.currentOwner = currentOwner;
        this.stage = new Stage();
    }

    public void show() {

        // TITLE
        Label title = new Label("PET PROFILE");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // GRID DETAILS
        GridPane grid = new GridPane();
        grid.setHgap(25);   
        grid.setVgap(12);   
        grid.setAlignment(Pos.CENTER);

        grid.add(new Label("Pet ID:"), 0, 0);
        grid.add(makeValue(pet.getPetID()), 1, 0);

        grid.add(new Label("Pet Name:"), 0, 1);
        grid.add(makeValue(pet.getName()), 1, 1);

        grid.add(new Label("Species:"), 0, 2);
        grid.add(makeValue(pet.getSpecies()), 1, 2);

        grid.add(new Label("Breed:"), 0, 3);
        grid.add(makeValue(pet.getBreed()), 1, 3);

        grid.add(new Label("Age:"), 0, 4);
        grid.add(makeValue(pet.getAge() + " year(s)"), 1, 4);

        grid.add(new Label("Gender:"), 0, 5);
        grid.add(makeValue(pet.getGender()), 1, 5);

        grid.add(new Label("Health Status:"), 0, 6);
        grid.add(makeValue(pet.getHealthStatus()), 1, 6);

        grid.add(new Label("Adoption Status:"), 0, 7);
        grid.add(makeValue(pet.getAdoptionStatus()), 1, 7);

        // BUTTONS
        Button adopt = new Button("Adopt Me");
        adopt.setPrefWidth(200);
        adopt.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        adopt.setOnAction(e -> {
            new NewApplication(currentOwner).show();
            stage.close();
        });

        Button back = new Button("← Back");
        back.setPrefWidth(200);

        back.setOnAction(e -> {
            new HomePage(currentOwner).show();
            stage.close();
        });

        // LAYOUT GRID
        VBox content = new VBox(20, grid); 
        content.setAlignment(Pos.CENTER);

        VBox buttons = new VBox(12, adopt, back);
        buttons.setAlignment(Pos.CENTER);

        // ROOT LAYOUT
        VBox root = new VBox(25, title, content, buttons); 
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30)); 

        Scene scene = new Scene(root, 650, 600);

        stage.setTitle("Pet Profile - " + pet.getName());
        stage.setScene(scene);
        stage.show();
    }

    private Label makeValue(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-font-weight: bold;");
        return lbl;
    }
}
