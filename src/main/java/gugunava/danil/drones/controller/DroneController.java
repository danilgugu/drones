package gugunava.danil.drones.controller;

import gugunava.danil.drones.command.LoadMedicationCommand;
import gugunava.danil.drones.command.RegisterDroneCommand;
import gugunava.danil.drones.model.Drone;
import gugunava.danil.drones.model.Medication;
import gugunava.danil.drones.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("drones")
public class DroneController {

    private final DroneService droneService;

    @PostMapping
    public Drone register(@RequestBody @Valid RegisterDroneCommand registerDroneCommand) {
        return droneService.register(registerDroneCommand);
    }

    @PostMapping("/{serialNumber}/medications")
    public void loadMedication(
            @PathVariable("serialNumber") String serialNumber,
            @RequestPart("command") @Valid LoadMedicationCommand command,
            @RequestPart("image") MultipartFile image
    ) {
        droneService.loadMedication(serialNumber, command, image);
    }

    @GetMapping("/{serialNumber}/medications")
    public List<Medication> getMedications(@PathVariable("serialNumber") String serialNumber) {
        return droneService.getMedications(serialNumber);
    }

    @GetMapping("/available")
    public List<Drone> getAvailableForLoadingDrones() {
        return droneService.getAvailableForLoadingDrones();
    }

    @GetMapping("/{serialNumber}/battery")
    public int getBatteryLevel(@PathVariable("serialNumber") String serialNumber) {
        return droneService.getBatteryLevel(serialNumber);
    }
}
