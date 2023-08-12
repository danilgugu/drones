package gugunava.danil.drones.exception;

public class DroneBatteryIsLowException extends RuntimeException {

    public DroneBatteryIsLowException(String message) {
        super(message);
    }

    public static DroneBatteryIsLowException currentCapacity(int capacity) {
        return new DroneBatteryIsLowException("Drone battery is low: " + capacity + "%");
    }
}
