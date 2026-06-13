/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

/**
 *
 * @author HP
 */

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class login_register_page {
    
    private final Stage window;
    private Scene loginScene, registerScene;
    
    public login_register_page(Stage stage) {
        this.window = stage;
        createLoginScene();
        createRegisterScene();
    }
    
    public Scene getLoginScene() {
        return loginScene;
    }
   
    public Scene getRegisterScene() {
        return registerScene;
    }
    
    // screen login
    private void createLoginScene() {
        HBox header = createHeader();
        
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setMaxWidth(400);
        loginForm.setPadding(new Insets(40, 20, 40, 20));
        
        Label titleLabel = new Label("LOG IN TO YOUR ACCOUNT");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titleLabel.setStyle("-fx-text-fill: #000000; -fx-padding: 0 0 20 0;");
        
        // login guna email
        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email Address");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField loginEmailField = new TextField(); 
        loginEmailField.setPromptText("Enter your email");
        loginEmailField.setPrefHeight(45);
        loginEmailField.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #E0E0E0; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        emailBox.getChildren().addAll(emailLabel, loginEmailField);
        
        VBox passBox = new VBox(5);
        Label passLabel = new Label("Password");
        passLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        PasswordField passField = new PasswordField();
        passField.setPromptText("Your password");
        passField.setPrefHeight(45);
        passField.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #E0E0E0; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        passBox.getChildren().addAll(passLabel, passField);
        
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(45);
        loginBtn.setStyle("-fx-background-color: #FBC473; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-cursor: hand;");
        
        HBox registerBox = new HBox(5);
        registerBox.setAlignment(Pos.CENTER);
        Label newLabel = new Label("New to FurEver Friends?");
        Hyperlink registerLink = new Hyperlink("Register here");
        registerLink.setStyle("-fx-text-fill: #FBC473; -fx-underline: false; -fx-font-weight: bold;");
        registerBox.getChildren().addAll(newLabel, registerLink);
        
        Label loginErrorLbl = new Label();
        loginErrorLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        
        loginForm.getChildren().addAll(titleLabel, emailBox, passBox, loginBtn, registerBox, loginErrorLbl);
        
        VBox footer = createFooter();
        
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setCenter(loginForm);
        mainLayout.setBottom(footer);
        mainLayout.setStyle("-fx-background-color: #FFFFFF;");
        
        loginScene = new Scene(mainLayout, 850, 750);
        
        registerLink.setOnAction(e -> window.setScene(registerScene));
        
        // check email and password
        loginBtn.setOnAction(e -> {
            String inputEmail = loginEmailField.getText().trim();
            String uPass = passField.getText().trim();

            if (inputEmail.isEmpty() || uPass.isEmpty()) {
                loginErrorLbl.setText("Please enter your Email and Password.");
                return;
            }

            PetOwner matchedOwner = null;
            try (BufferedReader br = new BufferedReader(new FileReader("owners.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    // ownerID,name,email,password
                    if (parts.length >= 4) {
                        String fileEmail = parts[2].trim();
                        String filePass = parts[3].trim();
                        
                        // validate using email only
                        if (fileEmail.equalsIgnoreCase(inputEmail) && filePass.equals(uPass)) {
                            // read phone from index 4 if present (new format), default to empty for old records
                            String filePhone = (parts.length >= 5) ? parts[4].trim() : "";
                            matchedOwner = new PetOwner(parts[1], parts[0], fileEmail, filePhone, filePass);
                            break;
                        }
                    }
                }
            } catch (IOException ex) {
                loginErrorLbl.setText("Could not read user data. Please try again.");
                return;
            }

            if (matchedOwner != null) {
                loginErrorLbl.setText("");
                new HomePage(matchedOwner).show(); 
                window.close();                    
            } else {
                loginErrorLbl.setText("Invalid Email or Password.");
            }
        });
    }
    
    // register screen
    private void createRegisterScene() {
        HBox header = createHeader();
        
        VBox registerForm = new VBox(12); 
        registerForm.setAlignment(Pos.CENTER);
        registerForm.setMaxWidth(450);
        registerForm.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-padding: 25px;");
        
        Label titleLabel = new Label("Create an account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #000000; -fx-padding: 0 0 10 0;");
        
        // full name
        VBox nameBox = new VBox(5);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        Label nameLabel = new Label("Full Name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        nameField.setPrefHeight(40);
        nameField.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC; -fx-background-radius: 4px; -fx-border-radius: 4px;");
        nameBox.getChildren().addAll(nameLabel, nameField);

        // email
        VBox emailBox = new VBox(5);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField emailField = new TextField();
        emailField.setPromptText("example@mail.com");
        emailField.setPrefHeight(40);
        emailField.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC; -fx-background-radius: 4px; -fx-border-radius: 4px;");
        emailBox.getChildren().addAll(emailLabel, emailField);
        
        // phone num
        VBox phoneBox = new VBox(5);
        phoneBox.setAlignment(Pos.CENTER_LEFT);
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        TextField phoneField = new TextField();
        phoneField.setPromptText("e.g. 0123456789");
        phoneField.setPrefHeight(40);
        phoneField.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC; -fx-background-radius: 4px; -fx-border-radius: 4px;");
        phoneBox.getChildren().addAll(phoneLabel, phoneField);
        
        // password
        VBox regPassBox = new VBox(5);
        regPassBox.setAlignment(Pos.CENTER_LEFT);
        Label regPassLabel = new Label("Password:");
        regPassLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        PasswordField regPassField = new PasswordField();
        regPassField.setPromptText("Create a password");
        regPassField.setPrefHeight(40);
        regPassField.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: #CCCCCC; -fx-background-radius: 4px; -fx-border-radius: 4px;");
        regPassBox.getChildren().addAll(regPassLabel, regPassField);
        
        Label welcomeLabel = new Label("Welcome to FurEver 🐈");
        welcomeLabel.setFont(Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, javafx.scene.text.FontPosture.ITALIC, 14));
        
        Button submitBtn = new Button("Register");
        submitBtn.setPrefWidth(120);
        submitBtn.setPrefHeight(35);
        submitBtn.setStyle("-fx-background-color: #FBC473; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-cursor: hand;");
        
        Label regMessageLbl = new Label();
        regMessageLbl.setStyle("-fx-font-size: 12px;");

        registerForm.getChildren().addAll(titleLabel, nameBox, emailBox, phoneBox, regPassBox, welcomeLabel, submitBtn, regMessageLbl);
        
        StackPane centerContent = new StackPane();
        centerContent.setPadding(new Insets(35, 0, 35, 0));
        centerContent.setStyle("-fx-background-color: #FFFFFF;"); 
        centerContent.getChildren().add(registerForm);
        
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setCenter(centerContent);
        
        registerScene = new Scene(mainLayout, 850, 750);

        // register action
        submitBtn.setOnAction(e -> {
            String fullName = nameField.getText().trim();
            String email    = emailField.getText().trim();
            String phone    = phoneField.getText().trim(); 
            String password = regPassField.getText().trim();

            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                regMessageLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
                regMessageLbl.setText("Please fill in all fields.");
                return;
            }

            // check if email alr existed
            try (BufferedReader br = new BufferedReader(new FileReader("owners.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[2].equalsIgnoreCase(email)) {
                        regMessageLbl.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
                        regMessageLbl.setText("Email is already registered. Use another email.");
                        return;
                    }
                }
            } catch (IOException ex) {
                
            }

            // auto generated userID (USR + 4 random num)
            String generatedId = "USR" + (1000 + new Random().nextInt(9000));
            
            // store user format: ownerID, name, email, password, phone
            PetOwner newOwner = new PetOwner(fullName, generatedId, email, phone, password);
            FileHandler.saveOwner(newOwner, password); 
            
            regMessageLbl.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
            regMessageLbl.setText("Account created! Your User ID is: " + generatedId);

            // clear form
            nameField.clear();
            emailField.clear();
            phoneField.clear();
            regPassField.clear();

            // redirect to login after 3 sec upon succesful registration
            PauseTransition pause = new PauseTransition(Duration.seconds(3.0));
            pause.setOnFinished(ev -> window.setScene(loginScene));
            pause.play();
        });
    }
    
    // sync header and footer
    private HBox createHeader() {
        HBox header = new HBox(25);
        header.setPadding(new Insets(25, 40, 25, 40));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #FFFFFF;");

        Label logo = new Label("FurEver Friends");
        logo.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button btnBack = new Button("BACK");
        btnBack.setPrefWidth(80);
        btnBack.setPrefHeight(30);
        btnBack.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 4px; -fx-cursor: hand;");
        
        btnBack.setOnAction(e -> {
            if (window.getScene() == registerScene) {
                window.setScene(loginScene);
            } else {
                HomePageVisitor homePage = new HomePageVisitor();
                try {
                    homePage.start(window);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        header.getChildren().addAll(logo, spacer, btnBack);
        return header;
    }
    
    private VBox createFooter() {
        VBox footerContainer = new VBox(10);
        footerContainer.setPadding(new Insets(30, 40, 30, 40));
        footerContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #E0E0E0; -fx-border-width: 1 0 0 0;");

        HBox topFooter = new HBox();
        Label footerLogo = new Label("FurEver Friends");
        footerLogo.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(5);
        grid.add(new Label("About"), 0, 0);
        grid.add(new Label("Terms"), 1, 0);
        grid.add(new Label("Address"), 2, 0);
        
        for(int i=0; i<3; i++) {
            for(int j=1; j<=2; j++) {
                Label pageLbl = new Label("Page");
                pageLbl.setStyle("-fx-text-fill: #777777; -fx-font-size: 11px;");
                grid.add(pageLbl, i, j);
            }
        }
        
        topFooter.getChildren().addAll(footerLogo, spacer, grid);
        
        Label socialMockup = new Label("🌐  🔗  📺  📸");
        socialMockup.setStyle("-fx-text-fill: #888888; -fx-font-size: 12px;");

        footerContainer.getChildren().addAll(topFooter, socialMockup);
        return footerContainer;
    }
}
