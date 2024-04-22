package kz.example.solva.data.component.impl;

import kz.example.solva.data.component.ExchangeRateComponent;
import kz.example.solva.data.entity.ExchangeRate;
import kz.example.solva.data.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateComponentImpl implements ExchangeRateComponent {
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public Optional<ExchangeRate> findExchangeRateByCurrencyFromAndCurrencyTo(String currencyFrom, String currencyTo) {
        return exchangeRateRepository.findFirstByCurrencyFromAndCurrencyToOrderByCreatedDesc(currencyFrom, currencyTo);
    }

    @Override
    public ExchangeRate create(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }
}
