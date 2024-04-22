package kz.example.solva.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(schema = "bank", name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long accountFrom;
    private long accountTo;
    private String currencyShortName;
    private double sum;
    private String expenseCategory;
    private boolean limitExceeded = false;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime completed;
    @ManyToOne
    @JoinColumn(name = "limit_id", referencedColumnName = "id")
    private Limit limit;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public double getScaleSum() {
        BigDecimal sum = BigDecimal.valueOf(this.sum);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        return sum.doubleValue();
    }
}
