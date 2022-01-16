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

    @GetMapping("admin/models/add")
    public String getAddModelView(Model model1){
        com.MakeMyTrip.Model model =  new com.MakeMyTrip.Model();
        model1.addAttribute(model);
        return "admin-models-add";
    }
    @GetMapping("admin/countries/add")
    public String getAddCountryView(Model model){
        Country country = new Country();
        model.addAttribute(country);
        return "admin-countries-add";
    }

    @GetMapping("admin/companies/add")
    public String getAddCompanyAddView(Model model){
        Company company = new Company();
        model.addAttribute(company);
        return "admin-companies-add";
    }

    @GetMapping("admin/transactions/add")
    public String getAddTransactionView(Model model){
        Transaction transaction = new Transaction();
        model.addAttribute(transaction);
        return "admin-transactions-add";
    }

}
