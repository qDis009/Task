package kz.example.solva.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class LimitRequest {
    private double sum;
    private String currencyShortName;
    private String expenseCategory;
    private int userId;

    public double getScaleSum() {
        BigDecimal sum = BigDecimal.valueOf(this.sum);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        return sum.doubleValue();
    }
}
