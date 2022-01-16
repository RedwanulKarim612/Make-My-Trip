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
public class TransactionDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void initialize(){setDataSource(dataSource);}

    public List<Transaction> getAllTransaction()  throws EmptyResultDataAccessException {
        String sql = "SELECT transaction_Id, amount, wallet_Id, user_Id, name, balance " +
                "FROM TRANSACTION JOIN WALLET USING (wallet_Id) JOIN CUSTOMER USING (USER_ID)";
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Transaction.class));
    }


    public Transaction getTransactionById(String transactionId) throws EmptyResultDataAccessException {
        String sql = "SELECT transaction_Id, amount, wallet_Id, user_Id, name, balance " +
                "FROM (SELECT * FROM TRANSACTION WHERE transaction_id = ?) JOIN WALLET USING (wallet_Id) JOIN CUSTOMER USING (USER_ID)";
        return getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Transaction.class), transactionId);
    }

    public void addTransaction(Transaction transaction) throws DuplicateKeyException {
        String sql = "INSERT INTO TRANSACTION VALUES(?,?,?)";
        getJdbcTemplate().update(sql,transaction.getTransactionId(),transaction.getAmount(), transaction.getWalletId());
        sql = "SELECT balance FROM WALLET WHERE WALLET_ID = ?";
        Double d = getJdbcTemplate().queryForObject(sql, Double.class, transaction.getWalletId());
        sql = "UPDATE WALLET " +
                "SET balance = ?";

        getJdbcTemplate().update(sql, d + transaction.getAmount());
    }
}
