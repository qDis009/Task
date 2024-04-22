package kz.example.solva.data.repository;

import kz.example.solva.data.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("select t from Transaction t join fetch t.limit where t.user.id=:userId")
    List<Transaction> findAllLimitExceededTransactionsByUserId(@Param("userId") int userId);
}