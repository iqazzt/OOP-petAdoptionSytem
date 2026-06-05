/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

/**
 *
 * @author HP
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class login_register_page {
    
    private Stage window;
    private Scene loginScene, registerScene;
    
    public login_register_page(Stage stage) {
        this.window = stage;
    }
    
    //Return Login Scene
    public Scene getLoginScene() {

        createLoginScene();
        return loginScene;
    }
   
    //Return Register Scene
    public Scene getRegisterScene() {

        createRegisterScene();
        return registerScene;
    }
    
    // screen login
    private void createLoginScene() {
        
        HBox header = createHeader();
        
        // primary login form
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setMaxWidth(400);
        loginForm.setPadding(new Insets(40, 20, 40, 20));
        
        Label titleLabel = new Label("LOG IN TO YOUR ACCOUNT");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titleLabel.setStyle("-fx-text-fill: #000000; -fx-padding: 0 0 20 0;");
        
        // input userID
        VBox idBox = new VBox(5);
        Label idLabel = new Label("User ID");
        idLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField idField = new TextField();
        idField.setPromptText("Enter your User ID");
        idField.setPrefHeight(45);
        idField.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #E0E0E0; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        idBox.getChildren().addAll(idLabel, idField);
        
        // password
        VBox passBox = new VBox(5);
        Label passLabel = new Label("Password");
        passLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        PasswordField passField = new PasswordField();
        passField.setPromptText("Your password");
        passField.setPrefHeight(45);
        passField.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #E0E0E0; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        passBox.getChildren().addAll(passLabel, passField);
        
        // login button
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(45);
        loginBtn.setStyle("-fx-background-color: #FBC473; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-cursor: hand;");
        
        // link to register if no acc
        HBox registerBox = new HBox(5);
        registerBox.setAlignment(Pos.CENTER);
        Label newLabel = new Label("New to FurEver Friends?");
        Hyperlink registerLink = new Hyperlink("Register here");
        registerLink.setStyle("-fx-text-fill: #FBC473; -fx-underline: false; -fx-font-weight: bold;");
        registerBox.getChildren().addAll(newLabel, registerLink);
        
        loginForm.getChildren().addAll(titleLabel, idBox, passBox, loginBtn, registerBox);
        
        // footer 
        VBox footer = createFooter();
        
        // layout guna borderpane
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setCenter(loginForm);
        mainLayout.setBottom(footer);
        mainLayout.setStyle("-fx-background-color: #FFFFFF;");
        
        loginScene = new Scene(mainLayout, 850, 750);
        
        // login interaction (change to register screen)
        registerLink.setOnAction(e -> window.setScene(registerScene));
        
        // Error label — shown below the login button on failure
        Label loginErrorLbl = new Label();
        loginErrorLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        loginForm.getChildren().add(loginErrorLbl);

        // Login button — validate against owners.txt, then open HomePage
        loginBtn.setOnAction(e -> {
            String uId   = idField.getText().trim();
            String uPass = passField.getText().trim();

            if (uId.isEmpty() || uPass.isEmpty()) {
                loginErrorLbl.setText("Please enter your User ID and Password.");
                return;
            }

            // Read owners.txt to find matching user
            // Format: ownerID,name,email,password
            PetOwner matchedOwner = null;
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("owners.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    // parts[0]=ownerID, parts[1]=name, parts[2]=email, parts[3]=password
                    if (parts.length >= 4 && parts[0].equals(uId) && parts[3].equals(uPass)) {
                        matchedOwner = new PetOwner(parts[1], parts[0], parts[2]);
                        break;
                    }
                }
            } catch (java.io.IOException ex) {
                loginErrorLbl.setText("Could not read user data. Please try again.");
                return;
            }

            if (matchedOwner != null) {
                loginErrorLbl.setText("");
                new HomePage(matchedOwner).show(); // Open the real home page
                window.close();                    // Close login window
            } else {
                loginErrorLbl.setText("Invalid User ID or Password.");
            }
        });
    }
    
    // screen register
    private void createRegisterScene() {
        
        HBox header = createHeader();
        
        // primary register form
        VBox registerForm = new VBox(15);
        registerForm.setAlignment(Pos.CENTER);
        registerForm.setMaxWidth(450);
        registerForm.setStyle("-fx-background-color: rgba(220, 220, 220, 0.85); -fx-background-radius: 10px; -fx-padding: 30px;");
        
        Label titleLabel = new Label("Create an account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #000000;");
        
        // input userID
        VBox regIdBox = new VBox(5);
        regIdBox.setAlignment(Pos.CENTER_LEFT);
        Label regIdLabel = new Label("User ID:");
        regIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField regIdField = new TextField();
        regIdField.setPrefHeight(40);
        regIdField.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC;");
        regIdBox.getChildren().addAll(regIdLabel, regIdField);
        
        // input register email
        VBox emailBox = new VBox(5);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField emailField = new TextField();
        emailField.setPrefHeight(40);
        emailField.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC;");
        emailBox.getChildren().addAll(emailLabel, emailField);
        
        // input register password
        VBox regPassBox = new VBox(5);
        regPassBox.setAlignment(Pos.CENTER_LEFT);
        Label regPassLabel = new Label("Password:");
        regPassLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        PasswordField regPassField = new PasswordField();
        regPassField.setPrefHeight(40);
        regPassField.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC;");
        regPassBox.getChildren().addAll(regPassLabel, regPassField);
        
        Label welcomeLabel = new Label("Welcome to FurEver 🐈");
        welcomeLabel.setFont(Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, javafx.scene.text.FontPosture.ITALIC, 14));
        
        // button submit
        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(120);
        submitBtn.setPrefHeight(35);
        submitBtn.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-cursor: hand;");
        
        registerForm.getChildren().addAll(titleLabel, regIdBox, emailBox, regPassBox, welcomeLabel, submitBtn);
        
        // use stackpane for background pic
        StackPane centerContent = new StackPane();
        centerContent.setPadding(new Insets(50, 0, 50, 0));
        centerContent.setStyle("-fx-background-color: #A0A0A0;"); 
        centerContent.getChildren().add(registerForm);
        
        // layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setCenter(centerContent);
        
        registerScene = new Scene(mainLayout, 850, 750);
        
        // Error/success label shown under the submit button
        Label regMessageLbl = new Label();
        regMessageLbl.setStyle("-fx-font-size: 12px;");
        registerForm.getChildren().add(regMessageLbl);

        // Register interaction — validate then save to owners.txt
        submitBtn.setOnAction(e -> {
            String regId    = regIdField.getText().trim();
            String email    = emailField.getText().trim();
            String password = regPassField.getText().trim();

            // Basic validation
            if (regId.isEmpty() || email.isEmpty() || password.isEmpty()) {
                regMessageLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
                regMessageLbl.setText("Please fill in all fields.");
                return;
            }

            // Check if ID is already taken (read owners.txt)
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("owners.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 1 && parts[0].equals(regId)) {
                        regMessageLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
                        regMessageLbl.setText("User ID already exists. Choose a different ID.");
                        return;
                    }
                }
            } catch (java.io.IOException ex) {
                // owners.txt doesn't exist yet — that's fine, we'll create it on save
            }

            // Save new owner: ownerID,name,email,password
            // Using the User ID as both the ID and display name since the form only asks for ID
            PetOwner newOwner = new PetOwner(regId, regId, email);
            FileHandler.saveOwner(newOwner, password); // saves to owners.txt
            
            regMessageLbl.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
            regMessageLbl.setText("Account created! You can now log in.");

            // Go back to login after a short moment
            javafx.animation.PauseTransition pause =
            new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.5));

            pause.setOnFinished(ev -> {
                window.setScene(loginScene);
            });

            pause.play();
        });
    }
    
    // sync header style
    private HBox createHeader() {
        HBox header = new HBox(25);
        header.setPadding(new Insets(25, 40, 25, 40));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #FFFFFF;");

        Label logo = new Label("FurEver Friends");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Hyperlink lnkHome = new Hyperlink("Home");
        Hyperlink lnkFaq = new Hyperlink("FAQs");
        Hyperlink lnkContact = new Hyperlink("Contact Us");
        
        // menu links
        String linkStyle = "-fx-text-fill: #000000; -fx-underline: false; -fx-font-size: 14px;";
        lnkHome.setStyle(linkStyle);
        lnkFaq.setStyle(linkStyle);
        lnkContact.setStyle(linkStyle);

        Button btnBack = new Button("BACK");
        btnBack.setPrefWidth(80);
        btnBack.setPrefHeight(30);
        btnBack.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 4px; -fx-cursor: hand;");
        
        btnBack.setOnAction(e -> {
            if (window.getScene() == registerScene) {
                window.setScene(loginScene);
            }
        });

        header.getChildren().addAll(logo, spacer, lnkHome, lnkFaq, lnkContact, btnBack);
        return header;
    }
    
    // sync footer style
    private VBox createFooter() {
        VBox footerContainer = new VBox(10);
        footerContainer.setPadding(new Insets(30, 40, 30, 40));
        footerContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E0E0E0; -fx-border-width: 1 0 0 0;");

        HBox topFooter = new HBox();
        Label footerLogo = new Label("FurEver Friends");
        footerLogo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // grid for about, terms, address
        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(5);
        grid.add(new Label("About"), 0, 0);
        grid.add(new Label("Terms"), 1, 0);
        grid.add(new Label("Address"), 2, 0);
        
        // temporary content - will change once decidedd
        for(int i=0; i<3; i++) {
            for(int j=1; j<=2; j++) {
                Label pageLbl = new Label("Page");
                pageLbl.setStyle("-fx-text-fill: #777777; -fx-font-size: 11px;");
                grid.add(pageLbl, i, j);
            }
        }
        
        topFooter.getChildren().addAll(footerLogo, spacer, grid);
        
        Label socialMockup = new Label("🌐  🔗  📺  📸 (Social Media Icons)");
        socialMockup.setStyle("-fx-text-fill: #888888; -fx-font-size: 12px;");

        footerContainer.getChildren().addAll(topFooter, socialMockup);
        return footerContainer;
    }
}
