package com.kata.ebank.web;

import com.kata.config.AppTestConfiguration;
import com.kata.dataset.AccountHistoryDTODummyData;
import com.kata.dataset.BankAccountDTODummyData;
import com.kata.dataset.CustomerDTODummyData;
import com.kata.ebank.dtos.*;
import com.kata.ebank.services.BankAccountService;
import com.kata.utils.JsonHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@Import(AppTestConfiguration.class)
@WebMvcTest(value = BankAccountRestController.class)
class BankAccountRestControllerUTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;


    @Test
    void getBankAccountsByCustomerIdTest() throws Exception{

        Long customerId = 1L;
        List<BankAccountDTO> mockBankAccountDTOs = BankAccountDTODummyData.generateBankAccountDTOList();
        Mockito.when(bankAccountService.listBankAccountByCustomerId(customerId)).thenReturn(mockBankAccountDTOs);
        String URI = "/api/bank//accounts/customer/"+customerId;
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = JsonHelper.toJson(mockBankAccountDTOs);
        String outputInJson = result.getResponse().getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedJson);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    }


    @Test
    void getAccountHistoryTest() throws Exception {

        AccountHistoryDTO mockAccountHistoryDTO = AccountHistoryDTODummyData.generateAccountHistoryDTOData();
        Mockito.when(bankAccountService.getBankAccountHistory(eq("ab5053b7-d5bd-431e-b008-0be53b10d1f5"),eq(0),eq(5))).thenReturn(mockAccountHistoryDTO);
        String URI = "/api/bank/accounts/ab5053b7-d5bd-431e-b008-0be53b10d1f5/operations";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = JsonHelper.toJson(mockAccountHistoryDTO);
        String outputInJson = result.getResponse().getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedJson);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void depositTest() throws Exception {

        OperationRequestDTO mockOperationRequestDTO = new OperationRequestDTO();

        mockOperationRequestDTO.setAccountId("ab5053b7-d5bd-431e-b008-0be53b10d1f5");
        mockOperationRequestDTO.setAmount(100);
        mockOperationRequestDTO.setDescription("Operation Description");

        String inputInJson = JsonHelper.toJson(mockOperationRequestDTO);

        String URI = "/api/bank/accounts/deposit";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Mockito.verify(bankAccountService, Mockito.times(1)).deposit(eq("ab5053b7-d5bd-431e-b008-0be53b10d1f5"),eq(100.0d),eq("Operation Description"));
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    void withdrawalTest() throws Exception {

        OperationRequestDTO mockOperationRequestDTO = new OperationRequestDTO();

        mockOperationRequestDTO.setAccountId("ab5053b7-d5bd-431e-b008-0be53b10d1f5");
        mockOperationRequestDTO.setAmount(50);
        mockOperationRequestDTO.setDescription("Operation Description");

        String inputInJson = JsonHelper.toJson(mockOperationRequestDTO);

        String URI = "/api/bank/accounts/withdrawal";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        Mockito.verify(bankAccountService, Mockito.times(1)).withdrawal(eq("ab5053b7-d5bd-431e-b008-0be53b10d1f5"),eq(50.0d),eq("Operation Description"));
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

    }
}