package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CityController {
    @Autowired
    CityDAO cityDAO;

    @RequestMapping("admin/cities")
    @PostMapping(path = "admin/cities" , params = "action=reset")

    public ModelAndView getAllCities(){
        ModelAndView modelAndView = new ModelAndView("admin-cities");
        try{
            modelAndView.addObject("cities", cityDAO.getAllCitiesWithCountry());
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }


    @GetMapping(path = "admin/cities/{cityId}")
    public ModelAndView getCityById(@PathVariable String cityId){
        ModelAndView modelAndView = new ModelAndView();
        try{
            modelAndView.addObject("city", cityDAO.getCityById(cityId));
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }

    @PostMapping(path = "admin/cities/add")
    public String addCity(@RequestBody City city){
        try{
            cityDAO.addCity(city);
            return "success";
        }
        catch (DataIntegrityViolationException e){
            return "failed";
        }
    }

    @PostMapping(path = "admin/cities" ,params = "action=search")
    public ModelAndView searchCity(@RequestParam String cityId){
        ModelAndView modelAndView = new ModelAndView("admin-cities");
        try{
            modelAndView.addObject("cities",cityDAO.getCityById(cityId));
        }
        catch (EmptyResultDataAccessException e){
            //no model;
        }
        return modelAndView;
    }

    @PutMapping(path = "admin/cities/{cityId}")
    public String editCity(@PathVariable String cityId, @RequestBody City city){
        try{
            cityDAO.editCity(cityId, city);
            return "success";
        }
        catch (DataIntegrityViolationException e){
            return "failed";
        }
    }

    @DeleteMapping(path = "admin/cities/{cityId}")
    public String deleteCity(@PathVariable String cityId){
        try{
            cityDAO.deleteCity(cityId);
            return "success";
        }
        catch (DataIntegrityViolationException e){
            return "failed";
        }
    }
}
