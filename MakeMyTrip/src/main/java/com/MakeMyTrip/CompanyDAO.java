package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class CompanyDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }

    public boolean companyExists(String companyId){
        String sql = "SELECT COUNT(*) FROM company WHERE company_id = ?";
        return getJdbcTemplate().queryForObject(sql,Integer.class, companyId)==1;
    }

    public Company getCompanyById(String companyId) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM company WHERE company_id = ?";
        return  getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Company.class),companyId);
    }

    public List<Company> getAllCompanies() {
        String sql = "SELECT * FROM company";
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Company.class));
    }

    public void addCompany(Company company) throws DuplicateKeyException {
        String sql = "INSERT INTO company VALUES(?,?)";
        getJdbcTemplate().update(sql,company.getCompanyId(),company.getCompanyName());

    }
}
