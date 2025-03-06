package br.com.keterly.bank_service.service;

import br.com.keterly.bank_service.dto.CustomerDTO;
import br.com.keterly.bank_service.dto.TransferDTO;
import br.com.keterly.bank_service.persistence.model.Customer;
import br.com.keterly.bank_service.persistence.model.Transfer;
import br.com.keterly.bank_service.persistence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerMapper mapper;


    @Override
    public Optional<CustomerDTO> create(CustomerDTO request) {

        Customer entity = new Customer();
        entity.setName(request.getName());
        entity.setAccountNumber(request.getAccountNumber());
        entity.setBalance(request.getBalance());

        Customer customer = repository.saveAndFlush(entity);
        CustomerDTO response = mapper.buildCustomerDTO(customer);

        return Optional.of(response);
    }

    @Override
    public List<CustomerDTO> getAll() {

        List<CustomerDTO> responses = new ArrayList<>();
        List<Customer> customers = repository.findAll();
        for (Customer customer : customers) {
            CustomerDTO response = mapper.buildCustomerDTO(customer);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public Optional<CustomerDTO> getByAccountNumber(int accountNumber) {

        Optional<Customer> entity = repository.findByAccountNumber(accountNumber);
        if (entity.isPresent()) {
            CustomerDTO response = mapper.buildCustomerDTO(entity.get());
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public void executeTransfer(TransferDTO request) {

        if (request.getAmmount() > 100.0) {
            throw new IllegalArgumentException("Amount must be less than or equal to R$ 100,00");
        }

        Optional<Customer> customer = repository.findByAccountNumber(request.getSourceAccount());
        if (customer.isPresent()) {
            Transfer transfer = new Transfer();

            if (customer.get().getBalance() > request.getAmmount()) {
                double newBalance = customer.get().getBalance() - request.getAmmount();
                customer.get().setBalance(newBalance);
                transfer.setStatus("Success");
            } else {
                transfer.setStatus("Failed");
            }

            transfer.setCustomer(customer.get());
            transfer.setTransferDate(LocalDateTime.now());
            transfer.setAmmount(request.getAmmount());
            transfer.setSourceAccount(request.getSourceAccount());
            transfer.setDestinationAccount(request.getDestinationAccount());

            List<Transfer> transferList = new ArrayList<>();
            transferList.add(transfer);
            customer.get().setTransfers(transferList);

            repository.saveAndFlush(customer.get());
        }
    }

    @Override
    public List<TransferDTO> getCustomerTransfers(int accountNumber) {

        Optional<Customer> customer = repository.findByAccountNumber(accountNumber);

        if (customer.isPresent()) {
            List<TransferDTO> responses = new ArrayList<>();

            for (Transfer transfer : customer.get().getTransfers()) {
                TransferDTO transferDTO = mapper.buildTransferDTO(transfer);
                responses.add(transferDTO);
            }
            return responses;
        }

        return List.of();
    }
}
