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
}
