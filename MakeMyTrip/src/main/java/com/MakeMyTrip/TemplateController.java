package com.MakeMyTrip;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginView(){
        return "login";
    }

    @GetMapping("admin")
    public String getCourses(){
        return "admin-index";
    }

    @GetMapping("admin/models/add")
    public String getAddModelView(Model model1){
        com.MakeMyTrip.Model model =  new com.MakeMyTrip.Model();
        model1.addAttribute(model);
        
        return "admin-models-add";
    }
}
