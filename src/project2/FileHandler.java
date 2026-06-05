/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    
    // Append one new pet
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

    // Overwrite entire file
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
    
    // Save owner info (original — no password, kept for backward compatibility)
    public static void saveOwner(PetOwner owner){
        saveOwner(owner, "");
    }

    // Save owner info WITH password — called by registration
    // Format: ownerID,name,email,password
    public static void saveOwner(PetOwner owner, String password){
        try (FileWriter fw = new FileWriter("owners.txt", true)){
            fw.write(
                owner.getOwnerID()    + "," +
                owner.getName()       + "," +
                owner.getOwnerEmail() + "," +
                password              + "\n"
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

    // ── Application methods ───────────────────────────────────────
    // All application data uses pipe (|) as delimiter
    // Format: appId|applicantId|petId|email|description|status|date|comment

    // Generate a unique application ID (AP101, AP102, ...)
    public static String generateApplicationId() {
        int count = 101;
        try (BufferedReader br = new BufferedReader(new FileReader("applications.txt"))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            // File doesn't exist yet — start from AP101
        }
        return "AP" + count;
    }

    // Append one new application to applications.txt
    // parts[]: appId|applicantId|petId|email|description|status|date|comment
    public static void saveApplication(String[] parts) {
        try (FileWriter fw = new FileWriter("applications.txt", true)) {
            fw.write(String.join("|", parts) + "\n");
        } catch (IOException e) {
            System.out.println("Error saving application: " + e.getMessage());
        }
    }

    // Load all applications from applications.txt
    public static List<String[]> loadApplications() {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("applications.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 8) {
                    list.add(parts);
                }
            }
        } catch (IOException e) {
            // File not found — return empty list
        }
        return list;
    }

    // Update status and comment of an existing application by appId
    public static void updateApplication(String appId, String newStatus, String comment) {
        try {
            File file = new File("applications.txt");
            List<String> lines = Files.readAllLines(file.toPath());
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length >= 8 && parts[0].equals(appId)) {
                    parts[5] = newStatus;
                    parts[7] = comment;
                    line = String.join("|", parts);
                }
                updatedLines.add(line);
            }
            Files.write(file.toPath(), updatedLines);
        } catch (IOException e) {
            System.out.println("Error updating application: " + e.getMessage());
        }
    }
}