package com.baykris.tr_crypto;

public class UserData {
    private double bnb;
    private double btc;
    private String email;
    private double eth;
    private String fullname;
    private double husd;
    private double omg;
    private String password;
    private String phone;
    private String username;
    private double wallet;


    public UserData(){

    }

    public UserData(double bnb, double btc, String email, double eth, String fullname, double husd, double omg, String password , String phone, String username, double wallet){
        this.bnb = bnb;
        this.btc = btc;
        this.email = email;
        this.eth = eth;
        this.fullname = fullname;
        this.husd = husd;
        this.omg = omg;
        this.password = password;
        this.phone = phone;
        this.username = username;
        this.wallet = wallet;
    }

    public double getBnb() {
        return bnb;
    }

    public void setbnb(double bnb) {
        this.bnb = bnb;
    }

    public double getBtc() {
        return btc;
    }

    public void setBtc(double btc) {
        this.btc = btc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getEth() {
        return eth;
    }

    public void setEth(double eth) {
        this.eth = eth;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public double getHusd() {
        return husd;
    }

    public void setHusd(double husd) {
        this.husd = husd;
    }

    public double getOmg() {
        return omg;
    }

    public void setOmg(double omg) {
        this.omg = omg;
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
