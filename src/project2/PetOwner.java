/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

public class PetOwner extends User {

    //constructor
    //phone and password default to empty string when not available (e.g. legacy login paths)
    public PetOwner(String name, String ownerID, String ownerEmail){
        super(name, ownerID, ownerEmail, "", "");
    }

    //full constructor, used after login when all 5 fields are available from owners.txt
    public PetOwner(String name, String ownerID, String ownerEmail, String phone, String password){
        super(name, ownerID, ownerEmail, phone, password);
    }

    // Getters and Setters
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
