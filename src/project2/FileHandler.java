/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    
    // Append one new pet used by AddPet
    public static void savePet(Pet pet) {
        try (FileWriter fw = new FileWriter("pets.txt", true)) {
            fw.write(
                pet.getPetID()       + "," +
                pet.getName()        + "," +
                pet.getSpecies()     + "," +
                pet.getBreed()       + "," +
                pet.getAge()         + "," +
                pet.getGender()      + "," +
                pet.getHealthStatus()+ "," +
                pet.getAdoptionStatus()        + "," +
                pet.getOwnerID()     + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error adding pet: " + e.getMessage());
        }
    }

    // Overwrite entire file used by MyPets after edit or delete
    public static void saveAllPets(ArrayList<Pet> petlist) {
        try (FileWriter fw = new FileWriter("pets.txt", false)) {
            for (Pet pet : petlist) {
                fw.write(
                    pet.getPetID()       + "," +
                    pet.getName()        + "," +
                    pet.getSpecies()     + "," +
                    pet.getBreed()       + "," +
                    pet.getAge()         + "," +
                    pet.getGender()      + "," +
                    pet.getHealthStatus()+ "," +
                    pet.getAdoptionStatus()        + "," +
                    pet.getOwnerID()     + "\n"
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
    
    //save owner info
    public static void saveOwner(PetOwner owner){
        try{
            
            FileWriter fw = new FileWriter("owners.txt", true);

            fw.write(
                owner.getOwnerID() + "," +
                owner.getName() + "," +
                owner.getOwnerEmail() + "\n"
            );

        } catch (IOException e) {

            System.out.println("Error saving owner: " + e.getMessage());
        }
    }
    
    //load all pets
    public static ArrayList<Pet> loadPets(){
        ArrayList<Pet> petList = new ArrayList<>();
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("pets.txt"));
            String line;

            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                Pet pet = new Pet( data[0], 
                        data[1],
                        data[2],
                        data[3],
                        Integer.parseInt(data[4]),
                        data[5],
                        data[6],
                        data[7],
                        data[8]
                );
                
                petList.add(pet);
            }  
        }catch(IOException e){
            System.out.println("Error loading pets: " + e.getMessage());
        }
        
        return petList;
    }
    
    //load pets by owner
    public static ArrayList<Pet> loadPetsByOwner(String ownerID){
        ArrayList<Pet> ownerPets = new ArrayList<>();
        ArrayList<Pet> allPets = loadPets();
        
        for (Pet pet : allPets){
            if (pet.getOwnerID().equals(ownerID)){
                ownerPets.add(pet);
            }
        }
        
        return ownerPets;
    }
}