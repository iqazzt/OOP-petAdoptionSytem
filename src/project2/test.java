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
        
        // Start with the landing page (visitor home page)
        // From there, clicking LOGIN navigates to login_register_page
        new HomePageVisitor().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
     
}
