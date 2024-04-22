package kz.example.solva.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class TransactionRequest {
    private int userId;
    private long accountFrom;
    private long accountTo;
    private String currencyShortName;
    private double sum;
    private String expenseCategory;

    public double getScaleSum() {
        BigDecimal sum = BigDecimal.valueOf(this.sum);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        return sum.doubleValue();
    }
}
