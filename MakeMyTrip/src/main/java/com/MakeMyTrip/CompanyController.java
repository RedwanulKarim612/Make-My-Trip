package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    CompanyDAO companyDAO;

    @GetMapping(path = "admin/companies")
    public List<Company> getAllCompanies(){
        return companyDAO.getAllCompanies();
    }

    @GetMapping(path = "admin/companies/{companyId}")
    public Company getCompanyById(@PathVariable String companyId){
        return companyDAO.getCompanyById(companyId);
    }

    @PostMapping(path = "admin/companies")
    public boolean addCompany(@RequestBody Company company){
        return companyDAO.addCompany(company);
    }

}
