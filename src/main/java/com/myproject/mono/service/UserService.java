package com.myproject.mono.service;

import com.myproject.mono.dto.AddressDto;
import com.myproject.mono.dto.UserRequest;
import com.myproject.mono.dto.UserResponse;
import com.myproject.mono.model.Address;
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
    public List<UserResponse> fetchAllUsers()
    {
        return userRepository.findAll().stream().map(this::mapToUserResponse).toList();
    }
    public void addUser(UserRequest userRequest)
    {
        User user = new User();
        userRepository.save(mapToEntity(user,userRequest));
    }

    public Optional<UserResponse> fetchUser(Integer id) {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public boolean updateUser(Integer id, UserRequest userRequestToUpdate) {
        return userRepository.findById(id)
                .map(user -> {
                    mapToEntity(user,userRequestToUpdate);
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
    private UserResponse mapToUserResponse(User user)
    {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setPhoneNo(user.getPhoneNo());
        if (user.getAddress()!=null)
        {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setZipcode(user.getAddress().getCity());
            userResponse.setAddressDto(addressDto);

        }
        return userResponse;

    }
    private User mapToEntity(User user,UserRequest userRequest)
    {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNo(userRequest.getPhoneNo());
        if(userRequest.getAddressDto()!=null)
        {
            Address address = new Address();
            address.setCity(userRequest.getAddressDto().getCity());
            address.setState(userRequest.getAddressDto().getState());
            address.setCountry(userRequest.getAddressDto().getCountry());
            address.setStreet(userRequest.getAddressDto().getStreet());
            address.setZipcode(userRequest.getAddressDto().getCity());
            user.setAddress(address);

        }
        return user;
    }
}
