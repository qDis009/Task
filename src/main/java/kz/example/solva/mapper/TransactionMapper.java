package kz.example.solva.mapper;

import kz.example.solva.data.dto.TransactionDto;
import kz.example.solva.data.entity.Transaction;
import kz.example.solva.rest.request.TransactionRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {LimitMapper.class})
public interface TransactionMapper {
    @Mapping(target = "sum", expression = "java(transaction.getScaleSum())")
    TransactionDto mapTransactionToTransactionDto(Transaction transaction);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "sum", expression = "java(transactionRequest.getScaleSum())")
    Transaction mapTransactionRequestToTransaction(TransactionRequest transactionRequest);
}
