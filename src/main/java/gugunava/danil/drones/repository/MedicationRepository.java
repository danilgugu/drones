package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {

}
