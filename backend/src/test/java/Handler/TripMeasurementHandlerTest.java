package Handler;

import de.bht_berlin.paf2023.entity.Measurement;
import de.bht_berlin.paf2023.entity.Trip;
import de.bht_berlin.paf2023.handler.MeasurementTimeSortHandler;
import de.bht_berlin.paf2023.handler.TripMeasurementHandler;
import de.bht_berlin.paf2023.repo.MeasurementRepo;
import de.bht_berlin.paf2023.repo.MeasurementRepoSubject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TripMeasurementHandlerTest {

    @Mock
    private Trip trip;

    @Mock
    private MeasurementRepo measurementRepo;

    @Mock
    private MeasurementTimeSortHandler nextHandler;


}

