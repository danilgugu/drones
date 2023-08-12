package gugunava.danil.drones.exception;

public class DroneReachedWeightLimitException extends RuntimeException {

    public DroneReachedWeightLimitException(String message) {
        super(message);
    }

    public static DroneReachedWeightLimitException withDefaultMessage() {
        return new DroneReachedWeightLimitException("Drone has reached weight limit.");
    }
}
