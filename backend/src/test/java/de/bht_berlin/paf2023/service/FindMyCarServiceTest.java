package de.bht_berlin.paf2023.service;

import de.bht_berlin.paf2023.component.LastLocalMeasurementPositionStrategy;
import de.bht_berlin.paf2023.component.LastTripPositionStrategy;
import de.bht_berlin.paf2023.strategy.PositionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindMyCarServiceTest {

    private FindMyCarService findMyCarService;

    @Mock
    private PositionStrategy positionStrategy;

    @Mock
    private LastTripPositionStrategy lastTripPositionStrategy;

    @Mock
    private LastLocalMeasurementPositionStrategy lastLocalMeasurementPositionStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        findMyCarService = new FindMyCarService(positionStrategy);
    }

    @Test
    void getLastCarPositionWithLastTripPositionStrategy() {
        Long carId = 100L;
        List<Float> expectedPosition = Arrays.asList(52.52f, 13.405f);
        when(lastTripPositionStrategy.findLastPosition(carId)).thenReturn(expectedPosition);

        findMyCarService.setPositionStrategy(lastTripPositionStrategy);
        List<Float> actualPosition = findMyCarService.getLastCarPosition(carId);

        assertEquals(expectedPosition, actualPosition);
        verify(lastTripPositionStrategy, times(1)).findLastPosition(carId);
    }
    @Test
    void getLastCarPositionWithLastLocalMeasurementPositionStrategy() {
        Long carId = 100L;
        List<Float> expectedPosition = Arrays.asList(52.52f, 13.405f);
        when(lastLocalMeasurementPositionStrategy.findLastPosition(carId)).thenReturn(expectedPosition);

        findMyCarService.setPositionStrategy(lastLocalMeasurementPositionStrategy);
        List<Float> actualPosition = findMyCarService.getLastCarPosition(carId);

        assertEquals(expectedPosition, actualPosition);
        verify(lastLocalMeasurementPositionStrategy, times(1)).findLastPosition(carId);
    }

    @Test
    void setPositionStrategyWithLastTripPositionStrategy() {
        PositionStrategy lastTripPositionStrategy = mock(LastTripPositionStrategy.class);

        findMyCarService.setPositionStrategy(lastTripPositionStrategy);

        assertEquals(lastTripPositionStrategy, findMyCarService.getPositionStrategy());
    }

    @Test
    void setPositionStrategyWithLastLocalMeasurementPositionStrategy() {
        PositionStrategy lastLocalMeasurementPositionStrategy = mock(LastLocalMeasurementPositionStrategy.class);

        findMyCarService.setPositionStrategy(lastLocalMeasurementPositionStrategy);

        assertEquals(lastLocalMeasurementPositionStrategy, findMyCarService.getPositionStrategy());
    }
}
