/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

public class Pet {
    private String petID;
    private String name;
    private String species;
    private String breed;
    private int age;
    private String gender;
    private String healthStatus;
    private String adoptionStatus;
    private String ownerID;

    public Pet(String petID, String name, String species, String breed,
               int age, String gender, String healthStatus, String adoptionStatus, String ownerID) {
        this.petID = petID;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.healthStatus = healthStatus;
        this.adoptionStatus = adoptionStatus;
        this.ownerID = ownerID;
    }
    
    public String getPetID() {
        return petID;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public String getAdoptionStatus() {
        return adoptionStatus;
    }
    
    public String getOwnerID() {
        return ownerID;
    }
   
    
    public void setPetID(String petID) {
        this.petID = petID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setAdoptionStatus(String adoptionStatus) {
        this.adoptionStatus =  adoptionStatus;
    }
    
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
   
}
