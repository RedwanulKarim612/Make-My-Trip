package com.MakeMyTrip;

public class Company {
    private String companyId;
    private String companyName;

    public Company(String companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
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

    @Override
    public String toString() {
        return "Company{" +
                "companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

}
