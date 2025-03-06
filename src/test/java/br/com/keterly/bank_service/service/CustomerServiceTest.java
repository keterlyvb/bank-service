package br.com.keterly.bank_service.service;

import br.com.keterly.bank_service.dto.CustomerDTO;
import br.com.keterly.bank_service.dto.TransferDTO;
import br.com.keterly.bank_service.persistence.model.Customer;
import br.com.keterly.bank_service.persistence.model.Transfer;
import br.com.keterly.bank_service.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl service;

    private Customer customer;
    private CustomerDTO customerDTO;
    private Transfer transfer;
    private TransferDTO transferDTO;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("keterly");
        customer.setAccountNumber(12345);
        customer.setBalance(200.0);

        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("keterly");
        customerDTO.setAccountNumber(12345);
        customerDTO.setBalance(200.0);

        transfer = new Transfer();
        transfer.setId(1L);
        transfer.setSourceAccount(12345);
        transfer.setDestinationAccount(67890);
        transfer.setAmmount(50.0);
        transfer.setTransferDate(LocalDateTime.now());
        transfer.setStatus("Success");
        transfer.setCustomer(customer);

        transferDTO = new TransferDTO();
        transferDTO.setSourceAccount(12345);
        transferDTO.setDestinationAccount(67890);
        transferDTO.setAmmount(50.0);
    }

    @Test
    void testCreateCustomer_Success() {
        when(repository.saveAndFlush(any())).thenReturn(customer);
        when(mapper.buildCustomerDTO(any())).thenReturn(customerDTO);

        Optional<CustomerDTO> result = service.create(customerDTO);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("keterly");
        assertThat(result.get().getAccountNumber()).isEqualTo(12345);
        assertThat(result.get().getBalance()).isEqualTo(200.0);

        verify(repository, times(1)).saveAndFlush(any());
        verify(mapper, times(1)).buildCustomerDTO(any());
    }

    @Test
    void testGetAllCustomers() {
        when(repository.findAll()).thenReturn(Arrays.asList(customer));
        when(mapper.buildCustomerDTO(any())).thenReturn(customerDTO);

        List<CustomerDTO> customers = service.getAll();

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getName()).isEqualTo("keterly");

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).buildCustomerDTO(any());
    }

    @Test
    void testGetByAccountNumber_Success() {
        when(repository.findByAccountNumber(12345)).thenReturn(Optional.of(customer));
        when(mapper.buildCustomerDTO(any())).thenReturn(customerDTO);

        Optional<CustomerDTO> result = service.getByAccountNumber(12345);

        assertThat(result).isPresent();
        assertThat(result.get().getAccountNumber()).isEqualTo(12345);

        verify(repository, times(1)).findByAccountNumber(12345);
        verify(mapper, times(1)).buildCustomerDTO(any());
    }

    @Test
    void testGetByAccountNumber_NotFound() {
        when(repository.findByAccountNumber(99999)).thenReturn(Optional.empty());

        Optional<CustomerDTO> result = service.getByAccountNumber(99999);

        assertThat(result).isEmpty();

        verify(repository, times(1)).findByAccountNumber(99999);
        verify(mapper, never()).buildCustomerDTO(any());
    }

    @Test
    void testExecuteTransfer_Success() {
        when(repository.findByAccountNumber(12345)).thenReturn(Optional.of(customer));
        when(repository.saveAndFlush(any())).thenReturn(customer);

        service.executeTransfer(transferDTO);

        assertThat(customer.getBalance()).isEqualTo(150.0);
        assertThat(customer.getTransfers()).hasSize(1);
        assertThat(customer.getTransfers().get(0).getStatus()).isEqualTo("Success");

        verify(repository, times(1)).findByAccountNumber(12345);
        verify(repository, times(1)).saveAndFlush(customer);
    }

    @Test
    void testExecuteTransfer_InsufficientBalance() {
        customer.setBalance(10.0);
        when(repository.findByAccountNumber(12345)).thenReturn(Optional.of(customer));

        service.executeTransfer(transferDTO);

        assertThat(customer.getBalance()).isEqualTo(10.0);
        assertThat(customer.getTransfers()).hasSize(1);
        assertThat(customer.getTransfers().get(0).getStatus()).isEqualTo("Failed");

        verify(repository, times(1)).findByAccountNumber(12345);
        verify(repository, times(1)).saveAndFlush(customer);
    }

    @Test
    void testExecuteTransfer_AmountExceedsLimit() {
        transferDTO.setAmmount(200.0);

        assertThatThrownBy(() -> service.executeTransfer(transferDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount must be less than or equal to R$ 100,00");

        verify(repository, never()).findByAccountNumber(anyInt());
        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    void testGetCustomerTransfers_Success() {
        customer.setTransfers(Arrays.asList(transfer));

        when(repository.findByAccountNumber(12345)).thenReturn(Optional.of(customer));
        when(mapper.buildTransferDTO(any())).thenReturn(transferDTO);

        List<TransferDTO> transfers = service.getCustomerTransfers(12345);

        assertThat(transfers).hasSize(1);
        assertThat(transfers.get(0).getAmmount()).isEqualTo(50.0);

        verify(repository, times(1)).findByAccountNumber(12345);
        verify(mapper, times(1)).buildTransferDTO(any());
    }

}


