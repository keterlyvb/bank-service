package br.com.keterly.bank_service.dto;

import java.time.LocalDateTime;

public class TransferDTO {

    private int sourceAccount;
    private int destinationAccount;
    private double ammount;
    private LocalDateTime transferDate;
    private String status;

    public int getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(int sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(int destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
