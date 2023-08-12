package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.DroneBatteryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneBatteryHistoryRepository extends JpaRepository<DroneBatteryHistoryEntity, Long> {

}
