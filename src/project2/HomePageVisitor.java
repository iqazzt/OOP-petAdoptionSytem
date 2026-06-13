/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HomePageVisitor extends Application {
    @Override
    public void start(Stage primaryStage) {

        // navigation bar
        Label webname = new Label("FurEver Friends");
        webname.setFont(new Font("Arial", 15));
        webname.setStyle("-fx-font-weight: bold;");

        Button homeNavBtn = new Button("Home");
        Button faqBtn = new Button("FAQs");
        Button contactBtn = new Button("Contact Us");

        // style navigation button
        String navBtnStyle = "-fx-background-color: transparent; -fx-font-size: 13px; -fx-cursor: hand;";
        homeNavBtn.setStyle(navBtnStyle);
        faqBtn.setStyle(navBtnStyle);
        contactBtn.setStyle(navBtnStyle);

        // login button
        Button loginBtn = new Button("LOGIN");
        loginBtn.setStyle(
            "-fx-background-color: black;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 8 20;" +
            "-fx-cursor: hand;"
        );

        loginBtn.setOnAction(e -> {
            login_register_page loginPage = new login_register_page(primaryStage);
            try {
                loginPage.getRegisterScene(); // pre-build register scene so the link works
                primaryStage.setScene(loginPage.getLoginScene());
                primaryStage.setTitle("FurEver Friends – Login");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Region navSpacer = new Region();
        HBox.setHgrow(navSpacer, Priority.ALWAYS);

        HBox navBar = new HBox(20, webname, navSpacer, homeNavBtn, faqBtn, contactBtn, loginBtn);
        navBar.setPadding(new Insets(15, 30, 15, 30));
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        // welcome
        Label welcomeTitle = new Label("WELCOME!");
        welcomeTitle.setFont(new Font("Arial", 38));
        welcomeTitle.setStyle("-fx-font-weight: bold;");

        Label welcomeSubtitle = new Label(
            "Find your perfect furry companion.\n" +
            "Browse our pets and give them a loving forever home."
        );
        welcomeSubtitle.setFont(new Font("Arial", 14));
        welcomeSubtitle.setTextFill(Color.GRAY);
        welcomeSubtitle.setWrapText(true);

        VBox welcomeText = new VBox(8, welcomeTitle, welcomeSubtitle);
        welcomeText.setMaxWidth(400);

        // hero image from URL
        ImageView heroImageView;
        try {
            Image heroImg = new Image("https://images.unsplash.com/photo-1450778869180-41d0601e046e?w=900", 900, 280, false, true);
            heroImageView = new ImageView(heroImg);
        } catch (Exception e) {
            heroImageView = new ImageView();
        }
        heroImageView.setFitWidth(900);
        heroImageView.setFitHeight(280);
        heroImageView.setPreserveRatio(false);
        heroImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        StackPane heroPane = new StackPane(heroImageView);
        heroPane.setMaxWidth(900);

        VBox welcomeSection = new VBox(20, welcomeText, heroPane);
        welcomeSection.setPadding(new Insets(30, 40, 10, 40));

        // pet catalogue
        Label catalogueTitle = new Label("Pet Catalogue");
        catalogueTitle.setFont(new Font("Arial", 28));
        catalogueTitle.setStyle("-fx-font-weight: bold;");

        // pet data with image URLs
        String[][] pets = {
            {"Buddy", "Dog", "Golden retriever pup, full of energy and love.",
             "https://images.unsplash.com/photo-1552053831-71594a27632d?w=300"},
            {"Snowball", "Cat", "Fluffy white cat, calm and loves to be pampered.",
             "https://images.unsplash.com/photo-1574158622682-e40e69881006?w=300"},
            {"Shadow", "Dog", "A gentle Pomeranian mix, great with kids.",
             "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=300"},
            {"Mango", "Cat", "Loves sunny spots and afternoon naps.",
             "https://images.unsplash.com/photo-1533738363-b7f9aef128ce?w=300"},
            {"Coco", "Rabbit", "A fluffy bunny who loves to hop around.",
             "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?w=300"},
            {"Nemo", "Cat", "Playful orange tabby, loves chasing toys.",
             "https://images.unsplash.com/photo-1495360010541-f48722b34f7d?w=300"}
        };

        // build grid
        GridPane petGrid = new GridPane();
        petGrid.setHgap(20);
        petGrid.setVgap(20);
        petGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < pets.length; i++) {
            int col = i % 3;
            int row = i / 3;
            petGrid.add(createPetCard(pets[i][0], pets[i][1], pets[i][2], pets[i][3]), col, row);
        }

        VBox catalogueSection = new VBox(20, catalogueTitle, petGrid);
        catalogueSection.setPadding(new Insets(30, 40, 40, 40));

        // full page layout
        VBox pageContent = new VBox(0, welcomeSection, catalogueSection);

        ScrollPane scrollPane = new ScrollPane(pageContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white; -fx-background: white;");

        BorderPane root = new BorderPane();
        root.setTop(navBar);
        root.setCenter(scrollPane);
        root.setStyle("-fx-background-color: white;");

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("FurEver Friends – Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // create pet card with image URL
    private VBox createPetCard(String name, String type, String description, String imageUrl) {

        // load image from URL
        ImageView imgView;
        try {
            Image img = new Image(imageUrl, 240, 180, false, true);
            imgView = new ImageView(img);
        } catch (Exception e) {
            imgView = new ImageView();
        }
        imgView.setFitWidth(240);
        imgView.setFitHeight(180);
        imgView.setPreserveRatio(false);

        // rounded corners using clip
        Rectangle clip = new Rectangle(240, 180);
        clip.setArcWidth(8);
        clip.setArcHeight(8);
        imgView.setClip(clip);

        StackPane imgPane = new StackPane(imgView);

        // pet name
        Label nameLabel = new Label(name + " · " + type);
        nameLabel.setFont(new Font("Arial", 13));
        nameLabel.setStyle("-fx-font-weight: bold;");

        // description
        Label descLabel = new Label(description);
        descLabel.setFont(new Font("Arial", 12));
        descLabel.setTextFill(Color.GRAY);
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(240);

        VBox card = new VBox(8, imgPane, nameLabel, descLabel);
        card.setPrefWidth(240);

        return card;
    }
}