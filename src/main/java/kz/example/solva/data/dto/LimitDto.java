package kz.example.solva.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LimitDto {
    private int id;
    private double sum;
    private String currencyShortName;
    private String expenseCategory;
    private int userId;
    private String created;
}
