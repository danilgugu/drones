package gugunava.danil.drones.service;

import gugunava.danil.drones.entity.DroneBatteryHistoryEntity;
import gugunava.danil.drones.repository.DroneBatteryHistoryRepository;
import gugunava.danil.drones.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneBatteryHistoryService {

    private final DroneRepository droneRepository;
    private final DroneBatteryHistoryRepository droneBatteryHistoryRepository;

    public void recordDronesBatteryCapacity() {
        Instant dateTime = Instant.now();
        List<DroneBatteryHistoryEntity> droneBatteryHistoryEntities = droneRepository.findAll().stream()
                .map(droneEntity -> DroneBatteryHistoryEntity.createNew(droneEntity.getId(), droneEntity.getBatteryCapacity(), dateTime))
                .collect(Collectors.toList());
        droneBatteryHistoryRepository.saveAll(droneBatteryHistoryEntities);
        log.info("Current drone battery capacities saved successfully.");
    }
}
