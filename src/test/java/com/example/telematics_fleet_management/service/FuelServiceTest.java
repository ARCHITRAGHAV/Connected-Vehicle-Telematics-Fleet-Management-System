package com.example.telematics_fleet_management.service;

import com.example.telematics_fleet_management.model.FuelLog;
import com.example.telematics_fleet_management.repository.FuelLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FuelServiceTest {

    @Mock
    private FuelLogRepository fuelLogRepository;

    @InjectMocks
    private FuelService fuelService;

    @Test
    void getAllFuelLogsReturnsAllLogsFromRepository() {
        FuelLog first = new FuelLog();
        FuelLog second = new FuelLog();
        List<FuelLog> logs = List.of(first, second);

        when(fuelLogRepository.findAll()).thenReturn(logs);

        List<FuelLog> result = fuelService.getAllFuelLogs();

        assertSame(logs, result);
    }

    @Test
    void getTotalFuelLogsReturnsRepositoryCount() {
        when(fuelLogRepository.count()).thenReturn(7L);

        long result = fuelService.getTotalFuelLogs();

        assertEquals(7L, result);
    }

    @Test
    void getTotalFuelConsumedReturnsSumOfAllRefilledLitres() {
        FuelLog first = new FuelLog();
        first.setLitresRefilled(10.5);
        FuelLog second = new FuelLog();
        second.setLitresRefilled(20.0);
        FuelLog third = new FuelLog();
        third.setLitresRefilled(4.5);

        when(fuelLogRepository.findAll()).thenReturn(List.of(first, second, third));

        double result = fuelService.getTotalFuelConsumed();

        assertEquals(35.0, result);
    }

    @Test
    void getTotalFuelConsumedReturnsZeroWhenNoLogsExist() {
        when(fuelLogRepository.findAll()).thenReturn(List.of());

        double result = fuelService.getTotalFuelConsumed();

        assertEquals(0.0, result);
    }

    @Test
    void getTotalFuelCostReturnsSumOfAllCostAmounts() {
        FuelLog first = new FuelLog();
        first.setCostAmount(1200.0);
        FuelLog second = new FuelLog();
        second.setCostAmount(350.25);

        when(fuelLogRepository.findAll()).thenReturn(List.of(first, second));

        double result = fuelService.getTotalFuelCost();

        assertEquals(1550.25, result);
    }

    @Test
    void getTotalFuelCostReturnsZeroWhenNoLogsExist() {
        when(fuelLogRepository.findAll()).thenReturn(List.of());

        double result = fuelService.getTotalFuelCost();

        assertEquals(0.0, result);
    }

    @Test
    void getAverageRefillAmountReturnsAverageWhenLogsExist() {
        FuelLog first = new FuelLog();
        first.setLitresRefilled(30.0);
        FuelLog second = new FuelLog();
        second.setLitresRefilled(45.0);
        FuelLog third = new FuelLog();
        third.setLitresRefilled(15.0);

        when(fuelLogRepository.findAll()).thenReturn(List.of(first, second, third));

        double result = fuelService.getAverageRefillAmount();

        assertEquals(30.0, result);
    }

    @Test
    void getAverageRefillAmountReturnsZeroWhenNoLogsExist() {
        when(fuelLogRepository.findAll()).thenReturn(List.of());

        double result = fuelService.getAverageRefillAmount();

        assertEquals(0.0, result);
    }
}