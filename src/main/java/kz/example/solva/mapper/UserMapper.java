package kz.example.solva.mapper;

import kz.example.solva.data.dto.UserDto;
import kz.example.solva.data.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapUserToUserDto(User user);
}
