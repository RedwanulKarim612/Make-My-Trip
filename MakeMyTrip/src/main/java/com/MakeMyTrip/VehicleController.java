package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.List;

@RestController
public class VehicleController {
    @Autowired
    VehicleDAO vehicleDAO;

    @GetMapping("/admin/vehicles")
    public List<Vehicle> getAllVehicles(){
        try{
            return vehicleDAO.getAllVehicles();
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @GetMapping("/admin/vehicles/{vehicleId}")
    public Vehicle getVehicleById(@PathVariable String vehicleId){
        try{
            return vehicleDAO.getVehicleById(vehicleId);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @PostMapping("/admin/vehicles")
    public String addVehicle(@RequestBody Vehicle vehicle){
        try {
            vehicleDAO.addVehicle(vehicle);
            return "success";
        }
        catch (DuplicateKeyException e){
            return "duplicate vehicle id";
        }
        catch (DataIntegrityViolationException e) {
            System.out.println("Invalid modelId or companyId");
            return "invalid modelId or companyId";
        }
        catch (ParseException e){
            return "invalid date format";
        }
    }

    @DeleteMapping("/admin/vehicles/{vehicleId}")
    public String deleteVehicle(@PathVariable String vehicleId){
        try{
            vehicleDAO.deleteVehicle(vehicleId);
            return "success";
        }
        catch (DataIntegrityViolationException e){
            return "cannot delete vehicle";
        }
    }

    @PutMapping("/admin/vehicles/{vehicleId}")
    public void editVehicle(@PathVariable String vehicleId, Vehicle vehicle){
        try{
            vehicleDAO.editVehicle(vehicleId, vehicle);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
        }
    }
}
