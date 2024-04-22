package kz.example.solva.service;

import kz.example.solva.data.dto.TransactionDto;
import kz.example.solva.rest.request.TransactionRequest;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getLimitExceededTransactions(int userId);
    TransactionDto create(TransactionRequest model);
}
