package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryDAO countryDAO;

    @GetMapping(path = "/admin/countries")
    public List<Country> getAllCountries(){
        return countryDAO.getAllCountries();
    }

    @GetMapping(path = "/admin/countries/{countryId}")
    public Country getCountryById(@PathVariable String countryId){
        try{
            return countryDAO.getCountryById(countryId);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @PostMapping(path = "/admin/countries")
    public void addCountry(@RequestBody Country country){
        try{
            countryDAO.addCountry(country);
        }
        catch (DataIntegrityViolationException e){
            //duplicate primary key
        }
    }

    @DeleteMapping(path = "/admin/countries/{countryId}")
    public void deleteCountry(@PathVariable String countryId){
        try{
            countryDAO.deleteCountry(countryId);
        }
        catch (DataIntegrityViolationException e){
            //cannot delete country, linked with city
        }
    }

    @PutMapping(path = "/admin/countries/{countryId}")
    public void editCountry(@PathVariable String countryId,@RequestBody Country country){
        try{
            countryDAO.editCountry(countryId, country);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
        }
    }


}
