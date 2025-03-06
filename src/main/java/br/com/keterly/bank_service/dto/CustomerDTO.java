package br.com.keterly.bank_service.dto;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private String name;
    private int accountNumber;
    private double balance;
    private List<TransferDTO> transfers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<TransferDTO> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferDTO> transfers) {
        this.transfers = transfers;
    }
}
