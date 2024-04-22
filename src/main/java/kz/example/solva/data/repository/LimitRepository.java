package kz.example.solva.data.repository;

import kz.example.solva.data.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Integer> {
    Optional<Limit> findFirstByExpenseCategoryAndCreatedAfterOrderByCreatedDesc(String expenseCategory, LocalDateTime createdAfter);
}
