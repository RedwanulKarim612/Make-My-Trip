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
        String sql = "SELECT company_id, company_name FROM company WHERE company_id = ?";
        return  getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Company.class),companyId);
    }

    public List<Company> getAllCompanies() {
        String sql = "SELECT company_id, company_name FROM company";
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Company.class));
    }

    public void addCompany(Company company) throws DuplicateKeyException {
        String sql = "INSERT INTO company VALUES(?,?,?)";
        getJdbcTemplate().update(sql,company.getCompanyId(),company.getCompanyName(),company.getPassword());
    }

    public void deleteCompany(String companyId) throws DataIntegrityViolationException {
        String sql = "DELETE FROM company WHERE company_id = ?";
        getJdbcTemplate().update(sql,companyId);
    }
    public void editCompany(String companyId,Company company) throws DataIntegrityViolationException{
        if(!companyId.equals(company.getCompanyId())) throw new DataIntegrityViolationException("cannot edit primary key");
        String sql = "UPDATE company SET " +
                "company_id = ?," +
                "company_name = ?" +
                "WHERE company_id = ?";
        getJdbcTemplate().update(sql,company.getCompanyId(),company.getCompanyName(),companyId);
    }

    public Company getCompanyHome(String companyId){
        String sql = "SELECT * FROM company WHERE company_id = ?";
        return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Company.class),companyId);

    }

    public Company getCompanyForAuth(String username) {
        String sql = "SELECT company_id, password FROM company WHERE company_id = ?";
        return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Company.class),username);
    }

    public Company getCompanyInfo(String name) {
//        System.out.println(name);
        String sql = "SELECT * FROM COMPANY WHERE COMPANY_ID = ?";
        return  getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Company.class),name);

    }

    public void editProfile(Company company, String name) {
        String sql = "UPDATE COMPANY SET " +
                "COMPANY_NAME = ?," +
                "PASSWORD = ? " +
                "WHERE COMPANY_ID = ?";
        getJdbcTemplate().update(sql, company.getCompanyName(),company.getPassword(), name);
    }
}
