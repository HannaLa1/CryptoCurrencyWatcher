package by.company.cryptocurrencywatcher.mapper.user;

import by.company.cryptocurrencywatcher.dto.user.UserDTO;
import by.company.cryptocurrencywatcher.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
    List<UserDTO> toUserDTOList(List<User> users);
}
