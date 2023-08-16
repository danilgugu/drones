package gugunava.danil.drones.service;

import gugunava.danil.drones.command.LoadMedicationCommand;
import gugunava.danil.drones.command.RegisterDroneCommand;
import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.entity.DroneMedicationEntity;
import gugunava.danil.drones.entity.MedicationEntity;
import gugunava.danil.drones.exception.*;
import gugunava.danil.drones.model.Drone;
import gugunava.danil.drones.model.Medication;
import gugunava.danil.drones.repository.DroneMedicationRepository;
import gugunava.danil.drones.repository.DroneRepository;
import gugunava.danil.drones.repository.MedicationRepository;
import gugunava.danil.drones.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static gugunava.danil.drones.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneMedicationRepository droneMedicationRepository;
    private final ConversionService conversionService;

    public Drone register(RegisterDroneCommand command) {
        if (droneRepository.existsBySerialNumber(command.getSerialNumber())) {
            throw DroneAlreadyExistsException.withSerialNumber(command.getSerialNumber());
        }
        DroneEntity droneEntity = DroneEntity.createNew(command.getSerialNumber(), command.getModel(), command.getWeightLimit(), INITIAL_BATTERY_CAPACITY, INITIAL_DRONE_STATE);
        DroneEntity saved = droneRepository.save(droneEntity);
        return conversionService.convert(saved, Drone.class);
    }

    @Transactional
    public void loadMedication(String serialNumber, LoadMedicationCommand command, MultipartFile image) {
        DroneEntity droneEntity = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> DroneNotFoundException.bySerialNumber(serialNumber));
        if (droneEntity.getBatteryCapacity() < MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING) {
            throw DroneBatteryIsLowException.currentCapacity(droneEntity.getBatteryCapacity());
        }
        if (!STATES_ALLOWED_FOR_LOADING.contains(droneEntity.getState())) {
            throw WrongDroneStateException.currentState(droneEntity.getState());
        }
        int loadedMedicationsWeight = droneRepository.findAllMedicationsByDroneIdWithoutImage(droneEntity.getId()).stream()
                .map(MedicationEntity::getWeight)
                .mapToInt(Integer::valueOf)
                .sum();
        if (droneEntity.getWeightLimit() < loadedMedicationsWeight + command.getWeight()) {
            throw DroneReachedWeightLimitException.withDefaultMessage();
        }
        byte[] imageBytes;
        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            throw new ReadImageFileException(e);
        }
        byte[] compressedImage = ImageUtil.compressImage(imageBytes);
        MedicationEntity medicationEntity = MedicationEntity.createNew(command.getName(), command.getWeight(), command.getCode(), compressedImage);
        MedicationEntity loadedMedication = medicationRepository.save(medicationEntity);
        DroneMedicationEntity droneMedicationEntity = new DroneMedicationEntity(droneEntity.getId(), loadedMedication.getId());
        droneMedicationRepository.save(droneMedicationEntity);
    }

    public List<Medication> getMedications(String serialNumber) {
        DroneEntity droneEntity = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> DroneNotFoundException.bySerialNumber(serialNumber));
        return droneRepository.findAllMedicationsByDroneIdWithoutImage(droneEntity.getId()).stream()
                .map(medicationEntity -> conversionService.convert(medicationEntity, Medication.class))
                .collect(Collectors.toList());
    }

    public List<Drone> getAvailableForLoadingDrones() {
        return droneRepository.findAllByBatteryCapacityGreaterThanEqualAndStateIn(MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING, STATES_ALLOWED_FOR_LOADING).stream()
                .map(droneEntity -> conversionService.convert(droneEntity, Drone.class))
                .collect(Collectors.toList());
    }

    public int getBatteryLevel(String serialNumber) {
        return droneRepository.findBySerialNumber(serialNumber)
                .map(DroneEntity::getBatteryCapacity)
                .orElseThrow(() -> DroneNotFoundException.bySerialNumber(serialNumber));
    }
}
