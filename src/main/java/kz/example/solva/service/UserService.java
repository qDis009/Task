package kz.example.solva.service;

import kz.example.solva.data.dto.UserDto;


public interface UserService {
    UserDto getById(int id);
}
