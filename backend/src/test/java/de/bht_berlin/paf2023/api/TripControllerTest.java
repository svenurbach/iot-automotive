package de.bht_berlin.paf2023.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.entity.Vehicle;
import de.bht_berlin.paf2023.service.TripService;
import de.bht_berlin.paf2023.repo.TripRepo;


import static org.junit.jupiter.api.Assertions.*;

public class TripControllerTest {

    @Mock
    TripService tripServiceMock;

    @Test
    public void testGetAllTripsStatus() throws Exception {
        // Mock TripService
        TripService tripServiceMock = mock(TripService.class);
        List<Trip> trips = new ArrayList<>();
        when(tripServiceMock.getAllTrips()).thenReturn(trips);

        TripController tripController = new TripController();
        tripController.setTripService(tripServiceMock);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tripController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/trips/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testGetAllTripsWithTripCount() throws Exception {
        TripService tripServiceMock = mock(TripService.class);
        List<Trip> trips = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Trip trip = mock(Trip.class);
            trips.add(trip);
        }
        when(tripServiceMock.getAllTrips()).thenReturn(trips);

        TripController tripController = new TripController();
        tripController.setTripService(tripServiceMock);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tripController).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/trips/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Trip> tripsFromJson = objectMapper.readValue(jsonResponse, new TypeReference<List<Trip>>() {
        });

        int tripCount = tripsFromJson.size();

        assertEquals(trips.size(), tripCount);
    }
}