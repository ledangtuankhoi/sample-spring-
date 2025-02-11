package com.example.employee.Service;

import com.example.employee.DTO.EmployeeDTO;
import com.example.employee.DTO.LoginUserDto;
import com.example.employee.DTO.RegisterUserDto;
import com.example.employee.Model.EmployeeEntity;
import com.example.employee.Model.User;
import com.example.employee.Repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        EmployeeService employeeService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService;
    }

    public User signup(RegisterUserDto input) {
        try {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setFirstName(input.getFirstName());
            employeeDTO.setLastName(input.getLastName());

            employeeDTO = employeeService.save(employeeDTO);
            var user = new User()
                .setFirstName(input.getFirstName())
                .setLastName(input.getLastName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setEmplId(employeeDTO.getId());

            return userRepository.save(user);
        } catch (Exception e) {
            throw e;
        }
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
