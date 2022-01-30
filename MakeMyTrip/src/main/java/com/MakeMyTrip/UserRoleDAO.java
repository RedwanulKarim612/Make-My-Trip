package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Repository
public class UserRoleDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize() {
        setDataSource(dataSource);
    }

    public String getRoleById(String userId) throws EmptyResultDataAccessException {
        String sql = "SELECT role_name from userrole WHERE userrole_id = ?";
        return getJdbcTemplate().queryForObject(sql,String.class,userId);
    }
}
