package com.myproject.mono.service;

import com.myproject.mono.model.User;
import com.myproject.mono.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> fetchAllUsers()
    {
        return userRepository.findAll();
    }
    public void addUser(User user)
    {
        userRepository.save(user);
    }

    public Optional<User> fetchUser(Integer id) {
        return userRepository.findById(id);
    }

    public boolean updateUser(Integer id, User userToUpdate) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(userToUpdate.getFirstName());
                    user.setLastName(userToUpdate.getLastName());
                    userRepository.save(user);
                    return true;
                }).orElse(false);

    }

    public boolean deleteUser(Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
