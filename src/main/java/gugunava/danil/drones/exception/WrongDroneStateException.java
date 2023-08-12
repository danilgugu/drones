package gugunava.danil.drones.exception;

import gugunava.danil.drones.entity.DroneState;

public class WrongDroneStateException extends RuntimeException {

    public WrongDroneStateException(String message) {
        super(message);
    }

    public static WrongDroneStateException currentState(DroneState state) {
        return new WrongDroneStateException(String.format("Current drone is in state '%s'. Please use another drone.", state));
    }
}
