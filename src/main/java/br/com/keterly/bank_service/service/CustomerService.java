package br.com.keterly.bank_service.service;

import br.com.keterly.bank_service.dto.CustomerDTO;
import br.com.keterly.bank_service.dto.TransferDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<CustomerDTO> create(CustomerDTO request);

    List<CustomerDTO> getAll();

    Optional<CustomerDTO> getByAccountNumber(int accountNumber);

    void executeTransfer(TransferDTO request);

    List<TransferDTO> getCustomerTransfers(int accountNumber);
}
