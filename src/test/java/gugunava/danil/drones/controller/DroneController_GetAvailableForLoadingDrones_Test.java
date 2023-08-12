package gugunava.danil.drones.controller;

import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.generator.DroneEntityGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static gugunava.danil.drones.constants.Constants.MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING;
import static gugunava.danil.drones.constants.Constants.STATES_ALLOWED_FOR_LOADING;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DroneController_GetAvailableForLoadingDrones_Test extends AbstractDroneControllerTest {

	private static final String API = "/available";

	@Test
	void whenDronesAreAvailable_thenReturnListOfDrones() throws Exception {
		List<DroneEntity> droneEntities = DroneEntityGenerator.list();
		given(droneRepository.findAllByBatteryCapacityGreaterThanEqualAndStateIn(MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING, STATES_ALLOWED_FOR_LOADING))
				.willReturn(droneEntities);

		mockMvc.perform(get(BASE_URL + API))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].serialNumber", is(droneEntities.get(0).getSerialNumber())))
				.andExpect(jsonPath("$[0].model", is(droneEntities.get(0).getModel())))
				.andExpect(jsonPath("$[0].weightLimit", is(droneEntities.get(0).getWeightLimit())))
				.andExpect(jsonPath("$[0].batteryCapacity", is(droneEntities.get(0).getBatteryCapacity())))
				.andExpect(jsonPath("$[0].state", is(droneEntities.get(0).getState().name())))
				.andExpect(jsonPath("$[1].serialNumber", is(droneEntities.get(1).getSerialNumber())))
				.andExpect(jsonPath("$[1].model", is(droneEntities.get(1).getModel())))
				.andExpect(jsonPath("$[1].weightLimit", is(droneEntities.get(1).getWeightLimit())))
				.andExpect(jsonPath("$[1].batteryCapacity", is(droneEntities.get(1).getBatteryCapacity())))
				.andExpect(jsonPath("$[1].state", is(droneEntities.get(1).getState().name())));

		verify(droneRepository).findAllByBatteryCapacityGreaterThanEqualAndStateIn(MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING, STATES_ALLOWED_FOR_LOADING);
	}

	@Test
	void whenNoDronesAvailable_thenReturnEmptyList() throws Exception {
		List<DroneEntity> droneEntities = Collections.emptyList();
		given(droneRepository.findAllByBatteryCapacityGreaterThanEqualAndStateIn(MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING, STATES_ALLOWED_FOR_LOADING))
				.willReturn(droneEntities);

		mockMvc.perform(get(BASE_URL + API))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(droneEntities)));

		verify(droneRepository).findAllByBatteryCapacityGreaterThanEqualAndStateIn(MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING, STATES_ALLOWED_FOR_LOADING);
	}
}
