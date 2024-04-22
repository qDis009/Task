package kz.example.solva.service;

import kz.example.solva.data.dto.LimitDto;
import kz.example.solva.rest.request.LimitRequest;

public interface LimitService {
    LimitDto create(LimitRequest model);
}
