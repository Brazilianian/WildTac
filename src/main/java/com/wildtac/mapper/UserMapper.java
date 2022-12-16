package com.wildtac.mapper;

import com.wildtac.domain.user.User;
import com.wildtac.dto.user.registration.UserRegistrationAbstractDto;
import com.wildtac.dto.user.registration.UserRegistrationResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserMapper implements StructMapper<UserRegistrationAbstractDto, User>{

    private final ModelMapper modelMapper;

    @Override
    public List<User> fromDtoListToObjectList(List<UserRegistrationAbstractDto> userAbstractDtoList) {
        return modelMapper.map(userAbstractDtoList, new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public List<UserRegistrationAbstractDto> fromObjectListToDtoList(List<User> userList) {
        return modelMapper.map(userList, new TypeToken<List<UserRegistrationResponseDto>>() {}.getType());
    }

    @Override
    public User fromDtoToObject(UserRegistrationAbstractDto userAbstractDto) {
        return modelMapper.map(userAbstractDto, User.class);
    }

    @Override
    public UserRegistrationAbstractDto fromObjectToDto(User user) {
        return modelMapper.map(user, UserRegistrationResponseDto.class);
    }
}
