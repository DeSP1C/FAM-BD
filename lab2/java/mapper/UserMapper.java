package mapper;

import dto.UserDto;
import entity.User;

public class UserMapper extends AbstractMapper<User, UserDto> {

    public UserMapper() {
        super(
                user -> new UserDto(
                        user.getUserId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName()
                ),
                dto -> new User(
                        dto.userId(),
                        dto.email(),
                        dto.firstName(),
                        dto.lastName()
                )
        );
    }
}