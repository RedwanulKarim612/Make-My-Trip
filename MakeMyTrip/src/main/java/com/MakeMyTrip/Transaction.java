package com.MakeMyTrip;

public class Transaction {
    private String transactionId;
    private double amount;
    private String walletId;
    private String userId;
    private String name;

    private double balance;
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public Transaction() {
    }

    public Transaction(String transactionId, double amount, String walletId, String userId, String name, double balance) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.walletId = walletId;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", walletId='" + walletId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
