package gugunava.danil.drones.converter;

import gugunava.danil.drones.entity.DroneEntity;
import gugunava.danil.drones.model.Drone;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DroneEntityToDroneConverter implements Converter<DroneEntity, Drone> {

    @Override
    public Drone convert(DroneEntity droneEntity) {
        return new Drone(
                droneEntity.getSerialNumber(),
                droneEntity.getModel(),
                droneEntity.getWeightLimit(),
                droneEntity.getBatteryCapacity(),
                droneEntity.getState()
        );
    }
}
