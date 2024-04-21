package com.example.demo2.service;

import com.example.demo2.request.AuthRequest;
import com.example.demo2.dto.UserDTO;
import com.example.demo2.model.User;
import com.example.demo2.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user -> mapToDTO(user, new UserDTO())).collect(Collectors.toList());
    }

    public User get(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
    }

    public UserDTO registerUser(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername().toLowerCase());
        user.setEmail(userDto.getEmail().toLowerCase(Locale.ROOT));
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());
        System.out.println(user);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public UserDTO login(AuthRequest userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user.getPassword().equals(userDto.getPassword())) {
            System.out.println(user.toString());
            UserDTO res = modelMapper.map(user, UserDTO.class);
            return res;
        }
        return null;
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, UserDTO userDTO) {
        userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, User user) {
        user = modelMapper.map(userDTO, User.class);
        return user;
    }
}
