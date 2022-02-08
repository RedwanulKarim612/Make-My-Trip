package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class VehicleDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }
//
//    public boolean vehicleExists(String vehicleId){
//        String sql = "SELECT COUNT(*) FROM vehicle WHERE vehicle_id = ?";
//        return getJdbcTemplate().queryForObject(sql,Integer.class, vehicleId)==1;
//    }

    public boolean addVehicle(Vehicle vehicle) throws  DuplicateKeyException, DataIntegrityViolationException,ParseException {
//        if(vehicleExists(vehicle.getVehicleId())) return false;
        String sql = "INSERT INTO vehicle VALUES(?,?,?,?,?)";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date today = new Date();

        Date todayWithZeroTime = null;
        todayWithZeroTime = formatter.parse(formatter.format(today));

        getJdbcTemplate().update(sql,vehicle.getVehicleId(),
                                    vehicle.getRegistrationNo(),
                                    todayWithZeroTime,
                                    vehicle.getModelId(),
                                    vehicle.getCompanyId());
        return true;
    }

    public boolean addVehicle(Vehicle vehicle, String companyId) throws DataIntegrityViolationException,ParseException{
        String sql = "INSERT INTO vehicle VALUES(?,?,?,?,?)";
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date today = new Date();

        Date todayWithZeroTime = null;
        todayWithZeroTime = formatter.parse(formatter.format(today));

        getJdbcTemplate().update(sql,vehicle.getVehicleId(),
                vehicle.getRegistrationNo(),
                todayWithZeroTime,
                vehicle.getModelId(),
                companyId);
        return true;
    }

    public List<Map<String, Object>> getAllVehicles() throws EmptyResultDataAccessException{
        String sql = "SELECT V.VEHICLE_ID, V.REGISTRATION_NO, V.DATE_ADDED, V.MODEL_ID, V.COMPANY_ID , C.COMPANY_NAME\n" +
                "from VEHICLE V \n" +
                "JOIN COMPANY C\n" +
                "ON (V.COMPANY_ID = C.COMPANY_ID)\n" +
                "ORDER BY VEHICLE_ID";
        return getJdbcTemplate().queryForList(sql);
    }

    public Vehicle getVehicleById(String vehicleId) throws EmptyResultDataAccessException {
//        if(!vehicleExists(vehicleId)) return null;
        String sql = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        return getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Vehicle.class), vehicleId );
    }

    public List<Map<String,Object>> getVehicleByIdInfo(String vehicleId) throws EmptyResultDataAccessException{
        String sql = "SELECT V.VEHICLE_ID , V.REGISTRATION_NO, V.DATE_ADDED, V.MODEL_ID, V.COMPANY_ID , C.COMPANY_NAME\n" +
                "FROM VEHICLE V \n" +
                "JOIN COMPANY C\n" +
                "ON(V.COMPANY_ID = C.COMPANY_ID)\n" +
                "WHERE VEHICLE_ID = ?";
        Map<String,Object> mp = getJdbcTemplate().queryForMap(sql,vehicleId);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(mp);
        return list;
    }

    public void deleteVehicle(String vehicleId) throws DataIntegrityViolationException{
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        getJdbcTemplate().update(sql,vehicleId);
    }

    public void editVehicle(String vehicleId,Vehicle vehicle) throws DataIntegrityViolationException{
        if(!vehicleId.equals(vehicle.getVehicleId())) {
            throw new DataIntegrityViolationException("cannot edit primary key");
        }
        String sql = "UPDATE vehicle SET " +
                "vehicle_id = ?," +
                "registration_no = ?," +
                "model_id = ?," +
                "company_id = ? " +
                "WHERE vehicle_id = ?";

        getJdbcTemplate().update(sql,vehicle.getVehicleId(),
                vehicle.getRegistrationNo(),
                vehicle.getModelId(),
                vehicle.getCompanyId(),
                vehicleId);
    }

    public List<Map<String,Object>> getAllVehiclesByCompany() throws EmptyResultDataAccessException{
        String sql = "SELECT V.VEHICLE_ID, V.REGISTRATION_NO, V.DATE_ADDED, V.MODEL_ID, V.COMPANY_ID , C.COMPANY_NAME\n" +
                "from VEHICLE V \n" +
                "JOIN COMPANY C\n" +
                "ON (V.COMPANY_ID = C.COMPANY_ID)\n" +
                "WHERE V.COMPANY_ID = ?"+
                "ORDER BY VEHICLE_ID " ;
        return getJdbcTemplate().queryForList(sql, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public List<Map<String,Object>> getVehicleByIdInfo(String vehicleId, String companyId) throws EmptyResultDataAccessException{
        String sql = "SELECT V.VEHICLE_ID , V.REGISTRATION_NO, V.DATE_ADDED, V.MODEL_ID, V.COMPANY_ID , C.COMPANY_NAME\n" +
                "FROM VEHICLE V \n" +
                "JOIN COMPANY C\n" +
                "ON(V.COMPANY_ID = C.COMPANY_ID)\n" +
                "WHERE VEHICLE_ID = ? AND " +
                "V.COMPANY_ID = ?";
        Map<String,Object> mp = getJdbcTemplate().queryForMap(sql,vehicleId,companyId);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(mp);
        return list;
    }
}
