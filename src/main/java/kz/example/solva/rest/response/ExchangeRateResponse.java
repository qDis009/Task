package kz.example.solva.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExchangeRateResponse {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("rate")
    private double rate;
    @JsonProperty("timestamp")
    private long timestamp;
}
