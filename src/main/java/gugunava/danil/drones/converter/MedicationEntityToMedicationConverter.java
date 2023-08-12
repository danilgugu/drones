package gugunava.danil.drones.converter;

import gugunava.danil.drones.entity.MedicationEntity;
import gugunava.danil.drones.model.Medication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MedicationEntityToMedicationConverter implements Converter<MedicationEntity, Medication> {

    @Override
    public Medication convert(MedicationEntity medicationEntity) {
        return new Medication(medicationEntity.getName(), medicationEntity.getWeight(), medicationEntity.getCode());
    }
}
