package gugunava.danil.drones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "drone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    private String model;

    private int weightLimit;

    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState state;

    public static DroneEntity createNew(String serialNumber, String model, int weightLimit, int batteryCapacity, DroneState state) {
        return new DroneEntity(null, serialNumber, model, weightLimit, batteryCapacity, state);
    }
}
