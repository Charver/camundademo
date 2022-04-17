package kz.camundademo.camundademo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(schema = "public", name = "interest")
@Entity(name = "interest")
public class Interest {
    @Id
    private UUID id;
    private Integer monthAmount;
    private Double interestRate;

    @PrePersist
    void prePersist() {
        id = UUID.randomUUID();
    }
}
