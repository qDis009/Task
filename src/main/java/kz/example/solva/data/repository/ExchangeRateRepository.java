package kz.example.solva.data.repository;

import kz.example.solva.data.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    Optional<ExchangeRate> findFirstByCurrencyFromAndCurrencyToOrderByCreatedDesc(String currencyFrom, String currencyTo);
}
