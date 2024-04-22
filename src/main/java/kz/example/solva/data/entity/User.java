package kz.example.solva.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@Table(schema = "bank",name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    @OneToMany(mappedBy = "user")
    private Set<Limit> limits;
    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions;
}
