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
        layout.setStyle("-fx-background-color: #F8F8F8;");


        BorderPane navbar = new BorderPane();

        navbar.setPadding(new Insets(15, 25, 15, 25));

        navbar.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #E5E5E5;" +
                "-fx-border-width: 0 0 1 0;"
        );

        Label systemLbl = new Label("FurEver Friends");

        systemLbl.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        navbar.setLeft(systemLbl);

        HBox menuBox = new HBox(10);
        menuBox.setAlignment(Pos.CENTER_RIGHT);

        Button homeBtn = createNavButton("Home");
        Button myPetBtn = createNavButton("My Pet");
        Button newAppBtn = createNavButton("New Application");
        Button appHistBtn = createNavButton("Application History");
        Button manageAppBtn = createNavButton("Manage Application");

        menuBox.getChildren().addAll(
                homeBtn,
                myPetBtn,
                newAppBtn,
                appHistBtn,
                manageAppBtn
        );

        navbar.setRight(menuBox);

        layout.setTop(navbar);


        ArrayList<Pet> allPets = FileHandler.loadPets();
        ArrayList<Pet> othersPets = new ArrayList<>();

        for (Pet pet : allPets) {

            if (!pet.getOwnerID().equals(currentOwner.getOwnerID())) {
                othersPets.add(pet);
            }
        }

        Label heading =
                new Label("🐾 Pets Available For Adoption");

        heading.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        28
                )
        );

        Label totalLbl =
                new Label("Total Available Pets : "
                        + othersPets.size());

        totalLbl.setStyle(
                "-fx-text-fill: #666666;" +
                "-fx-font-size: 13px;"
        );


        FlowPane petGrid = new FlowPane();

        petGrid.setAlignment(Pos.TOP_CENTER);
        petGrid.setHgap(25);
        petGrid.setVgap(25);
        petGrid.setPadding(new Insets(25));

        if (othersPets.isEmpty()) {

            Label emptyLbl =
                    new Label(
                            "No pets available at the moment."
                    );

            emptyLbl.setStyle(
                    "-fx-text-fill: gray;" +
                    "-fx-font-size: 14px;"
            );

            petGrid.getChildren().add(emptyLbl);

        } else {

            for (Pet pet : othersPets) {

                VBox card = buildPetCard(pet);

                petGrid.getChildren().add(card);
            }
        }

        VBox centerBox = new VBox(10);

        centerBox.setPadding(new Insets(15));

        centerBox.getChildren().addAll(
                heading,
                totalLbl,
                new Separator(),
                petGrid
        );

        layout.setCenter(centerBox);


        Button addPetBtn =
                new Button("➕ Add Pet");

        addPetBtn.setStyle(
                "-fx-background-color: #28A745;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;"
        );

        addPetBtn.setPrefWidth(130);
        addPetBtn.setPrefHeight(35);

        HBox bottom = new HBox();

        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setPadding(new Insets(15));

        bottom.getChildren().add(addPetBtn);

        layout.setBottom(bottom);


        addPetBtn.setOnAction(e -> {
            new AddPet(currentOwner).show();
            stage.close();
        });

        homeBtn.setOnAction(e -> {
            new HomePage(currentOwner).show();
            stage.close();
        });

        myPetBtn.setOnAction(e -> {
            new MyPets(currentOwner).show();
            stage.close();
        });

        newAppBtn.setOnAction(e -> {
            new NewApplication(currentOwner).show();
            stage.close();
        });

        appHistBtn.setOnAction(e -> {
            new ApplicationHistory(currentOwner).show();
            stage.close();
        });

        manageAppBtn.setOnAction(e -> {
            new ManageApplication(currentOwner).show();
            stage.close();
        });

        Scene scene =
                new Scene(layout, 1000, 600);

        stage.setScene(scene);
        stage.setTitle("FurEver Friends - Home");
    }

    public void show() {
        stage.show();
    }

    private Button createNavButton(String text) {

        Button btn = new Button(text);

        btn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #444444;" +
                "-fx-font-size: 13px;" +
                "-fx-cursor: hand;"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-background-color: #FF69B4;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                )
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: #444444;" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;"
                )
        );

        return btn;
    }

    private VBox buildPetCard(Pet pet) {

        VBox card = new VBox(8);

        card.setPadding(new Insets(15));
        card.setPrefWidth(260);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;" +
                "-fx-border-color: #DDDDDD;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 8,0,0,2);"
        );

        String emoji = "🐾";

        switch (pet.getSpecies().toLowerCase()) {

            case "cat":
                emoji = "🐱";
                break;

            case "dog":
                emoji = "🐶";
                break;

            case "hamster":
                emoji = "🐹";
                break;

            case "rabbit":
                emoji = "🐰";
                break;

            case "bird":
                emoji = "🐦";
                break;

            case "fish":
                emoji = "🐠";
                break;
        }

        Label nameLbl =
                new Label(emoji + " " + pet.getName());

        nameLbl.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        Label speciesLbl =
                new Label("Species : " + pet.getSpecies());

        Label breedLbl =
                new Label("Breed : " + pet.getBreed());

        Label ageLbl =
                new Label("Age : "
                        + pet.getAge()
                        + " year(s)");

        Label genderLbl =
                new Label("Gender : "
                        + pet.getGender());

        Label statusLbl =
                new Label("Status : "
                        + pet.getAdoptionStatus());

        if (pet.getAdoptionStatus()
                .equalsIgnoreCase("available")) {

            statusLbl.setStyle(
                    "-fx-text-fill: green;" +
                    "-fx-font-weight: bold;"
            );

        } else {

            statusLbl.setStyle(
                    "-fx-text-fill: gray;" +
                    "-fx-font-weight: bold;"
            );
        }

        Button viewBtn =
                new Button("View Pet");

        viewBtn.setPrefWidth(220);

        viewBtn.setStyle(
                "-fx-background-color: black;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 5;"
        );

        viewBtn.setOnAction(e -> {
            new PetProfile(pet, currentOwner).show();
        });

        card.getChildren().addAll(
                nameLbl,
                speciesLbl,
                breedLbl,
                ageLbl,
                genderLbl,
                statusLbl,
                viewBtn
        );

        return card;
    }
}