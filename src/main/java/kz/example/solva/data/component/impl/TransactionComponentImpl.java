package kz.example.solva.data.component.impl;

import kz.example.solva.data.component.TransactionComponent;
import kz.example.solva.data.entity.Transaction;
import kz.example.solva.data.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionComponentImpl implements TransactionComponent {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getLimitExceededTransaction(int userId) {
        return transactionRepository.findAllLimitExceededTransactionsByUserId(userId);
    }

    @Override
    public Transaction update(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
