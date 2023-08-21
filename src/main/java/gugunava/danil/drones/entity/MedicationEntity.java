package gugunava.danil.drones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "medication")
@SequenceGenerator(name = "medication_gen", sequenceName = "medication_id_seq")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationEntity implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_gen")
    private Long id;

    private String name;

    private int weight;

    private String code;

    @Lob
    @Column(name = "_image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    public static MedicationEntity createNew(String name, int weight, String code, byte[] image) {
        return new MedicationEntity(null, name, weight, code, image);
    }
}
