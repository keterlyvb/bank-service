package br.com.keterly.bank_service.controller;

import br.com.keterly.bank_service.dto.CustomerDTO;
import br.com.keterly.bank_service.dto.TransferDTO;
import br.com.keterly.bank_service.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void testCreateCustomer_Success() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("keterly");
        customerDTO.setAccountNumber(1234);
        customerDTO.setBalance(100.0);
        when(customerService.create(any())).thenReturn(Optional.of(customerDTO));

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"keterly\",\"accountNumber\":1234,\"balance\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("keterly"))
                .andExpect(jsonPath("$.accountNumber").value(1234))
                .andExpect(jsonPath("$.balance").value(100.0))
                .andDo(print());

        verify(customerService, times(1)).create(any());
    }

    @Test
    void testCreateCustomer_NoContent() throws Exception {
        when(customerService.create(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Keterly\",\"accountNumber\":1234,\"balance\":100.0}"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(customerService, times(1)).create(any());
    }

    @Test
    void testGetAllCustomers() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setName("keterly");
        customerDTO1.setAccountNumber(1234);
        customerDTO1.setBalance(100.0);

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setName("aparecida");
        customerDTO2.setAccountNumber(5678);
        customerDTO2.setBalance(200.0);

        List<CustomerDTO> customers = Arrays.asList(
                customerDTO1, customerDTO2
        );

        when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("keterly"))
                .andExpect(jsonPath("$[1].name").value("aparecida"))
                .andDo(print());

        verify(customerService, times(1)).getAll();
    }

    @Test
    void testGetByAccountNumber_Success() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Keterly");
        customerDTO.setAccountNumber(1234);
        customerDTO.setBalance(100.0);
        when(customerService.getByAccountNumber(1234)).thenReturn(Optional.of(customerDTO));

        mockMvc.perform(get("/v1/customers/1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("keterly"))
                .andExpect(jsonPath("$.accountNumber").value(1234))
                .andDo(print());

        verify(customerService, times(1)).getByAccountNumber(1234);
    }

    @Test
    void testGetByAccountNumber_NotFound() throws Exception {
        when(customerService.getByAccountNumber(99999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/customers/99999"))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(customerService, times(1)).getByAccountNumber(99999);
    }

    @Test
    void testTransfer() throws Exception {

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSourceAccount(1234);
        transferDTO.setDestinationAccount(5678);
        transferDTO.setAmmount(50.0);

        mockMvc.perform(post("/v1/customers/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceAccount\":1234,\"destinationAccount\":5678,\"amount\":50.0}"))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(customerService, times(1)).executeTransfer(any());
    }

}