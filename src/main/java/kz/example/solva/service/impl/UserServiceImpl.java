package kz.example.solva.service.impl;

import kz.example.solva.data.component.UserComponent;
import kz.example.solva.data.dto.UserDto;
import kz.example.solva.mapper.UserMapper;
import kz.example.solva.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(int id) {
        return userMapper.mapUserToUserDto(userComponent.findById(id));
    }
}
