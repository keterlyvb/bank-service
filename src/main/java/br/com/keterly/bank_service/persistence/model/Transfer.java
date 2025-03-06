package br.com.keterly.bank_service.persistence.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(columnDefinition = "id_customer", referencedColumnName = "id")
    private Customer customer;

    @Column
    private int sourceAccount;

    @Column
    private int destinationAccount;

    @Column
    private double ammount;

    @Column
    private LocalDateTime transferDate;

    @Column
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }
}
