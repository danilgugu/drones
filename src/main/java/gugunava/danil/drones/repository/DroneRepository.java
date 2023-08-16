package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.entity.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<DroneEntity, Long>, CustomDroneRepository {

    Optional<DroneEntity> findBySerialNumber(String serialNumber);

    List<DroneEntity> findAllByBatteryCapacityGreaterThanEqualAndStateIn(int batteryCapacity, List<DroneState> states);

    boolean existsBySerialNumber(String serialNumber);
}
