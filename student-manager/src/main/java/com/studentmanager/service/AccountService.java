package com.studentmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studentmanager.dto.ChangeAccountInformationDTO;
import com.studentmanager.dto.ChangeAccountPasswordDTO;
import com.studentmanager.dto.LoginDTO;
import com.studentmanager.dto.RegisterDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepo;

    public ServiceResponse<Account> login(LoginDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        Optional<Account> aOptional = accountRepo.findByUsername(dto.getUsername());
        if (!aOptional.isPresent()) {
            return ServiceResponse.error("Wrong username");
        }
        Account account = aOptional.get();
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            return ServiceResponse.error("Wrong password");
        }
        return ServiceResponse.success(account);
    }

    public ServiceResponse<Account> register(RegisterDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        Optional<Account> aOptional = accountRepo.findByUsername(dto.getUsername());
        if (aOptional.isPresent()) {
            return ServiceResponse.error("Username existed");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ServiceResponse.error("Password not match");
        }
        return ServiceResponse.success(accountRepo.save(dto.mapToAccount(new Account())));
    }

    public ServiceResponse<Account> changePassword(Account account, ChangeAccountPasswordDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword())) {
            return ServiceResponse.error("Wrong password");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            return ServiceResponse.error("Password not match");
        }
        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        return ServiceResponse.success(accountRepo.save(account));
    }

    public ServiceResponse<Account> changeInformation(Account account, ChangeAccountInformationDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        dto.mapToAccount(account);
        accountRepo.save(account);
        return ServiceResponse.success(accountRepo.save(account));
    }
}
