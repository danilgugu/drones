package gugunava.danil.drones.generator;

import gugunava.danil.drones.entity.DroneEntity;

import java.util.List;

import static gugunava.danil.drones.constants.Constants.INITIAL_BATTERY_CAPACITY;
import static gugunava.danil.drones.constants.Constants.INITIAL_DRONE_STATE;
import static gugunava.danil.drones.entity.DroneState.DELIVERING;

public final class DroneEntityGenerator {

	public static DroneEntity valid() {
		return new DroneEntity(
				1L,
				"1000",
				"Heavyweight",
				450,
				INITIAL_BATTERY_CAPACITY,
				INITIAL_DRONE_STATE
		);
	}

	public static DroneEntity other() {
		return new DroneEntity(
				2L,
				"500",
				"Lightweight",
				150,
				INITIAL_BATTERY_CAPACITY,
				INITIAL_DRONE_STATE
		);
	}

	public static List<DroneEntity> list() {
		return List.of(valid(), other());
	}

	public static DroneEntity delivering() {
		return new DroneEntity(
				1L,
				"1000",
				"Heavyweight",
				450,
				INITIAL_BATTERY_CAPACITY,
				DELIVERING
		);
	}

	public static DroneEntity lowBattery() {
		return new DroneEntity(
				1L,
				"1000",
				"Heavyweight",
				450,
				21,
				INITIAL_DRONE_STATE
		);
	}
}
