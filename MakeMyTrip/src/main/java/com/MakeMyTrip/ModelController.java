package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ModelController {
    @Autowired
    ModelDAO modelDAO;

    @RequestMapping({"admin/models","company/models"})
    @PostMapping(path = {"admin/models","company/models"} , params = "action=reset")
    public ModelAndView getAllModels() {
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) modelAndView.setViewName("admin-models");
        else modelAndView.setViewName("company-models");
        modelAndView.addObject("models",modelDAO.getAllModels());
        return modelAndView;

    }

    @RequestMapping("admin/models/{modelId}")
    public ModelAndView getModelById(@PathVariable String modelId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-model-single");

        try{
            Model model = modelDAO.getModelById(modelId);
            modelAndView.addObject("model",model);
        }
        catch (EmptyResultDataAccessException e){

            //no model found by id
        }
        return modelAndView;
    }




    @PostMapping(path = "/admin/models/add",params = "action=add")
    public ModelAndView addModel(@ModelAttribute ("model") Model model){
//        System.out.println(model.getModelId());
        try{
            modelDAO.addModel(model);
            return new ModelAndView("redirect:/admin/models/" + model.getModelId());
        } catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/admin/models");
        }
    }
    @PostMapping(path = "/admin/models/add",params = "action=cancel")
    public ModelAndView cancelAdd(){
        return new ModelAndView("redirect:/admin/models");
    }



    @PostMapping(path = {"admin/models","company/models"}, params = "action=search")
    public ModelAndView searchModel(@RequestParam String modelId){
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) modelAndView.setViewName("admin-models");
        else modelAndView.setViewName("company-models");
        try{
            modelAndView.addObject("models",modelDAO.getModelById(modelId));
        }
        catch (EmptyResultDataAccessException e){
            //no model;
        }
        return modelAndView;
    }

    @PostMapping(path = "admin/models/{modelId}", params = "action=delete")
    public ModelAndView deleteModel(@PathVariable String modelId){
        try{
            modelDAO.deleteModel(modelId);
            return new ModelAndView("redirect:/admin/models");
        }
        catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/admin/models/" + modelId);
        }
    }

    @RequestMapping("/admin/models/{modelId}/edit")
    public ModelAndView getEditPage(@PathVariable String modelId){
        ModelAndView modelAndView = new ModelAndView("admin-model-edit");
        modelAndView.addObject("model",modelDAO.getModelById(modelId));
        return modelAndView;
    }


    @PostMapping(path = "/admin/models/{modelId}/edit", params = "action=save")
    public ModelAndView editModel(@PathVariable String modelId, Model model){
        try{
            modelDAO.editModel(modelId, model);
            return new ModelAndView("redirect:/admin/models/" + modelId);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
            return new ModelAndView("redirect:/admin/models/" + modelId);
        }
    }
    @PostMapping(path = "/admin/models/{modelId}/edit", params = "action=cancel")
    public ModelAndView cancelEdit(@PathVariable String modelId){
        return new ModelAndView("redirect:/admin/models/" + modelId);
    }



}
