package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.List;

@RestController
public class VehicleController {
    @Autowired
    VehicleDAO vehicleDAO;

    @RequestMapping("/admin/vehicles")
    @PostMapping(path = "/admin/vehicles" , params = "action=reset")
    public ModelAndView getAllVehicles(){
        ModelAndView modelAndView = new ModelAndView("admin-vehicles");
        try{
            modelAndView.addObject("vehicles", vehicleDAO.getAllVehicles());
            return modelAndView;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @GetMapping("/admin/vehicles/{vehicleId}")
    public ModelAndView getVehicleById(@PathVariable String vehicleId){
        try{
            ModelAndView modelAndView = new ModelAndView("admin-vehicle-single");
            modelAndView.addObject("vehicle",vehicleDAO.getVehicleByIdInfo(vehicleId).get(0));
            return modelAndView;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @PostMapping(value = "/admin/vehicles/add", params = "action=add")
    public ModelAndView addVehicle(@ModelAttribute("vehicle") Vehicle vehicle){
        try {
            vehicleDAO.addVehicle(vehicle);
            return new ModelAndView("redirect:/admin/vehicles/" + vehicle.getVehicleId());
        } catch (DataIntegrityViolationException | ParseException e){
            return new ModelAndView("redirect:/admin/vehicles");
        }
    }

    @PostMapping(value = "admin/vehicles/add", params = "action=cancel")
    public ModelAndView cancelAdd(){
        return new ModelAndView("redirect:/admin/vehicles");
    }

    @PostMapping(path = "admin/vehicles", params = "action=search")
    public ModelAndView searchVehicle(@RequestParam String vehicleId){
        ModelAndView modelAndView = new ModelAndView("admin-vehicles");
        try{
            modelAndView.addObject("vehicles", vehicleDAO.getVehicleByIdInfo(vehicleId));
        }
        catch (EmptyResultDataAccessException e){

        }
        return modelAndView;
    }


    @PostMapping(value = "/admin/vehicles/{vehicleId}" , params = "action=delete")
    public ModelAndView deleteVehicle(@PathVariable String vehicleId){
        try{
            vehicleDAO.deleteVehicle(vehicleId);
        }
        catch (DataIntegrityViolationException e){
        }

        return new ModelAndView("redirect:/admin/vehicles");
    }

    @RequestMapping("/admin/vehicles/{vehicleId}/edit")
    public ModelAndView getEditPage(@PathVariable String vehicleId){
        ModelAndView modelAndView = new ModelAndView("admin-vehicle-edit");
        modelAndView.addObject("vehicle",vehicleDAO.getVehicleById(vehicleId));
        return modelAndView;
    }

    @PostMapping(path = "/admin/vehicles/{vehicleId}/edit", params = "action=save")
    public ModelAndView editVehicle(@PathVariable String vehicleId,Vehicle vehicle){
        try{
            vehicleDAO.editVehicle(vehicleId, vehicle);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
        }
        return new ModelAndView("redirect:/admin/vehicles/" + vehicleId);
    }
    @PostMapping(path = "/admin/vehicles/{vehicleId}/edit", params = "action=cancel")
    public ModelAndView cancelEdit(@PathVariable String vehicleId){
        return new ModelAndView("redirect:/admin/vehicles/" + vehicleId );
    }


}
