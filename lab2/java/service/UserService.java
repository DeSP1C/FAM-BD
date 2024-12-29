package service;

import dto.UserDto;
import entity.User;

import java.util.ArrayList;

public interface UserService {
    ArrayList<UserDto> getAllUsers();

    UserDto getUser(int id);

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    void randomGenerateUsers(int recordCount);
}
