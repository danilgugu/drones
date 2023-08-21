package gugunava.danil.drones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "drone_battery_history")
@SequenceGenerator(name = "drone_battery_history_gen", sequenceName = "drone_battery_history_id_seq")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneBatteryHistoryEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drone_battery_history_gen")
    private Long id;

    private Long droneId;

    private Integer batteryLevel;

    private Instant dateTime;

    public static DroneBatteryHistoryEntity createNew(Long droneId, Integer batteryLevel, Instant dateTime) {
        return new DroneBatteryHistoryEntity(null, droneId, batteryLevel, dateTime);
    }
}
