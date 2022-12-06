package com.wildtac.mapper;

import com.wildtac.domain.user.User;
import com.wildtac.dto.user.registration.UserAbstractDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserMapper implements StructMapper<UserAbstractDto, User>{

    private final ModelMapper modelMapper;

    @Override
    public List<User> fromDtoListToObjectList(List<UserAbstractDto> userAbstractDtoList) {
        return modelMapper.map(userAbstractDtoList, new TypeToken<List<User>>() {}.getType());
    }

    @Override
    public List<UserAbstractDto> fromObjectListToDtoList(List<User> userList) {
        return modelMapper.map(userList, new TypeToken<List<UserAbstractDto>>() {}.getType());
    }

    @Override
    public User fromDtoToObject(UserAbstractDto userAbstractDto) {
        return modelMapper.map(userAbstractDto, User.class);
    }

    @Override
    public UserAbstractDto fromObjectToDto(User user) {
        return modelMapper.map(user, UserAbstractDto.class);
    }
}
