package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class TicketDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }

    public List<Map<String,Object>> getTicketBookedByUser(String tripId){
        String sql = "SELECT T.TICKET_ID, T.PRICE, T.TYPE, T.SEAT_NO , TV.*\n" +
                "FROM TICKET T\n" +
                "JOIN TRAVELLER TV ON(T.TRAVELLER_ID = TV.TRAVELLER_ID)\n" +
                "WHERE T.BOUGHT_BY = ? AND T.TRIP_ID = ?";
        return getJdbcTemplate().queryForList(sql, SecurityContextHolder.getContext().getAuthentication().getName(),tripId);
    }
}
