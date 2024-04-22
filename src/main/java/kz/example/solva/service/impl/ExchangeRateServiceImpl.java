package kz.example.solva.service.impl;

import kz.example.solva.data.component.ExchangeRateComponent;
import kz.example.solva.data.entity.ExchangeRate;
import kz.example.solva.rest.response.ExchangeRateResponse;
import kz.example.solva.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateComponent exchangeRateComponent;

    @Override
    public void create(ExchangeRateResponse response) {
        String[] currencies = response.getSymbol().split("/");
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCloseRate(response.getRate());
        exchangeRate.setCurrencyFrom(currencies[0]);
        exchangeRate.setCurrencyTo(currencies[1]);
        exchangeRateComponent.create(exchangeRate);
    }
}
