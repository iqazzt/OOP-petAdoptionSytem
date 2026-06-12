/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

/**
 *
 * @author HP
 */
public class User {
    
    private String name;
    private String userID;
    private String email;
    private String phoneNum;
    private String password;

    // constructor
    public User(String name, String userID, String email, String phone, String password) {
        this.name = name;
        this.userID = userID;
        this.email = email;
        this.phoneNum = phone;
        this.password = password;
    }

    // full name
    public String getFullName() { return name; }
    public void setFullName(String name) { this.name = name; }

    public String getUserID() { return userID; }

    // email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // phone number
    public String getPhoneNum() { return phoneNum; }
    public void setPhoneNum(String phone) { this.phoneNum = phone; }

    // password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
