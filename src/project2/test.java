/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project2;

import javafx.application.Application;
import javafx.stage.Stage;


public class test extends Application{
    @Override
    public void start(Stage primaryStage) {
        
        // Dummy owner for testing
        PetOwner owner = new PetOwner(
                "Afiqah",
                "O001",
                "afiqah@gmail.com"
        );
        
        FileHandler.saveOwner(owner);
        
        // Start system with dummy owner
        new HomePage(owner).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
