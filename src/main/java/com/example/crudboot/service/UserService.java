package com.example.crudboot.service;

import com.example.crudboot.dto.UserDto;
import com.example.crudboot.entity.Users;
import com.example.crudboot.exception.ResourceNotFoundException;
import com.example.crudboot.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto map2Dto(Users user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setFullname(user.getFullname());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        return userDto;
    }

    public List<UserDto> getAllUsers() {
        List<Users> users = userRepository.findAll();

        return users.stream().map(this::map2Dto).toList();
    }

    public Users createUser(UserDto userBody) {
        Users user = modelMapper.map(userBody, Users.class);
        return userRepository.save(user);
    }

    public UserDto getUser(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User Not Found"));
        return map2Dto(user);
    }

    public UserDto updateUser(Long userId, UserDto userBody) {
        Users user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User Not Found"));

        modelMapper.map(userBody, user);
        userRepository.save(user);
        return map2Dto(user);
    }

    public void deleteUser(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User Not Found"));
        userRepository.delete(user);
    }
}
