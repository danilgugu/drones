package gugunava.danil.drones.generator;

import gugunava.danil.drones.entity.MedicationEntity;

import java.util.List;

public final class MedicationEntityGenerator {

    public static MedicationEntity valid() {
        return new MedicationEntity(1L, "medication_1", 30, "HC_500", "image".getBytes());
    }

    public static MedicationEntity other() {
        return new MedicationEntity(2L, "medication_2", 90, "DF_1000", "image_2".getBytes());
    }

    public static List<MedicationEntity> list() {
        return List.of(valid(), other());
    }
}
