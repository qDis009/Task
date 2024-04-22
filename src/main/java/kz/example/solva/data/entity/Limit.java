package kz.example.solva.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(schema = "bank", name = "limit")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double sum;
    private double spentInMonth;
    private String currencyShortName;
    private String expenseCategory;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "limit")
    private Set<Transaction> transactions;

    public double getScaleSum() {
        BigDecimal sum = BigDecimal.valueOf(this.sum);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        return sum.doubleValue();
    }
}
