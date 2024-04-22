package kz.example.solva.service.impl;

import kz.example.solva.data.component.ExchangeRateComponent;
import kz.example.solva.data.component.LimitComponent;
import kz.example.solva.data.component.TransactionComponent;
import kz.example.solva.data.component.UserComponent;
import kz.example.solva.data.entity.Limit;
import kz.example.solva.data.entity.Transaction;
import kz.example.solva.mapper.TransactionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private TransactionComponent transactionComponent;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private LimitComponent limitComponent;
    @Mock
    private UserComponent userComponent;
    @Mock
    private ExchangeRateComponent exchangeRateComponent;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testCheckLimitExceededTransaction() {
        Transaction transaction = new Transaction();
        transaction.setExpenseCategory("service");
        transaction.setCurrencyShortName("USD");
        transaction.setSum(1000.15);
        Limit limit = new Limit();
        limit.setSum(1000d);
        when(limitComponent.findLastAddedLimitByCategoryInThisMonth(anyString(), any(LocalDateTime.class))).thenReturn(Optional.of(limit));
        transactionService.checkLimit(transaction);
        assertTrue(transaction.isLimitExceeded());
    }

    @Test
    public void testCheckLimitNotExceededTransaction() {
        Transaction transaction = new Transaction();
        transaction.setExpenseCategory("service");
        transaction.setCurrencyShortName("USD");
        transaction.setSum(100.48);
        Limit limit = new Limit();
        limit.setSpentInMonth(500d);
        limit.setSum(1000d);
        when(limitComponent.findLastAddedLimitByCategoryInThisMonth(anyString(), any(LocalDateTime.class))).thenReturn(Optional.of(limit));
        transactionService.checkLimit(transaction);
        assertFalse(transaction.isLimitExceeded());
    }

    @Test
    public void testCheckLimitExceededTransactionWithSpentInMonth() {
        Transaction transaction = new Transaction();
        transaction.setExpenseCategory("service");
        transaction.setCurrencyShortName("USD");
        transaction.setSum(150.96);
        Limit limit = new Limit();
        limit.setSpentInMonth(900d);
        limit.setSum(1000d);
        when(limitComponent.findLastAddedLimitByCategoryInThisMonth(anyString(), any(LocalDateTime.class))).thenReturn(Optional.of(limit));
        transactionService.checkLimit(transaction);
        assertTrue(transaction.isLimitExceeded());
    }
}
