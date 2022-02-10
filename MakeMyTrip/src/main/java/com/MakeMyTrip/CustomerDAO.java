package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class CustomerDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize() {
        setDataSource(dataSource);
    }

    public Customer getCustomerByUsernameForAuth(String userId) throws EmptyResultDataAccessException {
        String sql = "SELECT user_id, password " +
                "FROM customer " +
                "WHERE user_id = ?";
        return (Customer) getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Customer.class),userId);

    }

    public void registerCustomer(Customer customer) throws DataIntegrityViolationException {
        String sql = "INSERT INTO customer " +
                "values(?,?,?,?)";
        getJdbcTemplate().update(sql,customer.getUserId(),customer.getPassword(),customer.getName(),customer.getEmail());
    }

    public Customer getCustomerInfo(String userId){
        System.out.println(userId);
        String sql = "SELECT * FROM customer WHERE user_id = ?";
        return (Customer) getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Customer.class),userId);

    }

    public Double getAmountInWallet(String userId){
        String sql = "SELECT amount FROM WALLET WHERE customer_id = ?";
        return getJdbcTemplate().queryForObject(sql,Double.class,userId);
    }

    public void verifyTransaction(String transactionId, String name) {
        try {
            String sql = "UPDATE TRANSACTION SET WALLET_ID = (SELECT WALLET_ID FROM WALLET WHERE CUSTOMER_ID = ?) WHERE TRANSACTION_ID = ?";
            getJdbcTemplate().update(sql, name, transactionId);
        }
        catch (Exception e){
            //
        }
    }
}
