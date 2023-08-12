package gugunava.danil.drones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medication")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int weight;

    private String code;

    @Lob
    @Column(name = "_image", columnDefinition = "BLOB")
    private byte[] image;

    public static MedicationEntity createNew(String name, int weight, String code, byte[] image) {
        return new MedicationEntity(null, name, weight, code, image);
    }
}
