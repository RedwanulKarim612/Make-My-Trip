package com.MakeMyTrip;

public class Company {
    private String companyId;
    private String companyName;
    private String password;

    public Company(String companyId, String companyName, String password) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.password = password;
    }

    public Company() {
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

}
