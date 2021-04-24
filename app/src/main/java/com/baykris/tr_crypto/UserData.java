package com.baykris.tr_crypto;

public class UserData {
    private String email;
    private String fullname;
    private String password;
    private String phone;
    private String username;
    private double wallet;
    public UserData(){

    }

    public UserData(String email, String fullname, String password , String phone, String username, double wallet){
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.wallet = wallet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }
}
