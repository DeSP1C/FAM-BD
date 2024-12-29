package repository;

import entity.User;

import java.util.List;
public interface UserRepository {
    List<User> getAllUsers();

    User getUser(int id);

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    void randomGenerateUsers(int recordCount);
}
