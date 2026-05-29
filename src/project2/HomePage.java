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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//question to standby: can we just use VBox ?

public class HomePage {
    private Stage stage;
    private PetOwner currentOwner;
    
    public HomePage(PetOwner currentOwner) {
        this.currentOwner = currentOwner;
        stage = new Stage();
        
        BorderPane layout = new BorderPane();
        
         //---Navigation bar
        HBox navbar = new HBox();
        navbar.setSpacing(20);
        navbar.setPadding(new Insets(10));
        navbar.setAlignment(Pos.TOP_RIGHT);

        Label homeLbl = new Label("Home");
        Label myPetLbl = new Label("My Pet");
        Label newAppLbl = new Label("New Application");
        Label appHistLbl = new Label("Application History");
        Label manageAppLbl = new Label("Manage Application");

        navbar.getChildren().addAll(homeLbl, myPetLbl, newAppLbl, appHistLbl, manageAppLbl);
        layout.setTop(navbar);
        
        
        Text homeText = new Text("HOME PAGE");
        homeText.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        layout.setCenter(homeText);
        
        //---Add pet button
        Button addpetBtn = new Button("Add Pet"); 
        
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setPadding(new Insets(10));
        bottom.getChildren().add(addpetBtn);
        layout.setBottom(bottom);

        
        //---Button n Label functionality
        addpetBtn.setOnAction(e -> {
            new AddPet(currentOwner).show();
            stage.close();
        });
        
        myPetLbl.setOnMouseClicked(e -> {
            new MyPets(currentOwner).show();
            stage.close();
        });
        
       
        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Homepage");
    }
    
    public void show(){
        stage.show();
    }
    
}

