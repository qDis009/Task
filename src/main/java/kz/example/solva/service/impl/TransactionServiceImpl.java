package kz.example.solva.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.example.solva.data.component.ExchangeRateComponent;
import kz.example.solva.data.component.LimitComponent;
import kz.example.solva.data.component.TransactionComponent;
import kz.example.solva.data.component.UserComponent;
import kz.example.solva.data.dto.TransactionDto;
import kz.example.solva.data.entity.ExchangeRate;
import kz.example.solva.data.entity.Limit;
import kz.example.solva.data.entity.Transaction;
import kz.example.solva.data.entity.User;
import kz.example.solva.mapper.TransactionMapper;
import kz.example.solva.rest.request.TransactionRequest;
import kz.example.solva.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionComponent transactionComponent;
    private final LimitComponent limitComponent;
    private final TransactionMapper transactionMapper;
    private final UserComponent userComponent;
    private final ExchangeRateComponent exchangeRateComponent;

    @Override
    public List<TransactionDto> getLimitExceededTransactions(int userId) {
        return transactionComponent.getLimitExceededTransaction(userId).stream().
                map(transactionMapper::mapTransactionToTransactionDto).toList();
    }

    @Override
    public TransactionDto create(TransactionRequest model) {
        Transaction transaction = transactionComponent.create(transactionMapper.mapTransactionRequestToTransaction(model));
        User user = userComponent.findById(model.getUserId());
        transaction.setUser(user);
        transactionComponent.update(transaction);
        checkLimit(transaction);
        return transactionMapper.mapTransactionToTransactionDto(transaction);
    }

    private void checkLimit(Transaction transaction) {
        LocalDateTime createdAfter = getStartOfMonth();
        Optional<Limit> limit = limitComponent.findLastAddedLimitByCategoryInThisMonth(transaction.getExpenseCategory(), createdAfter);
        double transactionSumInUSD = transaction.getSum();
        if (!transaction.getCurrencyShortName().equals("USD")) {
            transactionSumInUSD = convertTransactionCurrencyInUSD(transaction);
        }
        Limit lastLimit;
        if (limit.isPresent()) {
            lastLimit = limit.get();
            lastLimit.setSpentInMonth(lastLimit.getSpentInMonth() + transactionSumInUSD);
        } else {
            lastLimit = createDefaultLimit(transaction, transactionSumInUSD);
        }
        limitComponent.update(lastLimit);
        if (lastLimit.getSpentInMonth() > lastLimit.getSum()) {
            transaction.setLimit(lastLimit);
            transaction.setLimitExceeded(true);
        }
        transactionComponent.update(transaction);
    }

    private LocalDateTime getStartOfMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
        return LocalDateTime.of(firstDayOfMonth, LocalTime.MIN);
    }

    private Limit createDefaultLimit(Transaction transaction, double transactionSumInUSD) {
        Limit defaultLimit = new Limit();
        defaultLimit.setUser(transaction.getUser());
        defaultLimit.setSum(1000d);
        defaultLimit.setCurrencyShortName("USD");
        defaultLimit.setExpenseCategory(transaction.getExpenseCategory());
        defaultLimit.setSpentInMonth(transactionSumInUSD);
        return limitComponent.create(defaultLimit);
    }

    private double convertTransactionCurrencyInUSD(Transaction transaction) {
        Optional<ExchangeRate> exchangeRate = exchangeRateComponent.findExchangeRateByCurrencyFromAndCurrencyTo(transaction.getCurrencyShortName(), "USD");
        if (exchangeRate.isPresent()) {
            double closeRate = exchangeRate.get().getCloseRate();
            if (closeRate == 0) {
                closeRate = exchangeRate.get().getPreviousCloseRate();
            }
            return getTransactionScaleSum(closeRate * transaction.getSum());
        } else {
            throw new EntityNotFoundException("Обменный курс для указанной валюты не найден");
        }
    }

    private double getTransactionScaleSum(double tractionSum) {
        BigDecimal sum = BigDecimal.valueOf(tractionSum);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        return sum.doubleValue();
    }
}