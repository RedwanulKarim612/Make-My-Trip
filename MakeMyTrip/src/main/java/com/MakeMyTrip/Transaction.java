package com.MakeMyTrip;

public class Transaction {
    private String transactionId;
    private double amount;
    private String walletId;

    public Transaction() {
    }

    public Transaction(String transactionId, double amount, String walletId, String userId, String name, double balance) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.walletId = walletId;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", walletId='" + walletId + '\'' +
                '}';
    }
}
