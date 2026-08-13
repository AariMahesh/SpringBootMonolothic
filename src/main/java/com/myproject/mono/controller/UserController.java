package com.myproject.mono.controller;

import com.myproject.mono.dto.UserRequest;
import com.myproject.mono.dto.UserResponse;
import com.myproject.mono.model.User;
import com.myproject.mono.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(value = "id")Integer id)
    {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest)
    {
        userService.addUser(userRequest);
        return new ResponseEntity<>("User created Successfully",HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable(value = "id")Integer id,@RequestBody UserRequest userRequestToUpdate)
    {
        boolean updatedStatus = userService.updateUser(id, userRequestToUpdate);
        if(updatedStatus)
        {
            return new ResponseEntity<>("User Updated Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id)
    {
        boolean updatedStatus = userService.deleteUser(id);
        if(updatedStatus)
        {
            return new ResponseEntity<>("User Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);
    }
}
