package gugunava.danil.drones.constants;

import gugunava.danil.drones.entity.DroneState;

import java.util.List;

public final class Constants {

    public static final int INITIAL_BATTERY_CAPACITY = 100;
    public static final DroneState INITIAL_DRONE_STATE = DroneState.IDLE;
    public static final int MINIMUM_BATTERY_CAPACITY_NEEDED_FOR_LOADING = 25;
    public static final List<DroneState> STATES_ALLOWED_FOR_LOADING = List.of(DroneState.IDLE, DroneState.LOADING);
}
