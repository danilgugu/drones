package gugunava.danil.drones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "drone_medication")
@IdClass(DroneMedicationEntity.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneMedicationEntity implements Serializable {

    @Id
    private Long droneId;

    @Id
    private Long medicationId;
}
