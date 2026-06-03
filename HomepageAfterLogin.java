/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomepageAfterLogin extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label lblTitle = new Label("PET ADOPTION SYSTEM");
        lblTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        Label lblUser = new Label("Username:");
        TextField txtUser = new TextField();

        Label lblPass = new Label("Password:");
        PasswordField txtPass = new PasswordField();

        Button btnLogin = new Button("Login");
        Label lblMessage = new Label();

        btnLogin.setOnAction(e -> {

            String username = txtUser.getText();
            String password = txtPass.getText();

            if(username.equals("admin") && password.equals("1234")) {

                showHomePage();

                primaryStage.close();

            } else {

                lblMessage.setText("Invalid Username or Password");
            }
        });

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(
                lblTitle,
                lblUser,
                txtUser,
                lblPass,
                txtPass,
                btnLogin,
                lblMessage
        );

        Scene scene = new Scene(root, 350, 300);

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showHomePage() {

        Stage homeStage = new Stage();

        Label title = new Label("PET ADOPTION SYSTEM");
        title.setStyle("-fx-font-size:24px; -fx-font-weight:bold;");

        Label welcome = new Label("Welcome Admin!");

        Button btnViewPets = new Button("View Pets");
        Button btnAddPet = new Button("Add New Pet");
        Button btnManageAdoption = new Button("Manage Adoption");
        Button btnLogout = new Button("Logout");

        btnViewPets.setPrefWidth(200);
        btnAddPet.setPrefWidth(200);
        btnManageAdoption.setPrefWidth(200);
        btnLogout.setPrefWidth(200);

        TextArea output = new TextArea();
        output.setPrefHeight(150);

        btnViewPets.setOnAction(e ->
                output.setText(
                        "Pet List\n\n" +
                        "P001 - Luna (Cat)\n" +
                        "P002 - Max (Dog)\n" +
                        "P003 - Coco (Rabbit)"
                )
        );

        btnAddPet.setOnAction(e ->
                output.setText(
                        "Add New Pet Function Selected"
                )
        );

        btnManageAdoption.setOnAction(e ->
                output.setText(
                        "Manage Adoption Function Selected"
                )
        );

        btnLogout.setOnAction(e ->
                homeStage.close()
        );

        VBox homeRoot = new VBox(15);
        homeRoot.setAlignment(Pos.CENTER);
        homeRoot.setPadding(new Insets(20));

        homeRoot.getChildren().addAll(
                title,
                welcome,
                btnViewPets,
                btnAddPet,
                btnManageAdoption,
                btnLogout,
                output
        );

        Scene homeScene = new Scene(homeRoot, 500, 500);

        homeStage.setTitle("Home Page");
        homeStage.setScene(homeScene);
        homeStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}