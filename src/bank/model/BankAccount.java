package bank.model;

import java.io.Serializable;

public class BankAccount extends AbstractBankAccount implements Serializable {

    private String accountNumber;
    private double amount;

    public BankAccount() {
    }

    public BankAccount(String accountNumber, double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return accountNumber + " = " + amount;
    }
}
