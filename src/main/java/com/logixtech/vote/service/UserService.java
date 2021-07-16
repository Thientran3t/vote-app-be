package com.logixtech.vote.service;

import com.logixtech.vote.dto.UserDto;
import com.logixtech.vote.dto.request.UserRequest;
import com.logixtech.vote.exception.EmailTakenException;
import com.logixtech.vote.exception.UserNotFoundException;
import com.logixtech.vote.model.Authority;
import com.logixtech.vote.model.User;
import com.logixtech.vote.repository.AuthorityRepository;
import com.logixtech.vote.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public UserDto registerUser(UserRequest userRequest) {
        if (checkEmailExisted(userRequest.getEmail().toLowerCase())) {
            throw new EmailTakenException();
        }
        User user = new User();
        user.setEmail(userRequest.getEmail().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.findByName("ROLE_USER"));
        user.setAuthorities(authorities);
        user = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPublicId(user.getPublicId());

        return userDto;
    }

    public UserDto getUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException();
        }
        UserDto userDto = new UserDto(userOptional.get().getPublicId(), userOptional.get().getEmail());
        return userDto;
    }

    public boolean checkEmailExisted(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
