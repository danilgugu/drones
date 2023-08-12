package gugunava.danil.drones.controller;

import gugunava.danil.drones.command.LoadMedicationCommand;
import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.entity.DroneMedicationEntity;
import gugunava.danil.drones.entity.MedicationEntity;
import gugunava.danil.drones.generator.DroneEntityGenerator;
import gugunava.danil.drones.generator.LoadMedicationCommandGenerator;
import gugunava.danil.drones.generator.MedicationEntityGenerator;
import gugunava.danil.drones.util.ImageUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DroneController_LoadMedication_Test extends AbstractDroneControllerTest {

    private static final String API = "/medications";
    private static final String MEDICATION_IMAGE_FILE_PATH = "src/test/resources/image/medication_1.png";

    @Test
    void whenLoadValidMedication_thenStatusSuccess() throws Exception {
        LoadMedicationCommand command = LoadMedicationCommandGenerator.valid();
        DroneEntity droneEntity = DroneEntityGenerator.valid();
        MedicationEntity medicationEntity = MedicationEntityGenerator.valid();
        given(droneRepository.findBySerialNumber(droneEntity.getSerialNumber())).willReturn(Optional.of(droneEntity));
        given(droneRepository.findAllMedicationsByDroneIdWithoutImage(droneEntity.getId())).willReturn(Collections.emptyList());
        given(medicationRepository.save(any())).willReturn(medicationEntity);

        byte[] imageFileContent = Files.readAllBytes(Paths.get(MEDICATION_IMAGE_FILE_PATH));
        MockMultipartFile image = new MockMultipartFile("image", imageFileContent);
        MockMultipartFile commandAsBytes = new MockMultipartFile("command", "command", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(command));

        mockMvc.perform(multipart(BASE_URL + droneEntity.getSerialNumber() + API)
                        .file(image)
                        .file(commandAsBytes))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        verify(droneRepository).findBySerialNumber(droneEntity.getSerialNumber());
        verify(droneRepository).findAllMedicationsByDroneIdWithoutImage(droneEntity.getId());

        ArgumentCaptor<MedicationEntity> medicationEntityArgumentCaptor = ArgumentCaptor.forClass(MedicationEntity.class);
        verify(medicationRepository).save(medicationEntityArgumentCaptor.capture());
        MedicationEntity actualMedicationEntity = medicationEntityArgumentCaptor.getValue();
        then(actualMedicationEntity.getId()).isNull();
        then(actualMedicationEntity.getName()).isEqualTo(medicationEntity.getName());
        then(actualMedicationEntity.getWeight()).isEqualTo(medicationEntity.getWeight());
        then(actualMedicationEntity.getCode()).isEqualTo(medicationEntity.getCode());
        byte[] decompressedImage = ImageUtil.decompressImage(actualMedicationEntity.getImage());
        then(decompressedImage).isEqualTo(imageFileContent);

        DroneMedicationEntity expected = new DroneMedicationEntity(droneEntity.getId(), medicationEntity.getId());
        verify(droneMedicationRepository).save(expected);
    }

    @Test
    void whenLoadMedicationWithOverweight_thenStatusBadRequest() throws Exception {
        LoadMedicationCommand command = LoadMedicationCommandGenerator.overweight();
        DroneEntity droneEntity = DroneEntityGenerator.valid();
        given(droneRepository.findBySerialNumber(droneEntity.getSerialNumber())).willReturn(Optional.of(droneEntity));
        given(droneRepository.findAllMedicationsByDroneIdWithoutImage(droneEntity.getId())).willReturn(Collections.emptyList());

        byte[] imageFileContent = Files.readAllBytes(Paths.get(DroneController_LoadMedication_Test.MEDICATION_IMAGE_FILE_PATH));
        MockMultipartFile image = new MockMultipartFile("image", imageFileContent);
        MockMultipartFile commandAsBytes = new MockMultipartFile("command", "command", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(command));

        mockMvc.perform(multipart(BASE_URL + droneEntity.getSerialNumber() + API)
                        .file(image)
                        .file(commandAsBytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Drone has reached weight limit.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));

        verify(droneRepository).findBySerialNumber(droneEntity.getSerialNumber());
        verify(droneRepository).findAllMedicationsByDroneIdWithoutImage(droneEntity.getId());

        verify(medicationRepository, never()).save(any());
        verify(droneMedicationRepository, never()).save(any());
    }

    @Test
    void whenDroneStateIsNotIdleAndNotLoading_thenStatusBadRequest() throws Exception {
        LoadMedicationCommand command = LoadMedicationCommandGenerator.valid();
        DroneEntity droneEntity = DroneEntityGenerator.delivering();
        given(droneRepository.findBySerialNumber(droneEntity.getSerialNumber())).willReturn(Optional.of(droneEntity));

        byte[] imageFileContent = Files.readAllBytes(Paths.get(DroneController_LoadMedication_Test.MEDICATION_IMAGE_FILE_PATH));
        MockMultipartFile image = new MockMultipartFile("image", imageFileContent);
        MockMultipartFile commandAsBytes = new MockMultipartFile("command", "command", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(command));

        mockMvc.perform(multipart(BASE_URL + droneEntity.getSerialNumber() + API)
                        .file(image)
                        .file(commandAsBytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Current drone is in state 'DELIVERING'. Please use another drone.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));

        verify(droneRepository).findBySerialNumber(droneEntity.getSerialNumber());

        verify(droneRepository, never()).findAllMedicationsByDroneIdWithoutImage(anyLong());
        verify(medicationRepository, never()).save(any());
        verify(droneMedicationRepository, never()).save(any());
    }

    @Test
    void whenDroneBatteryIsLow_thenStatusBadRequest() throws Exception {
        LoadMedicationCommand command = LoadMedicationCommandGenerator.valid();
        DroneEntity droneEntity = DroneEntityGenerator.lowBattery();
        given(droneRepository.findBySerialNumber(droneEntity.getSerialNumber())).willReturn(Optional.of(droneEntity));

        byte[] imageFileContent = Files.readAllBytes(Paths.get(DroneController_LoadMedication_Test.MEDICATION_IMAGE_FILE_PATH));
        MockMultipartFile image = new MockMultipartFile("image", imageFileContent);
        MockMultipartFile commandAsBytes = new MockMultipartFile("command", "command", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(command));

        mockMvc.perform(multipart(BASE_URL + droneEntity.getSerialNumber() + API)
                        .file(image)
                        .file(commandAsBytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Drone battery is low: 21%")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));

        verify(droneRepository).findBySerialNumber(droneEntity.getSerialNumber());

        verify(droneRepository, never()).findAllMedicationsByDroneIdWithoutImage(anyLong());
        verify(medicationRepository, never()).save(any());
        verify(droneMedicationRepository, never()).save(any());
    }

    @Test
    void whenDroneNotFound_thenStatusNotFound() throws Exception {
        LoadMedicationCommand command = LoadMedicationCommandGenerator.valid();
        String droneSerialNumber = "not_exists";
        given(droneRepository.findBySerialNumber(droneSerialNumber)).willReturn(Optional.empty());

        byte[] imageFileContent = Files.readAllBytes(Paths.get(DroneController_LoadMedication_Test.MEDICATION_IMAGE_FILE_PATH));
        MockMultipartFile image = new MockMultipartFile("image", imageFileContent);
        MockMultipartFile commandAsBytes = new MockMultipartFile("command", "command", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(command));

        mockMvc.perform(multipart(BASE_URL + droneSerialNumber + API)
                        .file(image)
                        .file(commandAsBytes))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Drone with serialNumber 'not_exists' not found.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));

        verify(droneRepository).findBySerialNumber(droneSerialNumber);

        verify(droneRepository, never()).findAllMedicationsByDroneIdWithoutImage(anyLong());
        verify(medicationRepository, never()).save(any());
        verify(droneMedicationRepository, never()).save(any());
    }

    @Test
    void whenCommandIsInvalid_thenStatusBadRequest() throws Exception {
        LoadMedicationCommand command = LoadMedicationCommandGenerator.invalid();
        String droneSerialNumber = "SN";

        byte[] imageFileContent = Files.readAllBytes(Paths.get(DroneController_LoadMedication_Test.MEDICATION_IMAGE_FILE_PATH));
        MockMultipartFile image = new MockMultipartFile("image", imageFileContent);
        MockMultipartFile commandAsBytes = new MockMultipartFile("command", "command", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(command));

        mockMvc.perform(multipart(BASE_URL + droneSerialNumber + API)
                        .file(image)
                        .file(commandAsBytes))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Invalid request: ")))
                .andExpect(jsonPath("$.message", containsString("name")))
                .andExpect(jsonPath("$.message", containsString("Invalid medication name.")))
                .andExpect(jsonPath("$.message", containsString("weight")))
                .andExpect(jsonPath("$.message", containsString("Please specify medication weight.")))
                .andExpect(jsonPath("$.message", containsString("code")))
                .andExpect(jsonPath("$.message", containsString("Invalid medication code.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));

        verify(droneRepository, never()).findBySerialNumber(anyString());
        verify(droneRepository, never()).findAllMedicationsByDroneIdWithoutImage(anyLong());
        verify(medicationRepository, never()).save(any());
        verify(droneMedicationRepository, never()).save(any());
    }
}
