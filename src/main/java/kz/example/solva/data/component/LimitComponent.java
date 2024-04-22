package kz.example.solva.data.component;

import kz.example.solva.data.entity.Limit;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LimitComponent {
    Limit create(Limit limit);

    Limit update(Limit limit);

    Optional<Limit> findLastAddedLimitByCategoryInThisMonth(String expenseCategory, LocalDateTime createdAfter);
}
