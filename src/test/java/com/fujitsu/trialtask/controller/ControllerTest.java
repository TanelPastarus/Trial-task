package com.fujitsu.trialtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fujitsu.trialtask.enums.City;
import com.fujitsu.trialtask.enums.Vehicle;
import com.fujitsu.trialtask.model.CityBaseFee;
import com.fujitsu.trialtask.model.ExtraFees;
import com.fujitsu.trialtask.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = Controller.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeliveryService deliveryService;

    private ObjectMapper objectMapper;

    private ExtraFees extraFees;

    private CityBaseFee cityBaseFee;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        cityBaseFee = new CityBaseFee(1L, City.TARTU, 3.5, 3.0, 2.5);
        extraFees = new ExtraFees(1L, Vehicle.BIKE, 1.0,
                0.5, 0.5, -1.0,
                1.0, 0.5, -1.0);
    }

    @Test
    void getDeliveryFee_returnsCorrectFee_returnsCorrectHTTPValue() throws Exception {
        String cityName = "Tartu";
        String vehicleName = "Bike";
        double expectedFee = 3.5;

        Mockito.when(deliveryService.findDeliveryFee(cityName, vehicleName)).thenReturn(3.5);

        mockMvc.perform(MockMvcRequestBuilders.get("/fee/{city}/{vehicle}", cityName, vehicleName))
                .andExpect(status().isOk())
                .andExpect(content().string(Double.toString(expectedFee)));
    }

    @Test
    void updateCityBaseFee_callsUpdate_returnsCorrectHTTPValueAndResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/basefee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cityBaseFee)))
                .andExpect(status().isOk())
                .andExpect(content().string("Basefees for city TARTU updated succesfully"));

        verify(deliveryService, times(1)).updateCityBaseFee(cityBaseFee);
    }

    @Test
    void updateVehicleExtraFee_callsUpdate_returnsCorrectHttpValueAndResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/extrafee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(extraFees)))
                .andExpect(status().isOk())
                .andExpect(content().string("Extrafees for vehicle BIKE updated succesfully"));

        verify(deliveryService, times(1)).updateExtraFees(extraFees);
    }
}