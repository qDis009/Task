package kz.example.solva.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.example.solva.rest.response.ExchangeRateResponse;
import kz.example.solva.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class ExchangeRateClient {
    private final ExchangeRateService exchangeRateService;
    private static final String URL = "https://api.twelvedata.com/exchange_rate?symbol=KZT/USD&apikey=f5428103d19249babd9811ce0c137c90";

    @Scheduled(cron = "0 0 0 * * *")
    private void getExchangeRate() {
        try {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            ExchangeRateResponse exchangeRateResponse = objectMapper.readValue(response.toString(), ExchangeRateResponse.class);
            exchangeRateService.create(exchangeRateResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
