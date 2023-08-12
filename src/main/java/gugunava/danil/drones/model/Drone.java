package gugunava.danil.drones.model;

import gugunava.danil.drones.entity.DroneState;
import lombok.Value;

@Value
public class Drone {

    String serialNumber;

    String model;

    int weightLimit;

    int batteryCapacity;

    DroneState state;
}
