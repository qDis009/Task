package kz.example.solva.data.component;

import kz.example.solva.data.entity.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateComponent {
    Optional<ExchangeRate> findExchangeRateByCurrencyFromAndCurrencyTo(String currencyFrom, String currencyTo);

    ExchangeRate create(ExchangeRate exchangeRate);
}
