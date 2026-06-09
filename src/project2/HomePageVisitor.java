/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aisyahrosdan
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class HomePageVisitor extends Application {
    @Override
    public void start(Stage primaryStage) {
 
        // ── navigation bar 
        Label webname = new Label("FurEver Friends");
        webname.setFont(new Font("Arial", 15));
        webname.setStyle("-fx-font-weight: bold;");
 
        Button homeNavBtn = new Button("Home");
        Button faqBtn = new Button("FAQs");
        Button contactBtn = new Button("Contact Us");
 
        // style naviigation button
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
 
        // hero image
        Rectangle heroImage = new Rectangle(900, 280);
        heroImage.setFill(Color.web("#d9d9d9"));
        heroImage.setArcWidth(12);
        heroImage.setArcHeight(12);
 
        Label heroLabel = new Label("🐱  Hero Image");
        heroLabel.setTextFill(Color.GRAY);
        heroLabel.setFont(new Font("Arial", 16));
 
        StackPane heroPane = new StackPane(heroImage, heroLabel);
        heroPane.setMaxWidth(900);
 
        VBox welcomeSection = new VBox(20, welcomeText, heroPane);
        welcomeSection.setPadding(new Insets(30, 40, 10, 40));
 
        // pet catalogue 
        Label catalogueTitle = new Label("Pet Catalogue");
        catalogueTitle.setFont(new Font("Arial", 28));
        catalogueTitle.setStyle("-fx-font-weight: bold;");
 
        // pet data 
        String[][] pets = {
            {"Sunny", "Duck", "A cheerful little duckling looking for a pond-side home."},
            {"Koko", "Monkey", "Playful and curious, loves bananas and cuddles."},
            {"Buddy", "Dog", "Golden retriever pup, full of energy and love."},
            {"Snowball", "Cat", "Fluffy white cat, calm and loves to be pampered."},
            {"Shadow", "Dog", "A gentle Pomeranian mix, great with kids."},
            {"Mango", "Cat", "Loves sunny spots and afternoon naps."}
        };
 
        // pet card colours
        String[] cardColors = {"#f5e642", "#c8a96e", "#f0c060",
                               "#e8e8e8", "#b0b0b0", "#f0a060"};
 
        // build grid 
        GridPane petGrid = new GridPane();
        petGrid.setHgap(20);
        petGrid.setVgap(20);
        petGrid.setAlignment(Pos.CENTER);
 
        for (int i = 0; i < pets.length; i++) {
            int col = i % 3;
            int row = i / 3;
            petGrid.add(createPetCard(pets[i][0], pets[i][1], pets[i][2], cardColors[i]), col, row);
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
 
    // create pet card
    private VBox createPetCard(String name, String type, String description, String color) {
 
        // image placeholder
        Rectangle imgBox = new Rectangle(240, 180);
        imgBox.setFill(Color.web(color));
        imgBox.setArcWidth(8);
        imgBox.setArcHeight(8);
 
        // emoji pet
        String emoji;
        if (type.equals("Cat")) {
            emoji = "🐱";
        } else if (type.equals("Dog")) {
            emoji = "🐶";
        } else if (type.equals("Duck")) {
            emoji = "🐥";
        } else if (type.equals("Monkey")) {
            emoji = "🐒";
        } else {
            emoji = "🐾";
        }
 
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(new Font("Arial", 40));
 
        StackPane imgPane = new StackPane(imgBox, emojiLabel);
 
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
 
    public static void main(String[] args) {
        launch(args);
    }
}