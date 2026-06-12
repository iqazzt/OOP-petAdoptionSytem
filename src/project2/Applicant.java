/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

/**
 *
 * @author HP
 */
public class Applicant extends User {
    
    private String status;

    // constructor 
    public Applicant(String name, String userID, String email, String phone, String password, String status) {
        super(name, userID, email, phone, password);
        this.status = status;
    }

    // status
    public String getStatus() { 
        return status; 
    }
    
    // set status
    public void setStatus(String status) { 
        this.status = status; 
    }
}
    
    
    

