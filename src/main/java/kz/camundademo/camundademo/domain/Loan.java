package kz.camundademo.camundademo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter

@Entity(name = "loan")
@Table(schema = "public", name = "loans")
public class Loan {
    @Id
    private UUID id;
    private String comment;
    private String interestRate;
    private String amount;
    private String finalAmount;
    private String monthPayment;
    private Boolean approved;
    private LocalDateTime dateFinish;

    @PrePersist
    void prePersist() {
        id = UUID.randomUUID();
    }
}
