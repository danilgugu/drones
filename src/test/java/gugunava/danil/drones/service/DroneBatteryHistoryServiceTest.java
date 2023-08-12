package gugunava.danil.drones.service;

import gugunava.danil.drones.AbstractTest;
import gugunava.danil.drones.entity.DroneBatteryHistoryEntity;
import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.generator.DroneEntityGenerator;
import gugunava.danil.drones.repository.DroneBatteryHistoryRepository;
import gugunava.danil.drones.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DroneBatteryHistoryServiceTest extends AbstractTest {

	@Autowired
	private DroneBatteryHistoryService droneBatteryHistoryService;

	@MockBean
	private DroneRepository droneRepository;

	@MockBean
	private DroneBatteryHistoryRepository droneBatteryHistoryRepository;

	@Test
	void whenDronesExist_thenShouldRecordBatteryCapacity() {
		List<DroneEntity> droneEntities = DroneEntityGenerator.list();
		given(droneRepository.findAll()).willReturn(droneEntities);
		Instant beforeRecord = Instant.now();

		droneBatteryHistoryService.recordDronesBatteryCapacity();

		Instant afterRecord = Instant.now();
		ArgumentCaptor<List<DroneBatteryHistoryEntity>> droneBatteryHistoryEntitiesArgumentCaptor = ArgumentCaptor.forClass(List.class);
		verify(droneBatteryHistoryRepository).saveAll(droneBatteryHistoryEntitiesArgumentCaptor.capture());
		List<DroneBatteryHistoryEntity> actualRecords = droneBatteryHistoryEntitiesArgumentCaptor.getValue();
		then(actualRecords).hasSameSizeAs(droneEntities);
		then(actualRecords.get(0).getDroneId()).isEqualTo(droneEntities.get(0).getId());
		then(actualRecords.get(0).getBatteryLevel()).isEqualTo(droneEntities.get(0).getBatteryCapacity());
		then(actualRecords.get(0).getDateTime()).isBetween(beforeRecord, afterRecord);
	}

	@Test
	void whenNoDronesExist_thenShouldNotRecordAnything() {
		List<DroneEntity> droneEntities = Collections.emptyList();
		given(droneRepository.findAll()).willReturn(droneEntities);

		droneBatteryHistoryService.recordDronesBatteryCapacity();

		List<DroneBatteryHistoryEntity> droneBatteryHistoryEntities = Collections.emptyList();
		verify(droneBatteryHistoryRepository).saveAll(droneBatteryHistoryEntities);
	}
}
