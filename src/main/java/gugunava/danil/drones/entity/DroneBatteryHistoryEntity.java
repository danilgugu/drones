package gugunava.danil.drones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "drone_battery_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneBatteryHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long droneId;

    private Integer batteryLevel;

    private Instant dateTime;

    public static DroneBatteryHistoryEntity createNew(Long droneId, Integer batteryLevel, Instant dateTime) {
        return new DroneBatteryHistoryEntity(null, droneId, batteryLevel, dateTime);
    }
}
