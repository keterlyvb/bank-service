package br.com.keterly.bank_service.service;

import br.com.keterly.bank_service.dto.CustomerDTO;
import br.com.keterly.bank_service.dto.TransferDTO;
import br.com.keterly.bank_service.persistence.model.Customer;
import br.com.keterly.bank_service.persistence.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {

    public CustomerDTO buildCustomerDTO(Customer customer) {
        CustomerDTO response = new CustomerDTO();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setAccountNumber(customer.getAccountNumber());
        response.setBalance(customer.getBalance());

        if (customer.getTransfers() != null && !customer.getTransfers().isEmpty()) {
            List<TransferDTO> transferDTOS = new ArrayList<>();
            for (Transfer entity : customer.getTransfers()) {
                transferDTOS.add(buildTransferDTO(entity));
            }
            response.setTransfers(transferDTOS);
        }

        return response;
    }

    public TransferDTO buildTransferDTO(Transfer entity) {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSourceAccount(entity.getSourceAccount());
        transferDTO.setDestinationAccount(entity.getDestinationAccount());
        transferDTO.setAmmount(entity.getAmmount());
        transferDTO.setStatus(entity.getStatus());
        transferDTO.setTransferDate(entity.getTransferDate());

        return transferDTO;
    }




}
