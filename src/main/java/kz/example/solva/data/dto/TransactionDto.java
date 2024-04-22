package kz.example.solva.data.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDto {
    private int id;
    private long accountFrom;
    private long accountTo;
    private String currencyShortName;
    private double sum;
    private String expenseCategory;
    private boolean limitExceeded = false;
    private String completed;
    private LimitDto limit;
}
