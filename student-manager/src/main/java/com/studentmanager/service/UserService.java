package com.studentmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentmanager.dto.ChangeUserInformationDTO;
import com.studentmanager.dto.ChangeUserPasswordDTO;
import com.studentmanager.dto.LoginDTO;
import com.studentmanager.dto.RegisterDTO;
import com.studentmanager.model.User;
import com.studentmanager.repository.UserDAO;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return error;
        }
        User user = userDAO.readByUsername(dto.getUsername());
        if (user == null) {
            return "Wrong username";
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return "Wrong password";
        }
        return null;
    }
    
    public String register(RegisterDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return error;
        }
        if (userDAO.readByUsername(dto.getUsername()) != null) {
            return "Username existed";
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return "Password not match";
        }
        if (!userDAO.create(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()
        )) {
            return "Something went wrong while creating your user";
        }
        return null;
    }

    public String changePassword(String username, ChangeUserPasswordDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return error;
        }
        User user = userDAO.readByUsername(username);
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return "Wrong password";
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            return "Password not match";
        }
        if (!userDAO.updatePassword(
                username,
                passwordEncoder.encode(dto.getNewPassword())
        )) {
            return "Something went wrong while changing your password";
        }
        return null;
    }

    public String changeInformation(String username, ChangeUserInformationDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return error;
        }
        if (!userDAO.updateInformation(
                username,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getAddress()
        )) {
            return "Something went wrong while changing your information";
        }
        return null;
    }
}
