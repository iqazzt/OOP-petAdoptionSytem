/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

public class PetOwner extends User {

    //constructor
    public PetOwner(String name, String ownerID, String ownerEmail, String phone, String password){
        super(name, ownerID, ownerEmail, phone, password);
    }

    //getters and setters
    public String getName() { return getFullName(); }
    public String getOwnerID() { return getUserID(); }
    public String getOwnerEmail() { return getEmail(); }

    public void setName(String name) {
        setFullName(name);
    }

    public void setOwnerEmail(String ownerEmail) {
        setEmail(ownerEmail);
    }
}
