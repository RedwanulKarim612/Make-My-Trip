package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class AdminDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize() {
        setDataSource(dataSource);
    }

    public Admin getAdminByIdForAuth(String adminId){
        String sql = "SELECT admin_id,password " +
                "FROM admin " +
                "WHERE admin_id = ?";
        return getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Admin.class),adminId);
    }

    public Admin getAdminInfo(){
        String sql = "SELECT * FROM ADMIN WHERE admin_id = ?";
        return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Admin.class),
                SecurityContextHolder.getContext().getAuthentication().getName());

    }

}
