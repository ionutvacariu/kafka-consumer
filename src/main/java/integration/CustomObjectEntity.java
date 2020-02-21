package integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CustomObject")
@Data
@Builder
@AllArgsConstructor
public class CustomObjectEntity {

    public CustomObjectEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    private String firstname;

    private String lastname;

    private Integer age;
}
