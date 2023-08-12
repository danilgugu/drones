package gugunava.danil.drones.controller;

import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.generator.DroneEntityGenerator;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DroneController_GetBatteryLevel_Test extends AbstractDroneControllerTest {

	private static final String API = "/battery";

	@Test
	void whenDroneExists_thenStatusSuccess() throws Exception {
		DroneEntity droneEntity = DroneEntityGenerator.valid();
		given(droneRepository.findBySerialNumber(droneEntity.getSerialNumber())).willReturn(Optional.of(droneEntity));

		mockMvc.perform(get(BASE_URL + droneEntity.getSerialNumber() + API))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(droneEntity.getBatteryCapacity())));

		verify(droneRepository).findBySerialNumber(droneEntity.getSerialNumber());
	}

	@Test
	void whenDroneNotExists_thenStatusNotFound() throws Exception {
		String serialNumber = "not_exists";
		given(droneRepository.findBySerialNumber(serialNumber)).willReturn(Optional.empty());

		mockMvc.perform(get(BASE_URL + serialNumber + API))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Drone with serialNumber 'not_exists' not found.")))
				.andExpect(jsonPath("$.timestamp", notNullValue()));

		verify(droneRepository).findBySerialNumber(serialNumber);
	}
}
