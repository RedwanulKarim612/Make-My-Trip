package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    AdminDAO adminDAO;
    @Autowired
    CompanyDAO companyDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try{
            String role = userRoleDAO.getRoleById(username);
            if(role.equalsIgnoreCase("CUSTOMER")) {
                Customer customer = customerDAO.getCustomerByUsernameForAuth(username);
                return new User(customer.getUserId(), customer.getPassword(), getAuthority(role));
            }
            else if(role.equalsIgnoreCase("COMPANY")){
                Company company = companyDAO.getCompanyForAuth(username);
                return new User(company.getCompanyId(), company.getPassword(), getAuthority(role));
            }
            else if(role.equalsIgnoreCase("ADMIN")){
                Admin admin = adminDAO.getAdminByIdForAuth(username);
                return new User(admin.getAdminId(), admin.getPassword(), getAuthority(role));
            }
        }
        catch (EmptyResultDataAccessException e){
            throw new UsernameNotFoundException("not found");
        }
        return null;
    }

    private ArrayList<SimpleGrantedAuthority> getAuthority(String role) {
            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
        return authorities;
    }
}
