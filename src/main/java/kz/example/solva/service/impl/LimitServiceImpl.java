package kz.example.solva.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.example.solva.data.component.ExchangeRateComponent;
import kz.example.solva.data.component.LimitComponent;
import kz.example.solva.data.component.UserComponent;
import kz.example.solva.data.dto.LimitDto;
import kz.example.solva.data.entity.ExchangeRate;
import kz.example.solva.data.entity.Limit;
import kz.example.solva.data.entity.User;
import kz.example.solva.mapper.LimitMapper;
import kz.example.solva.rest.request.LimitRequest;
import kz.example.solva.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LimitServiceImpl implements LimitService {
    private final LimitComponent limitComponent;
    private final LimitMapper limitMapper;
    private final UserComponent userComponent;
    private final ExchangeRateComponent exchangeRateComponent;

    @Override
    public LimitDto create(LimitRequest model) {
        double spentInMonth = getSpentInMonth(model.getExpenseCategory());
        Limit limit = limitComponent.create(limitMapper.mapLimitRequestToLimit(model));
        User user = userComponent.findById(model.getUserId());
        limit.setUser(user);
        limit.setSpentInMonth(spentInMonth);
        if (!limit.getCurrencyShortName().equals("USD")) {
            limit.setSum(convertLimitCurrencyInUSD(limit));
            limit.setCurrencyShortName("USD");
        }
        limitComponent.update(limit);
        return limitMapper.mapLimitToLimitDto(limit);
    }

    private double getSpentInMonth(String expenseCategory) {
        LocalDateTime createdAfter = getStartOfMonth();
        Optional<Limit> limit = limitComponent.findLastAddedLimitByCategoryInThisMonth(expenseCategory, createdAfter);
        return limit.map(Limit::getSpentInMonth).orElse(0.0);
    }

    private double convertLimitCurrencyInUSD(Limit limit) {
        Optional<ExchangeRate> exchangeRate = exchangeRateComponent.findExchangeRateByCurrencyFromAndCurrencyTo(limit.getCurrencyShortName(), "USD");
        if (exchangeRate.isPresent()) {
            double closeRate = exchangeRate.get().getCloseRate();
            if (closeRate == 0) {
                closeRate = exchangeRate.get().getPreviousCloseRate();
            }
            return getTransactionScaleSum(closeRate * limit.getSum());
        } else {
            throw new EntityNotFoundException("Обменный курс для указанной валюты не найден");
        }
    }

    private double getTransactionScaleSum(double tractionSum) {
        BigDecimal sum = BigDecimal.valueOf(tractionSum);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        return sum.doubleValue();
    }

    private LocalDateTime getStartOfMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
        return LocalDateTime.of(firstDayOfMonth, LocalTime.MIN);
    }
}
