package br.com.keterly.bank_service.persistence.repository;

import br.com.keterly.bank_service.persistence.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByAccountNumber(int accountNumber);
}
