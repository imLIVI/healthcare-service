import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalServiceImplTest {
    private final String patientId = "df622e78-729a-4ec0-9790-73fb29518fa5";
    // Blood Pressure
    private final BloodPressure bloodPressureHealthy = new BloodPressure(120, 80);
    private final BloodPressure bloodPressureUnhealthy = new BloodPressure(200, 0);
    // Temperature
    private final BigDecimal temperatureHealthy = new BigDecimal("36.6");
    private final BigDecimal temperatureUnhealthy = new BigDecimal("31.1");
    // Info
    private final PatientInfo patientInfo = new PatientInfo(
            patientId,
            "Иван", "Петров",
            LocalDate.of(1980, 11, 26),
            new HealthInfo(temperatureHealthy, bloodPressureHealthy)
    );
    private final String message = "Warning, patient with id: " + patientId + ", need help";
    // Mockito
    private final PatientInfoFileRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
    private final SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);
    private final MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
    private final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

    @BeforeAll
    public static void init() {
        System.out.println("Running tests...");
    }

    @BeforeEach
    public void startOfTest() {
        System.out.println("---------------------------------------\n[START OF THE TEST]");
        Mockito.when(patientInfoRepository.getById(patientId)).thenReturn(patientInfo);
        Mockito.doAnswer((Answer<Void>) invocationOnMock -> {
            System.out.println(message);
            return null;
        }).when(alertService).send(Mockito.any());
    }

    @Test
    @DisplayName("Проверка: нездоровые показатели кровяного давления => вызывается метод send(...) - предупреждение." +
            "При этом метод send(...) вызывается 1 раз")
    public void checkBloodPressure_unhealthyValues() {
        medicalService.checkBloodPressure(patientId, bloodPressureUnhealthy);
        Mockito.verify(alertService).send(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), message);
        Mockito.verify(alertService, Mockito.only()).send(message);
    }

    @Test
    @DisplayName("Проверка: здоровые показатели кровяного давления => метод send(...) - предупреждение не вызывается")
    public void checkBloodPressure_healthyValues() {
        medicalService.checkBloodPressure(patientId, bloodPressureHealthy);
        Mockito.verify(alertService, Mockito.never()).send(message);
    }

    @Test
    @DisplayName("Проверка: нездоровые показатели температуры => вызывается метод send(...) - предупреждение." +
            "При этом метод send(...) вызывается 1 раз")
    public void checkTemperature_unhealthyValues() {
        medicalService.checkTemperature(patientId, temperatureUnhealthy);
        Mockito.verify(alertService).send(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), message);
        Mockito.verify(alertService, Mockito.only()).send(message);
    }

    @Test
    @DisplayName("Проверка: здоровые показатели температуры => метод send(...) - предупреждение не вызывается")
    public void checkTemperature_healthyValues() {
        medicalService.checkTemperature(patientId, temperatureHealthy);
        Mockito.verify(alertService, Mockito.never()).send(message);
    }

    @AfterEach
    public void endOfTest() {
        System.out.println("[END OF THE TEST]\n---------------------------------------");
    }

    @AfterAll
    public static void end() {
        System.out.println("Tests done...");
    }
}
