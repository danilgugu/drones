package gugunava.danil.drones.repository;

import gugunava.danil.drones.entity.MedicationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomDroneRepositoryImpl implements CustomDroneRepository {

    private final EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<MedicationEntity> findAllMedicationsByDroneIdWithoutImage(Long droneId) {
        Query query = entityManager.createNativeQuery("select m.id, m.name, m.weight, m.code, null as _image from medication m " +
                "join drone_medication dm on dm.medication_id = m.id " +
                "join drone d on d.id = dm.drone_id " +
                "where d.id = :droneId", MedicationEntity.class);
        query.setParameter("droneId", droneId);
        return query.getResultList();
    }
}
