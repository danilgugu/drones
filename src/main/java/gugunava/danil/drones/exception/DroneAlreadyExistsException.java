package gugunava.danil.drones.exception;

public class DroneAlreadyExistsException extends RuntimeException {

    public DroneAlreadyExistsException(String message) {
        super(message);
    }

    public static DroneAlreadyExistsException withSerialNumber(String serialNumber) {
        return new DroneAlreadyExistsException("Drone with serialNumber '" + serialNumber + "' already registered.");
    }
}
