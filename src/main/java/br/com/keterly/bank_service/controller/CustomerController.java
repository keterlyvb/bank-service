package br.com.keterly.bank_service.controller;

import br.com.keterly.bank_service.dto.CustomerDTO;
import br.com.keterly.bank_service.dto.TransferDTO;
import br.com.keterly.bank_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {


    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO) {
        Optional<CustomerDTO> response = service.create(customerDTO);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<CustomerDTO> getByAccountNumber(@PathVariable("accountNumber") int accountNumber) {
        Optional<CustomerDTO> response = service.getByAccountNumber(accountNumber);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferDTO transferDTO) {
        service.executeTransfer(transferDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transfers/{accountNumber}")
    public ResponseEntity<List<TransferDTO>> getTransfersByAccountNumber(@PathVariable("accountNumber") int accountNumber) {
        return ResponseEntity.ok(service.getCustomerTransfers(accountNumber));
    }

}
