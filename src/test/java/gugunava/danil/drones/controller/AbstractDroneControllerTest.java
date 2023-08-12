package gugunava.danil.drones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gugunava.danil.drones.AbstractTest;
import gugunava.danil.drones.repository.DroneMedicationRepository;
import gugunava.danil.drones.repository.DroneRepository;
import gugunava.danil.drones.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class AbstractDroneControllerTest extends AbstractTest {

	protected static final String BASE_URL = "/drones/";

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected DroneRepository droneRepository;

	@MockBean
	protected MedicationRepository medicationRepository;

	@MockBean
	protected DroneMedicationRepository droneMedicationRepository;
}
