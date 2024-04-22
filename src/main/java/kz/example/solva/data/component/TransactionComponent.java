package kz.example.solva.data.component;

import kz.example.solva.data.entity.Transaction;

import java.util.List;

public interface TransactionComponent {
    Transaction create(Transaction transaction);
    List<Transaction> getLimitExceededTransaction(int userId);
    Transaction update(Transaction transaction);
}
