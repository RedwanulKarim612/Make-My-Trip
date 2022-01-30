package com.MakeMyTrip;

public class Admin {
    private String adminId;
    private String password;
    private String email;

    public Admin() {
    }

    public Admin(String adminId, String password, String email) {
        this.adminId = adminId;
        this.password = password;
        this.email = email;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
