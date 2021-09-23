package com.example.task_2_1_2.service;

import com.example.task_2_1_2.entity.User;
import com.example.task_2_1_2.message.ResponseApi;
import com.example.task_2_1_2.payload.UserDto;
import com.example.task_2_1_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Creating new user
     * @param userDto - username, password
     * @return ResponseApi - result message, success bit while creating
     */
    public ResponseApi create(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()))
            return new ResponseApi("Username already exists", false);
        User user = new User(null, userDto.getUsername(), userDto.getPassword());
        userRepository.save(user);
        return new ResponseApi("User is created", true);
    }

    /**
     * Reading all users
     * @return List<User>
     * @param page
     * @param size
     */
    public List<User> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> all = userRepository.findAll(pageable);
        return all.getContent();
    }

    /**
     * Reading user by id
     * @param id - user id
     * @return ResponseApi - result message, success bit, User object while reading
     */
    public ResponseApi getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return new ResponseApi("User not found", false);
        return new ResponseApi("User is found", true, optionalUser.get());
    }

    /**
     * Updating user by id
     * @param id - user id
     * @param userDto - username, password
     * @return ResponseApi - result message, success bit while updating
     */
    public ResponseApi update(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return new ResponseApi("User not found", false);
        if (userRepository.existsByUsername(userDto.getUsername()))
            return new ResponseApi("Username already exists", false);
        User user = optionalUser.get();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ResponseApi("User data is updated", true);
    }

    /**
     * Deleting user by id
     * @param id - user id
     * @return ResponseApi - result message, success bit while deleting
     */
    public ResponseApi deleteById(Integer id) {
        if (!userRepository.existsById(id))
            return new ResponseApi("User not found", false);
        userRepository.deleteById(id);
        return new ResponseApi("User is deleted", true);
    }
}
