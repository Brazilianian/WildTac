package com.wildtac.mapper.user;

import com.wildtac.domain.user.User;
import com.wildtac.dto.user.UserDto;
import com.wildtac.mapper.StructMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserMapper implements StructMapper<UserDto, User> {

    private final ModelMapper modelMapper;

    @Override
    public List<User> fromDtoListToObjectList(List<UserDto> userDtoList) {
        return modelMapper.map(userDtoList, new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public List<UserDto> fromObjectListToDtoList(List<User> userList) {
        return modelMapper.map(userList, new TypeToken<List<UserDto>>() {}.getType());
    }

    @Override
    public User fromDtoToObject(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDto fromObjectToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
