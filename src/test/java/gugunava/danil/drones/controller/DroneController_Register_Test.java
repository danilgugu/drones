package gugunava.danil.drones.controller;

import gugunava.danil.drones.command.RegisterDroneCommand;
import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.generator.DroneEntityGenerator;
import gugunava.danil.drones.generator.RegisterDroneCommandGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DroneController_Register_Test extends AbstractDroneControllerTest {

    @Test
    void whenRegisterValidDrone_thenStatusSuccess() throws Exception {
        RegisterDroneCommand command = RegisterDroneCommandGenerator.valid();
        DroneEntity droneEntity = DroneEntityGenerator.valid();
        given(droneRepository.existsBySerialNumber(command.getSerialNumber())).willReturn(false);
        given(droneRepository.save(any())).willReturn(droneEntity);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber", is(droneEntity.getSerialNumber())))
                .andExpect(jsonPath("$.model", is(droneEntity.getModel())))
                .andExpect(jsonPath("$.weightLimit", is(droneEntity.getWeightLimit())))
                .andExpect(jsonPath("$.batteryCapacity", is(droneEntity.getBatteryCapacity())))
                .andExpect(jsonPath("$.state", is(droneEntity.getState().name())));

        verify(droneRepository).existsBySerialNumber(command.getSerialNumber());
        ArgumentCaptor<DroneEntity> droneEntityArgumentCaptor = ArgumentCaptor.forClass(DroneEntity.class);
        verify(droneRepository).save(droneEntityArgumentCaptor.capture());
        DroneEntity actual = droneEntityArgumentCaptor.getValue();
        then(actual.getId()).isNull();
        then(actual.getSerialNumber()).isEqualTo(droneEntity.getSerialNumber());
        then(actual.getModel()).isEqualTo(droneEntity.getModel());
        then(actual.getWeightLimit()).isEqualTo(droneEntity.getWeightLimit());
        then(actual.getBatteryCapacity()).isEqualTo(droneEntity.getBatteryCapacity());
        then(actual.getState()).isEqualTo(droneEntity.getState());
    }

    @Test
    void whenRegisterExistingDrone_thenStatusSuccess() throws Exception {
        RegisterDroneCommand command = RegisterDroneCommandGenerator.valid();
        given(droneRepository.existsBySerialNumber(command.getSerialNumber())).willReturn(true);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Drone with serialNumber '1000' already registered.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));

        verify(droneRepository).existsBySerialNumber(command.getSerialNumber());
        verify(droneRepository, never()).save(any());
    }

    @Test
    void whenRegisterInvalidDrone_thenStatusBadRequest() throws Exception {
        RegisterDroneCommand command = RegisterDroneCommandGenerator.invalid();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Invalid request: ")))
                .andExpect(jsonPath("$.message", containsString("serialNumber")))
                .andExpect(jsonPath("$.message", containsString("Serial number is too long.")))
                .andExpect(jsonPath("$.message", containsString("weightLimit")))
                .andExpect(jsonPath("$.message", containsString("Weight limit cannot be more than 500 gr")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));

        verify(droneRepository, never()).existsBySerialNumber(anyString());
        verify(droneRepository, never()).save(any());
    }
}
