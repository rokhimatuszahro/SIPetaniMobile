package com.mobile.sipetani.Model;

public class Registrasi_model {
    String email, username, password, alamat, jenkel;
    int pin, no;
    public Registrasi_model(){}

    public Registrasi_model(String email, String username, String password, int pin, int no, String alamat, String jenkel) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.pin = pin;
        this.no = no;
        this.alamat = alamat;
        this.jenkel = jenkel;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPin() { return pin; }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getAlamt() {
        return alamat;
    }

    public void setAlamat(String password) {
        this.alamat = alamat;
    }

    public String getJenkel() {
        return jenkel;
    }

    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }
}
