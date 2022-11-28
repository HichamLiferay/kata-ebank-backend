package com.kata.ebank.web;

import com.kata.config.AppTestConfiguration;
import com.kata.dataset.BankAccountDTODummyData;
import com.kata.dataset.CustomerDTODummyData;
import com.kata.ebank.dtos.BankAccountDTO;
import com.kata.ebank.dtos.CustomerDTO;
import com.kata.ebank.services.BankAccountService;
import com.kata.utils.JsonHelper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@Import(AppTestConfiguration.class)
@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerRestController.class)
class CustomerRestControllerUTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    void getCustomersTest() throws Exception {

        List<CustomerDTO> mockCustomerDTOList = CustomerDTODummyData.generateCustomerDTOList();

        Mockito.when(bankAccountService.listCustomers()).thenReturn(mockCustomerDTOList);
        String URI = "/api/bank/customers";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = JsonHelper.toJson(mockCustomerDTOList);
        String outputInJson = result.getResponse().getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());

    }

    @Test
    void getCustomerByNameTest() throws Exception {

        String keyword = "";
        List<CustomerDTO> mockCustomerDTOs = CustomerDTODummyData.generateCustomerDTOList();

        Mockito.when(bankAccountService.searchCustomer(keyword)).thenReturn(mockCustomerDTOs);
        String URI = "/api/bank/customers/search?keyword="+keyword;
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = JsonHelper.toJson(mockCustomerDTOs);
        String outputInJson = result.getResponse().getContentAsString();

        assertThat(outputInJson).isEqualTo(expectedJson);
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());

    }

}