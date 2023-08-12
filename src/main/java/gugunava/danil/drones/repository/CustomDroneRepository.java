package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.MedicationEntity;

import java.util.List;

public interface CustomDroneRepository {

    List<MedicationEntity> findAllMedicationsByDroneIdWithoutImage(Long droneId);
}
