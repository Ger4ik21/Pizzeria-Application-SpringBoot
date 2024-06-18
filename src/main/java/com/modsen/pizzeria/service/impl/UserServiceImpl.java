package com.modsen.pizzeria.service.impl;

import com.modsen.pizzeria.domain.User;
import com.modsen.pizzeria.dto.response.UserResponse;
import com.modsen.pizzeria.dto.request.CreateUserRequest;
import com.modsen.pizzeria.dto.request.UpdateUserRequest;
import com.modsen.pizzeria.error.ErrorMessage;
import com.modsen.pizzeria.exception.DuplicateResourceException;
import com.modsen.pizzeria.exception.InvalidInputException;
import com.modsen.pizzeria.exception.ResourceNotFoundException;
import com.modsen.pizzeria.mappers.UserMapper;
import com.modsen.pizzeria.repository.UserRepository;
import com.modsen.pizzeria.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        validateUserEmail(createUserRequest.email());
        checkUserExistence(createUserRequest.email());

        User user = userMapper.toUser(createUserRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        validateUserEmail(updateUserRequest.email());
        User existingUser = findUserByIdOrThrow(id);

        checkUserExistence(updateUserRequest.email());
        userMapper.updateUserFromRequest(updateUserRequest, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findUserByIdOrThrow(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = findUserByIdOrThrow(id);
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, "User", id)));
    }

    private void validateUserEmail(String email) {
        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new InvalidInputException(String.format(ErrorMessage.INVALID_DATA, "email address"));
        }
    }

    private void checkUserExistence(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(String.format(ErrorMessage.DUPLICATE_RESOURCE, "User", "email"));
        }
    }
}
