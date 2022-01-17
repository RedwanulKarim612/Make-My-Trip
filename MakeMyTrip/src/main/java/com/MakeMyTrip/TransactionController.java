package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TransactionController {
    @Autowired
    TransactionDAO transactionDAO;

    @RequestMapping("admin/transactions")
    @PostMapping(value = "admin/transactions", params = "action=reset")
    public ModelAndView getAllTransaction(){
        ModelAndView modelAndView = new ModelAndView("admin-transactions");
        modelAndView.addObject("transactions", transactionDAO.getAllTransaction());
        return modelAndView;
    }

    @GetMapping(path = "/admin/transactions/{transactionId}")
    public ModelAndView getTransactionById(@PathVariable String transactionId){
        ModelAndView modelAndView = new ModelAndView("admin-transaction-single");
        try{
            Transaction a = transactionDAO.getTransactionById(transactionId);
            modelAndView.addObject("transaction",transactionDAO.getTransactionById(transactionId));
            return modelAndView;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @PostMapping(path = "/admin/transactions/add", params = "action=add")
    public ModelAndView addTransaction(@ModelAttribute ("transaction") Transaction transaction){
        try{
            transactionDAO.addTransaction(transaction);
            return new ModelAndView("redirect:/admin/transactions/" + transaction.getTransactionId());
        }
        catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/admin/transactions/");
            //duplicate primary key
        }
    }

    @PostMapping(path = "/admin/transactions/add",params = "action=cancel")
    public ModelAndView cancelAdd(){
        return new ModelAndView("redirect:/admin/transactions/");
    }

    @PostMapping(path = "admin/transactions", params = "action=search")
    public ModelAndView searchTransaction(@RequestParam String transactionId){

        ModelAndView modelAndView = new ModelAndView("admin-transactions");
        try{
            modelAndView.addObject("transactions", transactionDAO.getTransactionById(transactionId));
        }
        catch (EmptyResultDataAccessException e){
            //no model;
        }
        return modelAndView;
    }
}
