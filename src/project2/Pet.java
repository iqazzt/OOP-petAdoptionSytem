/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

public class Pet {
    private String petID;
    private String name;
    private String type;   //
    private String breed;
    private int age;
    private String gender;
    private String healthStatus;
    private String adoptionStatus;
    private String ownerID;
    private String imagePath; // path to uploaded image, empty string if none

    // Full constructor including imagePath
    public Pet(String petID, String name, String type, String breed,
               int age, String gender, String healthStatus,
               String adoptionStatus, String ownerID, String imagePath) {
        this.petID         = petID;
        this.name          = name;
        this.type       = type;
        this.breed         = breed;
        this.age           = age;
        this.gender        = gender;
        this.healthStatus  = healthStatus;
        this.adoptionStatus = adoptionStatus;
        this.ownerID       = ownerID;
        this.imagePath     = imagePath;
    }

    // Backward-compatible constructor (no imagePath — defaults to empty)
    public Pet(String petID, String name, String type, String breed,
               int age, String gender, String healthStatus,
               String adoptionStatus, String ownerID) {
        this(petID, name, type, breed, age, gender,
             healthStatus, adoptionStatus, ownerID, "");
    }

    // Getters
    public String getPetID()           { return petID; }
    public String getName()            { return name; }
    public String getType()         { return type; }
    public String getBreed()           { return breed; }
    public int    getAge()             { return age; }
    public String getGender()          { return gender; }
    public String getHealthStatus()    { return healthStatus; }
    public String getAdoptionStatus()  { return adoptionStatus; }
    public String getOwnerID()         { return ownerID; }
    public String getImagePath()       { return imagePath; }

    // Setters
    public void setPetID(String petID)                   { this.petID = petID; }
    public void setName(String name)                     { this.name = name; }
    public void setType(String type)               { this.type = type; }
    public void setBreed(String breed)                   { this.breed = breed; }
    public void setAge(int age)                          { this.age = age; }
    public void setGender(String gender)                 { this.gender = gender; }
    public void setHealthStatus(String healthStatus)     { this.healthStatus = healthStatus; }
    public void setAdoptionStatus(String adoptionStatus) { this.adoptionStatus = adoptionStatus; }
    public void setOwnerID(String ownerID)               { this.ownerID = ownerID; }
    public void setImagePath(String imagePath)           { this.imagePath = imagePath; }
}
