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

    // ── Pet ID generator ──────────────────────────────────────────
    // Counts existing pets and returns P101, P102, P103 ...
    public static String generatePetId() {
        int count = 101;
        try (BufferedReader br = new BufferedReader(new FileReader("pets.txt"))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            // File doesn't exist yet — start from P101
        }
        return "P" + count;
    }

    // ── Save one pet (append) ─────────────────────────────────────
    // Format: petID,name,type,breed,age,gender,healthStatus,adoptionStatus,ownerID,imagePath
    public static void savePet(Pet pet) {
        try (FileWriter fw = new FileWriter("pets.txt", true)) {
            fw.write(
                pet.getPetID()          + "," +
                pet.getName()           + "," +
                pet.getType()        + "," +
                pet.getBreed()          + "," +
                pet.getAge()            + "," +
                pet.getGender()         + "," +
                pet.getHealthStatus()   + "," +
                pet.getAdoptionStatus() + "," +
                pet.getOwnerID()        + "," +
                pet.getImagePath()      + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error adding pet: " + e.getMessage());
        }
    }

    // ── Save all pets (overwrite entire file) ─────────────────────
    public static void saveAllPets(ArrayList<Pet> petlist) {
        try (FileWriter fw = new FileWriter("pets.txt", false)) {
            for (Pet pet : petlist) {
                fw.write(
                    pet.getPetID()          + "," +
                    pet.getName()           + "," +
                    pet.getType()        + "," +
                    pet.getBreed()          + "," +
                    pet.getAge()            + "," +
                    pet.getGender()         + "," +
                    pet.getHealthStatus()   + "," +
                    pet.getAdoptionStatus() + "," +
                    pet.getOwnerID()        + "," +
                    pet.getImagePath()      + "\n"
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // ── Save owner (no password — backward compat) ────────────────
    public static void saveOwner(PetOwner owner) {
        saveOwner(owner, "");
    }

    // ── Save owner WITH password ──────────────────────────────────
    // Format: ownerID,name,email,password,phone
    public static void saveOwner(PetOwner owner, String password) {
        try (FileWriter fw = new FileWriter("owners.txt", true)) {
            fw.write(
                owner.getOwnerID()    + "," +
                owner.getName()       + "," +
                owner.getOwnerEmail() + "," +
                password              + "," +
                owner.getPhoneNum()   + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error saving owner: " + e.getMessage());
        }
    }

    // ── Load all pets ─────────────────────────────────────────────
    // Handles both old format (9 fields) and new format (10 fields with imagePath)
    public static ArrayList<Pet> loadPets() {
        ArrayList<Pet> petList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("pets.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 9) continue; // skip malformed lines

                String imagePath = (data.length >= 10) ? data[9] : ""; // optional 10th field

                Pet pet = new Pet(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    Integer.parseInt(data[4]),
                    data[5],
                    data[6],
                    data[7],
                    data[8],
                    imagePath
                );
                petList.add(pet);
            }
        } catch (IOException e) {
            System.out.println("Error loading pets: " + e.getMessage());
        }
        return petList;
    }

    // ── Load pets by owner ────────────────────────────────────────
    public static ArrayList<Pet> loadPetsByOwner(String ownerID) {
        ArrayList<Pet> ownerPets = new ArrayList<>();
        for (Pet pet : loadPets()) {
            if (pet.getOwnerID().equals(ownerID)) {
                ownerPets.add(pet);
            }
        }
        return ownerPets;
    }

    // ── Application methods ───────────────────────────────────────
    // Format: appId|applicantId|petId|email|description|status|date|comment

    // Generate unique application ID: AP101, AP102 ...
    public static String generateApplicationId() {
        int count = 101;
        try (BufferedReader br = new BufferedReader(new FileReader("applications.txt"))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return "AP" + count;
    }

    // Append one application
    public static void saveApplication(String[] parts) {
        try (FileWriter fw = new FileWriter("applications.txt", true)) {
            fw.write(String.join("|", parts) + "\n");
        } catch (IOException e) {
            System.out.println("Error saving application: " + e.getMessage());
        }
    }

    // Load all applications
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

    // Update application status and comment by appId
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
