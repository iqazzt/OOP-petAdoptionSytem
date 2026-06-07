/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

public class PetOwner {
    private String name;
    private String ownerID;
    private String ownerEmail;
    
    //constructor
    public PetOwner(String name, String ownerID, String ownerEmail){
        this.name = name;
        this.ownerID = ownerID;
        this.ownerEmail = ownerEmail;
    }
    
    //Getters and Setters
    public String getName() {return name;}
    public String getOwnerID() {return ownerID;}
    public String getOwnerEmail() {return ownerEmail;}
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
