package service.impl;

import dto.UserDto;
import entity.User;
import mapper.UserMapper;
import repository.UserRepository;
import service.UserService;
import util.Error;
import validation.Validation;

import java.util.List;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final Error error;

    public UserServiceImpl(UserRepository userRepository, Error error){
        this.userRepository = userRepository;
        this.mapper = new UserMapper();
        this.error = error;
    }

    private boolean ValidationUser(User user){
        if (!Validation.isID(user.getUserId())) {
            System.out.println("User id entered incorrectly.");
            return false;
        }
        if (!Validation.isEmail(user.getEmail())) {
            System.out.println("Email entered incorrectly");
            return false;
        }
        if (!Validation.isName(user.getFirstName())) {
            System.out.println("First name entered incorrectly");
            return false;
        }
        if (!Validation.isName(user.getLastName())) {
            System.out.println("Last name entered incorrectly");
            return false;
        }
        return true;
    }

    private boolean ValidationId(int id){
        if (!Validation.isID(id)) {
            System.out.println("User id entered incorrectly.");
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<UserDto> getAllUsers(){
        List<User> usersList = userRepository.getAllUsers();
        ArrayList<UserDto> usersDtoList = new ArrayList<>();

        if(usersList == null){
            System.out.println("User table is empty");
            return null;
        }

        for(User u : usersList) {
            usersDtoList.add(mapper.toDto(u));
        }

        return usersDtoList;
    }

    @Override
    public UserDto getUser(int id){
        if(!ValidationId(id))
            return null;

        if (!error.isUserIdExists(id)) {
            System.out.println("The user with this id does not exist.");
            return null;
        }

        User user = userRepository.getUser(id);

        return mapper.toDto(user);
    }

    @Override
    public void addUser(User user){
        if(!ValidationUser(user))
            return;
        if(error.isUserIdExists(user.getUserId())){
            System.out.println("The user with this id is already taken");
            return;
        }

        userRepository.addUser(user);
    }

    @Override
    public void deleteUser(int id){
        if(!ValidationId(id))
            return;
        if (!error.isUserIdExists(id)) {
            System.out.println("The user with this id does not exist.");
            return;
        }

        userRepository.deleteUser(id);
    }

    @Override
    public void updateUser(User user){
        if(!ValidationUser(user))
            return;
        if (!error.isUserIdExists(user.getUserId())) {
            System.out.println("The user with this id does not exist.");
            return;
        }

        userRepository.updateUser(user);
    }

    @Override
    public void randomGenerateUsers(int recordCount){
        if(!ValidationId(recordCount)){
            System.out.println("This number is unacceptable");
            return;
        }

        userRepository.randomGenerateUsers(recordCount);
    }
}
