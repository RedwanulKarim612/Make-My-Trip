package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try{
            return companyDAO.getCompanyById(companyId);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @PostMapping(path = "admin/companies")
    public String addCompany(@RequestBody Company company){
        try{
            companyDAO.addCompany(company);
            return "success";
        }
        catch (DuplicateKeyException e){
            return "duplicate company id";
        }
    }

}
