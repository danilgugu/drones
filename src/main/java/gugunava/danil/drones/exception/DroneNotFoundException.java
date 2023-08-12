package gugunava.danil.drones.exception;

public class DroneNotFoundException extends RuntimeException {

    public DroneNotFoundException(String message) {
        super(message);
    }

    public static DroneNotFoundException bySerialNumber(String serialNumber) {
        return new DroneNotFoundException("Drone with serialNumber '" + serialNumber + "' not found.");
    }
}
