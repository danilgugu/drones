package gugunava.danil.drones.controller;

import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.entity.MedicationEntity;
import gugunava.danil.drones.generator.DroneEntityGenerator;
import gugunava.danil.drones.generator.MedicationEntityGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DroneController_GetMedications_Test extends AbstractDroneControllerTest {

	private static final String API = "/medications";

	@Test
	void whenDroneExists_thenStatusSuccess() throws Exception {
		DroneEntity droneEntity = DroneEntityGenerator.valid();
		List<MedicationEntity> medicationEntities = MedicationEntityGenerator.list();
		given(droneRepository.findBySerialNumber(droneEntity.getSerialNumber())).willReturn(Optional.of(droneEntity));
		given(droneRepository.findAllMedicationsByDroneIdWithoutImage(droneEntity.getId())).willReturn(medicationEntities);

		mockMvc.perform(get(BASE_URL + droneEntity.getSerialNumber() + API))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(medicationEntities.get(0).getName())))
				.andExpect(jsonPath("$[0].weight", is(medicationEntities.get(0).getWeight())))
				.andExpect(jsonPath("$[0].code", is(medicationEntities.get(0).getCode())))
				.andExpect(jsonPath("$[1].name", is(medicationEntities.get(1).getName())))
				.andExpect(jsonPath("$[1].weight", is(medicationEntities.get(1).getWeight())))
				.andExpect(jsonPath("$[1].code", is(medicationEntities.get(1).getCode())));

		verify(droneRepository).findBySerialNumber(droneEntity.getSerialNumber());
		verify(droneRepository).findAllMedicationsByDroneIdWithoutImage(droneEntity.getId());
	}

	@Test
	void whenDroneIsEmpty_thenStatusSuccessAndReturnEmptyList() throws Exception {
		DroneEntity droneEntity = DroneEntityGenerator.valid();
		List<MedicationEntity> medicationEntities = Collections.emptyList();
		given(droneRepository.findBySerialNumber(droneEntity.getSerialNumber())).willReturn(Optional.of(droneEntity));
		given(droneRepository.findAllMedicationsByDroneIdWithoutImage(droneEntity.getId())).willReturn(medicationEntities);

		mockMvc.perform(get(BASE_URL + droneEntity.getSerialNumber() + API))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(medicationEntities)));

		verify(droneRepository).findBySerialNumber(droneEntity.getSerialNumber());
		verify(droneRepository).findAllMedicationsByDroneIdWithoutImage(droneEntity.getId());
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
		verify(droneRepository, never()).findAllMedicationsByDroneIdWithoutImage(anyLong());
	}
}
