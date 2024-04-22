package kz.example.solva.data.component.impl;

import kz.example.solva.data.component.LimitComponent;
import kz.example.solva.data.entity.Limit;
import kz.example.solva.data.repository.LimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LimitComponentImpl implements LimitComponent {
    private final LimitRepository limitRepository;

    @Override
    public Limit create(Limit limit) {
        return limitRepository.save(limit);
    }

    @Override
    public Limit update(Limit limit) {
        return limitRepository.save(limit);
    }

    @Override
    public Optional<Limit> findLastAddedLimitByCategoryInThisMonth(String expenseCategory, LocalDateTime createdAfter) {
        return limitRepository.findFirstByExpenseCategoryAndCreatedAfterOrderByCreatedDesc(expenseCategory, createdAfter);
    }
}
